package model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 * Represents a product with attributes such as ID, name, stock, category, price
 * history, and unit conversion details.
 * 
 * - Supports managing multiple prices using a priority queue, sorted by date. -
 * Allows retrieval of the most recent price or a price for a specific date. -
 * Includes methods to manage stock, category, and organic status. - Overrides
 * equals, hashCode, and toString for proper comparison and representation.
 */

public class Product {
	private int productId;
	private String name;
	private boolean organic;
	private double stock;
	private PriorityQueue<Price> prices = new PriorityQueue<>(
			(price1, price2) -> price2.getDate().compareTo(price1.getDate()));
	ProductCategory productCategory;
	private UnitConverter unitConverter;
	private Price price;

	public Product(int productId, String name, ProductCategory productCategory, double stock,
			UnitConverter unitConverter) {
		this.productId = productId;
		this.name = name;
		organic = false;
		this.stock = stock;
		this.productCategory = productCategory;
		this.unitConverter = unitConverter;
	}

	public Product(int productId, String name, ProductCategory productCategory, double stock, Price price,
			UnitConverter unitConverter) {
		this.productId = productId;
		this.name = name;
		organic = false;
		this.price = price;
		this.stock = stock;
		this.productCategory = productCategory;
		this.unitConverter = unitConverter;
		this.prices.offer(price);
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Price getPrice() {
		return prices.peek();
	}

	public void setPrice(Price price) {
		prices.offer(price);
	}

	public void setPrice(Collection<Price> priceCollection) {
		if (priceCollection != null) {
			for (Price price : priceCollection) {
				prices.offer(price);
			}
		}
	}

	public Price getPriceForDate(LocalDate searchDate) {
		PriorityQueue<Price> tempQueue = new PriorityQueue<>(prices);
		Price result = null;

		while (!tempQueue.isEmpty()) {
			Price price = tempQueue.poll();
			if (!price.getDate().isAfter(searchDate)) {
				result = price;
				break;
			}
		}
		return result;
	}

	public boolean isOrganic() {
		return organic;
	}

	public void setOrganic(boolean organic) {
		this.organic = organic;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UnitConverter getUnitConverter() {
		return unitConverter;
	}

	public void setUnitConverter(UnitConverter unitConverter) {
		this.unitConverter = unitConverter;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Product product = (Product) obj;
		return productId == product.productId;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(productId);
	}

	@Override
	public String toString() {
		return "Product [id=" + productId + ", name=" + name + ", stock=" + stock + "]";
	}
}
