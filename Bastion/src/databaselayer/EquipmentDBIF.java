package databaselayer;

import java.sql.SQLException;
import java.util.List;

import model.Equipment;

public interface EquipmentDBIF {

	public Equipment addEquipment(Equipment equipment) throws SQLException, DataAccessException;

	public List<Equipment> findEquipmentByName(String name) throws SQLException, DataAccessException;

}
