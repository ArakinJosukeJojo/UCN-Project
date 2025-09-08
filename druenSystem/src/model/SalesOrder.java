package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalesOrder {
	private int orderNo;
	private LocalDate date;
	private BigDecimal totalPrice;
	private OrderStatus orderStatus;
	private String salesType;
	private Customer customer;
	private List<SalesOrderLine> orderlines;

	public SalesOrder() {
		this.orderNo = 0;
		this.date = LocalDate.now();
		this.totalPrice = BigDecimal.ZERO;
		this.orderStatus = OrderStatus.PENDING;
		this.salesType = "Standard";
		orderlines = new ArrayList<SalesOrderLine>();
	}

	public SalesOrder(int orderNo, LocalDate date, BigDecimal totalPrice, OrderStatus orderStatus) {
		this.orderNo = orderNo;
		this.date = date;
		this.totalPrice = totalPrice;
		this.orderStatus = orderStatus;
		this.salesType = "Standard";
		this.orderlines = new ArrayList<>();
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;

	}

	public boolean addCustomerToOrder(Customer customer) {
		boolean result = false;
		if (customer != null) {
			this.customer = customer;
			result = true;
		}
		return result;
	}

	public List<SalesOrderLine> getOrderLines() {
		return new ArrayList<SalesOrderLine>(orderlines);
	}

	public void addOrderLine(SalesOrderLine orderLine) {
		if (this.orderlines == null) {
			this.orderlines = new ArrayList<>();
		}
		this.orderlines.add(orderLine);
	}

	// Adds a product to the order with a specified quantity.
	// 1. Validates that the product and customer exist, and the product has a unit
	// converter.
	// 2. Converts the quantity to weight using the unit converter.
	// 3. Checks if sufficient stock is available for the product.
	// 4. If the product already exists in the order, updates its quantity if within
	// stock limits.
	// 5. If the product is new, adds it to the orderlines with the converted
	// quantity.
	// 6. Recalculates the total price after successful addition.

	public boolean addProductToOrder(Product p, double quantity) throws Exception {
		boolean result = false;

		if (p != null) {
			if (p.getUnitConverter() == null) {
				throw new Exception("Der eksisterer ikke en unit på");
			}
			if (getCustomer() == null) {
				throw new Exception("Der eksisterer ikke en kunde");
			}

			double convertedQuantity = convertToWeight(p, quantity);

			if (p.getStock() >= convertedQuantity) {
				boolean updated = false;

				for (SalesOrderLine orderline : orderlines) {
					if (orderline.getProduct().equals(p)) {
						double newQuantity = orderline.getQuantity() + convertedQuantity;

						if (newQuantity <= p.getStock()) {
							orderline.setQuantity(newQuantity);
							updated = true;
							result = true;
						} else {
							return false;
						}
						break;
					}
				}

				if (!updated) {
					if (convertedQuantity <= p.getStock()) {
						SalesOrderLine ol = new SalesOrderLine(p, convertedQuantity);
						orderlines.add(ol);
						result = true;
					} else {
						return false;
					}
				}

				calculateTotalPrice();
			} else {
				return false;
			}
		}

		return result;
	}

	public double convertToWeight(Product product, double quantity) {

		if (!getCustomer().getPreferedUnit().equals(product.getUnitConverter().getUnitConversionType())) {
			quantity = quantity * product.getUnitConverter().getConversionRate();
		}
		return quantity;
	}

	public boolean updateOrderLine(Product product, double quantity) {
		boolean result = false;
		for (SalesOrderLine orderLine : orderlines) {
			if (orderLine.getProduct().equals(product)) {
				double newQuantity = quantity;
				double convertedQuantity = convertToWeight(product, newQuantity);
				orderLine.setQuantity(convertedQuantity);
				result = true;
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return "SalesOrder{" + "orderNo=" + orderNo + ", date=" + date + ", totalPrice=" + totalPrice
				+ ", orderStatus='" + orderStatus + '\'' + ", salesType='" + salesType + '\'' + ", customer="
				+ customer.getName() + '}';
	}
	// Removes a specific SalesOrderLine from the orderlines list.
	// 1. Iterates through the orderlines collection using an iterator.
	// 2. Compares each SalesOrderLine with the provided one using equals.
	// 3. Removes the matching line and breaks out of the loop on success.
	// 4. Returns true if removal was successful, false otherwise.

	public boolean removeOrderLine(SalesOrderLine so) {
		boolean result = false;
		var iterator = orderlines.iterator();
		while (iterator.hasNext()) {
			SalesOrderLine orderline = iterator.next();
			if (orderline.equals(so)) {
				iterator.remove();
				result = true;
				break;
			}
		}
		return result;
	}
	// Calculates the total price of the sales order by summing up the total price
	// of each order line.
	// 1. Resets the total price to zero.
	// 2. Iterates through all SalesOrderLines in the order.
	// 3. For each line, multiplies the product's sales price by its quantity if the
	// price is available.
	// 4. Accumulates the calculated order line total into the total price.

	public void calculateTotalPrice() {
		totalPrice = BigDecimal.ZERO; // Nulstil totalen

		for (SalesOrderLine so : orderlines) {
			BigDecimal quantity = BigDecimal.valueOf(so.getQuantity());
			Price productPrice = so.getProduct().getPrice();

			if (productPrice != null && productPrice.getSalesPrice() != null) {
				BigDecimal salesPrice = productPrice.getSalesPrice();
				BigDecimal orderLineTotal = quantity.multiply(salesPrice);
				totalPrice = totalPrice.add(orderLineTotal);
			}
		}
	}

}
