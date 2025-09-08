package model;

public class SalesOrderLine {

	private int orderLineNo;
	private Equipment equipment;
	private int quantity;

	public SalesOrderLine(int orderLineNo, Equipment equipment, int quantity) {
		this.orderLineNo = orderLineNo;
		this.equipment = equipment;
		this.quantity = quantity;
	}

	public int getOrderLineNo() {
		return orderLineNo;
	}

	public void setOrderLineNo(int orderLineNo) {
		this.orderLineNo = orderLineNo;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
