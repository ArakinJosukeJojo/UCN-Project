package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Equipment;

public class EquipmentDB implements EquipmentDBIF {

	private static final String ADD_EQUIPMENT = "INSERT INTO equipments (name, category, stock, price) VALUES (?,?,?,?)";
	private PreparedStatement addEquipment;

	private static final String SELECT_ALL_EQUIPMENT = "SELECT * FROM EQUIPMENTS e";
	private static final String FIND_EQUIPMENT_BY_NAME = SELECT_ALL_EQUIPMENT + " WHERE e.Name LIKE ?";
	private PreparedStatement findEquipment;

	public EquipmentDB() throws DataAccessException {
		init();
	}

	public void init() throws DataAccessException {
		Connection con = DBConnection.getUniqueInstance().getConnection();
		try {
			addEquipment = con.prepareStatement(ADD_EQUIPMENT);
			findEquipment = con.prepareStatement(FIND_EQUIPMENT_BY_NAME);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	public Equipment addEquipment(Equipment equipment) throws DataAccessException {
		try {
			addEquipment.setString(1, equipment.getName());
			addEquipment.setString(2, equipment.getCategory());
			addEquipment.setInt(3, equipment.getStock());
			addEquipment.setDouble(4, equipment.getPrice());
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			addEquipment.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);

		}
		return equipment;

	}

	public List<Equipment> findEquipmentByName(String name) throws DataAccessException {
		List<Equipment> e = new ArrayList<>();
		try {
			findEquipment.setString(1, "%" + name + "%");
			ResultSet rs = findEquipment.executeQuery();
			while (rs.next()) {
				e.add(buildObject(rs));
			}
		} catch (SQLException ex) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, ex);
		}
		return e;
	}

	private Equipment buildObject(ResultSet rs) throws DataAccessException {
		Equipment currentEquipment = new Equipment();
		try {
			currentEquipment.setName(rs.getString("name"));
			currentEquipment.setCategory(rs.getString("category"));
			currentEquipment.setStock(rs.getInt("stock"));
			currentEquipment.setPrice(rs.getDouble("price"));
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currentEquipment;
	}

}
