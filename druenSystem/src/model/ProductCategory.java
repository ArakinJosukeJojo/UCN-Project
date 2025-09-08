package model;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
	private String description;
	private List<Product> products;

	public ProductCategory(String description) {
		this.description = description;
		products = new ArrayList<>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Product> getProducts() {
		return products;
	}

}
