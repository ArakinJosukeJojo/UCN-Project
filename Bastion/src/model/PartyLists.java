package model;

import java.util.ArrayList;
import java.util.List;

public class PartyLists {
	private List<PartyLines> partyLine;
	private String description;
	private Characters character;

	public PartyLists() {
		partyLine = new ArrayList<>();
	}

	public PartyLists(String description) {
		this.description = description;
		partyLine = new ArrayList<>();
	}

	public List<Characters> getCharacterList() {
		List<Characters> characters = new ArrayList<>();
		for (PartyLines line : partyLine) {
			characters.add(line.getCharacter()); // Extract Characters from PartyLines
		}
		return characters;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean addCharacterToParty(Characters character) {
		if (character != null) {
			PartyLines pl = new PartyLines(character);
			partyLine.add(pl);
			return true;
		}
		return false;
	}

	public boolean removeCharacterFromParty(Characters character) {
		return partyLine.removeIf(line -> line.getCharacter().equals(character));
	}

	public List<PartyLines> getPartyLine() {
		return partyLine;
	}

	public void setPartyLine(ArrayList<PartyLines> partyLine) {
		this.partyLine = partyLine;
	}

	public Characters getCharacter() {
		return character;
	}

	public void setCharacter(Characters character) {
		this.character = character;
	}

	@Override
	public String toString() {
		return character + " added from PartyList";
	}
}
