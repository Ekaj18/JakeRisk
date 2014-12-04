package ai;

import map.Country;

public class Connection {
	
	private int continent1;
	private int continent2;
	private Country country1;
	private Country country2;
	
	public Connection(Country country1, Country country2){
		
		continent1 = country1.getContinent();
		continent2 = country2.getContinent();
		this.country1 = country1;
		this.country2 = country2;
	}

	public int getContinent1() {
		return continent1;
	}

	public void setContinent1(int continent1) {
		this.continent1 = continent1;
	}

	public int getContinent2() {
		return continent2;
	}

	public void setContinent2(int continent2) {
		this.continent2 = continent2;
	}

	public Country getCountry1() {
		return country1;
	}

	public void setCountry1(Country country1) {
		this.country1 = country1;
	}

	public Country getCountry2() {
		return country2;
	}

	public void setCountry2(Country country2) {
		this.country2 = country2;
	}
}
