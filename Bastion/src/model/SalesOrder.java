package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesOrder {

	private int salesNo;
	private List<SalesOrderLine> orderlines;
	private BigDecimal totalPrice;

	public SalesOrder(int salesNo, BigDecimal totalPrice) {
		this.salesNo = salesNo;
		this.orderlines = new ArrayList<SalesOrderLine>();
		this.totalPrice = BigDecimal.ZERO;
	}

	public int getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(int salesNo) {
		this.salesNo = salesNo;
	}

	public List<SalesOrderLine> getOrderlines() {
		return new ArrayList<SalesOrderLine>(orderlines);
	}

	public void setOrderlines(List<SalesOrderLine> orderlines) {
		this.orderlines = orderlines;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void calculateTotalPrice() {
		totalPrice = BigDecimal.ZERO; // Reset total price

		for (SalesOrderLine so : orderlines) {
			if (so != null && so.getEquipment() != null) { // Check for null
				BigDecimal quantity = BigDecimal.valueOf(so.getQuantity());
				BigDecimal salesPrice = BigDecimal.valueOf(so.getEquipment().getPrice());

				if (quantity.compareTo(BigDecimal.ZERO) > 0 && salesPrice.compareTo(BigDecimal.ZERO) > 0) {
					// Calculate order line total
					BigDecimal orderLineTotal = quantity.multiply(salesPrice);
					totalPrice = totalPrice.add(orderLineTotal);
				}
			}
		}
	}

	public void addOrderLine(SalesOrderLine orderLine) {
		if (this.orderlines != null) {
			this.orderlines = new ArrayList<>();
		}
		this.orderlines.add(orderLine);
	}

}
