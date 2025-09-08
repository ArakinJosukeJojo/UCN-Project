package model;

public class Location {
	private String address;
	private String city;
	private String zipcode;
	private PackingList packingList;

	public Location(String address, String city, String zipcode) {
		this.address = address;
		this.city = city;
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public PackingList getPackingList() {
		return packingList;
	}

	public void setPackingList(PackingList packingList) {
		this.packingList = packingList;
	}
}
