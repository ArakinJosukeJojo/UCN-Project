package databaselayer;

import java.sql.SQLException;
import java.util.List;

import model.Characters;

public interface CharacterDBIF {

	public Characters addCharacter(Characters character) throws SQLException, DataAccessException;

	public List<Characters> findCharactersByName(String name) throws DataAccessException;

}
