package control;

import java.sql.SQLException;
import java.util.List;

import databaselayer.DataAccessException;
import databaselayer.PartyDB;
import databaselayer.PartyDBIF;
import model.Characters;
import model.PartyLines;
import model.PartyLists;

public class PartyController {

	private PartyDBIF partyDB;
	private PartyLists currentParty;

	public PartyController() throws DataAccessException, SQLException {
		partyDB = new PartyDB();
		createPartyList();
	}

	public PartyLists createPartyList() throws DataAccessException, SQLException {
		currentParty = new PartyLists();
		return currentParty;
	}

	public PartyLists getCurrentParty() {
		return currentParty;
	}

	public boolean addCharacterToList(Characters c) {
		return currentParty.addCharacterToParty(c);
	}

	public void removeCharacterFromParty(Characters character) {
		// Find the PartyLines entry with the given character and remove it
		List<PartyLines> partyLines = currentParty.getPartyLine();
		partyLines.removeIf(partyLine -> partyLine.getCharacter().equals(character));
//	    Iterator<PartyLines> iterator = partyLines.iterator();
//	    while (iterator.hasNext()) {
//	        PartyLines partyLine = iterator.next();
//	        if (partyLine.getCharacter().equals(character)) {
//	            iterator.remove();
	}

	public boolean saveParty() {
		boolean result = false;

		result = partyDB.saveParty(currentParty);

		return result;
	}

}
