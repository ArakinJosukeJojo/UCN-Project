package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Monster;

public class MonsterDB implements MonsterDBIF {

	private static final String ADD_MONSTER = "INSERT INTO monsters (name, lifespan, classification, average_size, habitat, danger_level) VALUES (?,?,?,?,?,?)";
	PreparedStatement addMonster;

	private static final String SELECT_ALL_MONSTER = "SELECT * FROM MONSTERS m";
	private static final String FIND_MONSTER_BY_NAME = SELECT_ALL_MONSTER + " WHERE m.Name LIKE ?";
	PreparedStatement findMonster;

	public MonsterDB() throws DataAccessException {
		init();
	}

	public void init() throws DataAccessException {
		// Jeg holder dem samlet. Det er her at forbindelsen til databasen sker.
		// Vi skal holde indirekte.
		Connection con = DBConnection.getUniqueInstance().getConnection();
		try {
			addMonster = con.prepareStatement(ADD_MONSTER);
			findMonster = con.prepareStatement(FIND_MONSTER_BY_NAME);
			// TODO findMonster = con.prepareStatement(FIND_MONSTER);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	@Override
	public Monster addMonster(Monster monster) throws DataAccessException {
		try {
			addMonster.setString(1, monster.getName());
			addMonster.setInt(2, monster.getLifespan());
			addMonster.setString(3, monster.getClassification());
			addMonster.setDouble(4, monster.getSize());
			addMonster.setString(5, monster.getHabitat());
			addMonster.setString(6, monster.getDanger());
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			addMonster.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);

		}
		return monster;
	}

	@Override
	public List<Monster> findMonsterByName(String name) throws DataAccessException {
		List<Monster> m = new ArrayList<>();
		try {
			findMonster.setString(1, "%" + name + "%");
			ResultSet rs = findMonster.executeQuery();
			while (rs.next()) {
				m.add(buildObject(rs));
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return m;
	}

	private Monster buildObject(ResultSet rs) throws DataAccessException {
		Monster currentMonster = new Monster(); // Jeg skal bruge en no-argument constructor her, ellers giver
												// det fejl.
		try {
			currentMonster.setName(rs.getString("name"));
			currentMonster.setLifespan(rs.getInt("lifespan"));
			currentMonster.setClassification(rs.getString("classification"));
			currentMonster.setSize(rs.getDouble("average_size"));
			currentMonster.setHabitat(rs.getString("habitat"));
			currentMonster.setDanger(rs.getString("danger_level"));
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currentMonster;
	}

}
