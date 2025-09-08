package model;

import java.util.Objects;

public class SalesOrderLine {
	private int orderLineId;
	private Product product;
	private double quantity;

	public SalesOrderLine(int id, Product j, double quantity) {
		orderLineId = id;
		product = j;
		this.quantity = quantity;
	}

	public SalesOrderLine(Product j, double quantity) {
		this.orderLineId = 0;
		this.product = j;
		this.quantity = quantity;
	}

	public SalesOrderLine(int id, double quantity) {
		orderLineId = id;
		this.quantity = quantity;
	}

	public int getOrderLineId() {
		return orderLineId;
	}

	public void setOrderLineId(int orderLineId) {
		this.orderLineId = orderLineId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SalesOrderLine orderLine = (SalesOrderLine) o;
		return orderLineId == orderLine.orderLineId && quantity == orderLine.quantity
				&& Objects.equals(product, orderLine.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderLineId, product, quantity);
	}

	@Override
	public String toString() {
		return "OrderLine{" + "orderLineId=" + orderLineId + ", product=" + product + ", quantity=" + quantity + '}';
	}
}
