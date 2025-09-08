package model;

public enum EquipmentCategory {

	FOODS("Food"), ITEMS("Furniture"), OTHERS("Others");

	private final String displayName;

	EquipmentCategory(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

	public static EquipmentCategory fromDisplayName(String displayName) {
		for (EquipmentCategory category : EquipmentCategory.values()) {
			if (category.displayName.equalsIgnoreCase(displayName)) {
				return category;
			}
		}
		throw new IllegalArgumentException("Unknown displayName: " + displayName);
	}
}
