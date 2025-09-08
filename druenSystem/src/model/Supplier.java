package model;

public class Supplier extends Company {
	private String country;

	public Supplier(String cvr, String name, String phoneNo, String email, Location location, String country) {
		super(cvr, name, phoneNo, email, location);
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
