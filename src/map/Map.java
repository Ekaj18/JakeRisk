package map;

import java.util.ArrayList;

public class Map {
	private ArrayList<Country> countryList;
	private ArrayList<Integer> continentBonuses;
	private boolean connected = false;

	public Map(ArrayList<Country> countryList, ArrayList<Integer> continentBonuses){
		this.countryList = countryList;
		this.continentBonuses = continentBonuses;
	}
	
	public ArrayList<Country> getCountryList() {
		return countryList;
	}
	
	public boolean isConnected(Country first, Country second){
		connected = false;
		ArrayList<Country> list = new ArrayList<>();
		list.add(first);
		recursiveSearch(first, second, list);
		return connected;
	}
	
	private void recursiveSearch(Country first, Country second, ArrayList<Country> list){
		if(!connected){
			if(first.getBorders().contains(second)){
				connected = true;
			}
			else{
				for(int i = 0; i < first.getBorders().size(); i++){
					if(!list.contains(first.getBorders().get(i))){
						list.add(first.getBorders().get(i));
						recursiveSearch(first.getBorders().get(i), second, list);
					}
				}
			}
		}
	}

	public boolean isAdjacent(Country first, Country second){
		if(first.getBorders().contains(second)){
			return true;
		}
		else{
			return false;			
		}
	}
	
	public int getReinforcements(int player){
		int force = 0;
		boolean[] continents = new boolean[continentBonuses.size()];
		for(int j = 0; j < continents.length; j++){
			continents[j] = true;
		}
		for(int i = 0; i < countryList.size(); i++){
			if(countryList.get(i).getPlayer() == player){
				force++;
			}
			else if(continents[countryList.get(i).getContinent()]){
				continents[countryList.get(i).getContinent()] = false;
			}
		}
		if(force == 0){
			return 0;
		}
		else{
			force /= 3;
			if(force < 3){
				force = 3;
			}
			for(int k = 0; k < continents.length; k++){
				if(continents[k]){
					force += continentBonuses.get(k);
				}
			}
			return force;			
		}
	}
	
	public boolean controlsAll(int player){
		boolean all = true;
		for(int i = 0; i < countryList.size(); i++){
			if(countryList.get(i).getPlayer() != player){
				all = false;
			}
		}
		return all;
	}

	public ArrayList<Integer> getContinentBonuses() {
		return continentBonuses;
	}
}
