package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Price {
	private LocalDate date;
	private BigDecimal purchasePrice;
	private BigDecimal salesPrice;

	public Price(LocalDate date, BigDecimal salesPrice, BigDecimal purchasePrice) {
		this.date = date;
		this.purchasePrice = purchasePrice;
		this.salesPrice = salesPrice;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

}
