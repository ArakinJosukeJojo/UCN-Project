package model;

public class PartyLines {

	private Characters character;

	public PartyLines(Characters character) {
		this.character = character;
	}

	public Characters getCharacter() {
		return character;
	}

	public void setCharacter(Characters character) {
		this.character = character;
	}

	@Override
	public String toString() {
		return character.getName() + " " + character.getMilitaryRank();
	}

}
