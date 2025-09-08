package control;

import java.sql.SQLException;
import java.util.List;

import databaselayer.DataAccessException;
import databaselayer.EquipmentDB;
import databaselayer.EquipmentDBIF;
import model.Equipment;

public class EquipmentController {

	private EquipmentDBIF equipmentDB;

	public EquipmentController() throws DataAccessException {
		equipmentDB = new EquipmentDB();
	}

	public Equipment addEquipment(Equipment equipment) throws SQLException, DataAccessException {
		equipmentDB.addEquipment(equipment);
		return equipment;
	}

	public List<Equipment> findEquipmentByName(String name) throws SQLException, DataAccessException {
		List<Equipment> e = equipmentDB.findEquipmentByName(name);
		return e;
	}

}
