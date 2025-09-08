package model;

public class Company {
	private String cvr;
	private String name;
	private String phoneNo;
	private String email;
	private Location location;

	public Company(String cvr, String name, String phoneNo, String email, Location location) {
		this.cvr = cvr;
		this.name = name;
		this.phoneNo = phoneNo;
		this.email = email;
		this.location = location;
	}

	public String getCvr() {
		return cvr;
	}

	public void setCvr(String cvr) {
		this.cvr = cvr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
