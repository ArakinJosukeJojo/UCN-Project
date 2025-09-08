package model;

public enum OrderStatus {
	PENDING(0), READY_FOR_PACKING(1), BEING_PACKED(2), READY_FOR_DELIVERY(3);

	private final int statusCode;

	OrderStatus(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public static OrderStatus fromInt(int i) {
		switch (i) {
		case 0:
			return PENDING;
		case 1:
			return READY_FOR_PACKING;
		case 2:
			return BEING_PACKED;
		case 3:
			return READY_FOR_DELIVERY;
		default:
			throw new IllegalArgumentException("Invalid OrderStatus code: " + i);
		}
	}

	public static OrderStatus fromString(String status) {
		try {
			return OrderStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid OrderStatus string: " + status, e);
		}
	}
}
