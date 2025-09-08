package databaselayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Characters;

public class CharacterDB implements CharacterDBIF {

	private static final String ADD_CHARACTERS = "INSERT INTO characters (name, age, military_rank, location, occupation, currency) VALUES (?,?,?,?,?,?)";
	private PreparedStatement addCharacter;

	private static final String SELECT_ALL_CHARACTERS = "SELECT * FROM CHARACTERS c";
	private static final String FIND_CHARACTERS_BY_NAME = SELECT_ALL_CHARACTERS + " WHERE c.Name LIKE ?";
	private PreparedStatement findCharacter;

	public CharacterDB() throws DataAccessException {
		init();
	}

	public void init() throws DataAccessException {
		// Jeg holder dem samlet. Det er her at forbindelsen til databasen sker.
		// Vi skal holde indirekte.
		Connection con = DBConnection.getUniqueInstance().getConnection();
		try {
			addCharacter = con.prepareStatement(ADD_CHARACTERS);
			findCharacter = con.prepareStatement(FIND_CHARACTERS_BY_NAME);
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_PREPARE_STATEMENT, e);
		}
	}

	@Override
	public Characters addCharacter(Characters character) throws DataAccessException {
		try {
			addCharacter.setString(1, character.getName());
			addCharacter.setInt(2, character.getAge());
			addCharacter.setString(3, character.getMilitaryRank());
			addCharacter.setString(4, character.getLocation());
			addCharacter.setString(5, character.getOccupation());
			addCharacter.setDouble(6, character.getCurrency());
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_PS_VARS_INSERT, e);
		}
		try {
			addCharacter.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_INSERT, e);

		}
		return character;
	}

	@Override
	public List<Characters> findCharactersByName(String name) throws DataAccessException {
//		Characters c = null;
//		try {
//			findCharacter.setString(1, "%" + name + "%");
//			ResultSet rs = findCharacter.executeQuery();
//			if (rs.next()) {
//				c = buildObject(rs);
//			}
//		} catch (SQLException e) {
//			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
//		}

		List<Characters> c = new ArrayList<>();
		try {
			findCharacter.setString(1, "%" + name + "%");
			ResultSet rs = findCharacter.executeQuery();
			while (rs.next()) {
				c.add(buildObject(rs));
			}
		} catch (SQLException e) {
			throw new DataAccessException(DBMessages.COULD_NOT_BIND_OR_EXECUTE_QUERY, e);
		}
		return c;
	}

	private Characters buildObject(ResultSet rs) throws DataAccessException {
		Characters currentCharacter = new Characters(); // Jeg skal bruge en no-argument constructor her, ellers giver
														// det fejl.
		try {
			currentCharacter.setName(rs.getString("name"));
			currentCharacter.setAge(rs.getInt("age"));
			currentCharacter.setMilitaryRank(rs.getString("military_rank"));
			currentCharacter.setLocation(rs.getString("location"));
			currentCharacter.setOccupation(rs.getString("occupation"));
			currentCharacter.setCurrency(rs.getDouble("currency"));
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new DataAccessException(DBMessages.COULD_NOT_READ_RESULTSET, e);
		}
		return currentCharacter;
	}

}
