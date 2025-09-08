package model;

public class UnitConverter {
	private String unitConversionType;
	private double conversionRate;
	private double boxWeight;
	private Product product;

	public UnitConverter(String unitConversionType, double conversionRate, double boxWeight) {
		this.unitConversionType = unitConversionType;
		this.conversionRate = conversionRate;
		this.boxWeight = boxWeight;
	}

	public String getUnitConversionType() {
		return unitConversionType;
	}

	public void setUnitConversionType(String unitConversionType) {
		this.unitConversionType = unitConversionType;
	}

	public double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(double conversionRate) {
		this.conversionRate = conversionRate;
	}

	public double getBoxWeight() {
		return boxWeight;
	}

	public void setBoxWeight(double boxWeight) {
		this.boxWeight = boxWeight;
	}

}
