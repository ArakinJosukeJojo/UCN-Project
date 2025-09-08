package model;

import java.util.ArrayList;
import java.util.List;

public class PackingList {
	private int id;
	private String name;
	private List<SalesOrder> salesOrders;

	public PackingList(String name) {
		this.name = name;
		this.salesOrders = new ArrayList<>();
	}

	public PackingList(int id, String name) {
		this.id = id;
		this.name = name;
		this.salesOrders = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SalesOrder> getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(List<SalesOrder> salesOrders) {
		this.salesOrders = salesOrders;
	}

	public void addSalesOrder(SalesOrder salesOrder) {
		if (salesOrder != null) {
			this.salesOrders.add(salesOrder);
		}
	}

	@Override
	public String toString() {
		return "PackingList{" + "id=" + id + ", name='" + name + '\'' + ", salesOrders=" + salesOrders + '}';
	}
}
