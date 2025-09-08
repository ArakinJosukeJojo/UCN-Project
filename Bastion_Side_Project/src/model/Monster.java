package model;

public class Monster {

	private String name;
	private int lifespan;
	private String classification;
	private double size;
	private String habitat;
	private String danger;

	public Monster() {

	}

	public Monster(String name, int lifespan, String classification, double size, String habitat, String danger) {
		this.name = name;
		this.lifespan = lifespan;
		this.classification = classification;
		this.size = size;
		this.habitat = habitat;
		this.danger = danger;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLifespan() {
		return lifespan;
	}

	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getHabitat() {
		return habitat;
	}

	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}

	public String getDanger() {
		return danger;
	}

	public void setDanger(String danger) {
		this.danger = danger;
	}

}
