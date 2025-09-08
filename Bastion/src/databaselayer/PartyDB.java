package databaselayer;

import java.sql.Connection;
import java.sql.SQLException;

import model.PartyLists;

public class PartyDB implements PartyDBIF {

	public PartyDB() throws DataAccessException, SQLException {
		init();
	}

	public void init() throws DataAccessException, SQLException {
		Connection con = DBConnection.getUniqueInstance().getConnection();
	}

	@Override
	public boolean saveParty(PartyLists partyList) {
		boolean result = false;

		return false;
	}

}
