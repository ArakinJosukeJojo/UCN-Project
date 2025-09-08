package model;

public class Customer extends Company {
	private String preferedUnit;
	private int packingListId;

	public Customer(String cvr, String name, String phoneNo, String email, Location location, String preferedUnit,
			int packingListId) {
		super(cvr, name, phoneNo, email, location);
		this.preferedUnit = preferedUnit;
		this.packingListId = packingListId;
	}

	public String getPreferedUnit() {
		return preferedUnit;
	}

	public void setPreferedUnit(String preferedUnit) {
		this.preferedUnit = preferedUnit;
	}

	public int getPackingListId() {
		return packingListId;
	}

	public void setPackingListId(int id) {
		packingListId = id;
	}

}
