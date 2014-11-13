package map;

import java.util.ArrayList;

public class Country {
	private int player, continent, armies = 1;
	private ArrayList<Country> borders;
	
	public Country(int continent){
		this.continent = continent;
	}
	public ArrayList<Country> getBorders() {
		return borders;
	}
	public void setBorders(ArrayList<Country> borders) {
		this.borders = borders;
	}
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	public int getArmies() {
		return armies;
	}
	public void addArmies(int armies) {
		this.armies += armies;
	}
	public void destroyArmies(int armies) {
		this.armies -= armies;
	}
	public void setArmies(int armies) {
		this.armies = armies;
	}
	public int getContinent() {
		return continent;
	}
}
