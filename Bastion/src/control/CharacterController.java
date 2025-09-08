package control;

import java.sql.SQLException;
import java.util.List;

import databaselayer.CharacterDB;
import databaselayer.CharacterDBIF;
import databaselayer.DataAccessException;
import model.Characters;

public class CharacterController {

	private CharacterDBIF characterDB;

	public CharacterController() {
		try {
			characterDB = new CharacterDB();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	public Characters addCharacter(Characters character) throws SQLException, DataAccessException {
		Characters c = characterDB.addCharacter(character);
		return c;
	}

	public List<Characters> findCharacterByName(String name) throws SQLException, DataAccessException {
		List<Characters> c = characterDB.findCharactersByName(name);
		return c;
	}

}
