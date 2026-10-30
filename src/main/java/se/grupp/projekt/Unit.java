package se.grupp.projekt;

public enum Unit {	
	
	GRAM(1),
	HEKTO(100),
	KILO(1000),
	TON(1000000);
	
	private final long grams;
	
	Unit(long grams) {
		this.grams = grams;
	}
	
	public long gramsPerUnit() {
		return grams;
	}
	
}
