package model;

import java.util.Objects;

public class Characters {

	private String name;
	private int age;
	private String militaryRank;
	private String location;
	private String occupation;
	private double currency;

	public Characters() {

	}

	public Characters(String name, int age, String militaryRank, String location, String occupation, double currency) {
		this.name = name;
		this.age = age;
		this.militaryRank = militaryRank;
		this.location = location;
		this.occupation = occupation;
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMilitaryRank() {
		return militaryRank;
	}

	public void setMilitaryRank(String militaryRank) {
		this.militaryRank = militaryRank;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public double getCurrency() {
		return currency;
	}

	public void setCurrency(double currency) {
		this.currency = currency;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Characters that = (Characters) obj;
		return Objects.equals(this.name, that.name); // Assuming `id` uniquely identifies a character
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {

		return name + ", Rank: " + militaryRank + "";
	}

}
