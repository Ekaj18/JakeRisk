package game;

import java.util.ArrayList;

import map.Country;
import map.Map;

public class Risk {
	private int players;
	private Map map;
	
	public Risk(Map map, int players){
		this.map = map;
		this.players = players;
	}
	
	public void randomStart(){
		ArrayList<Integer> temp = new ArrayList<>();
		for(int i = 0; i < map.getCountryList().size(); i++){
			temp.add(i);
		}
		int curPlayer = 0;
		while(temp.size() > 0){
			int rand = (int) (Math.random()*temp.size());
			map.getCountryList().get(temp.get(rand)).setPlayer(curPlayer%players);
			temp.remove(rand);
			curPlayer++;
		}
		System.out.println("Randomly generated starting countries.");
	}
	
	public void placeReinforcements(ArrayList<Country> countryList,ArrayList<Integer> armies,int player){
		int total = 0;
		for(int num: armies){
			total += num;
		}
		if(total == map.getReinforcements(player)){
			for(int i = 0; i < countryList.size(); i++){
				countryList.get(i).addArmies(armies.get(i));
			}
			System.out.println(total + " reinforcements added.");
		}
		else{
			System.out.println("\nIncorrect Number of Reinforcments.");
		}
	}
	
	public int getPlayers() {
		return players;
	}

	public Map getMap() {
		return map;
	}

	public void attack(Country attack, Country defense, int attackingUnits){
		if(map.isAdjacent(attack, defense) && attackingUnits < attack.getArmies() && attackingUnits > 0){
			int attDice = attackingUnits;
			if(attDice >  3){
				attDice = 3;
			}
			int defDice = defense.getArmies();
			if(defDice > 2){
				defDice = 2;
			}
			int att1 = 0, att2 = 0;
			int def1 = 0, def2 = 0;
			System.out.println();
			System.out.print("Attack Rolls: ");
			for(int i = 0; i < attDice; i++){
				int roll = (int) (Math.random()*6)+1;
				System.out.print(roll + " ");
				if(roll > att1){
					att1 = roll;
				}
				else if(roll > att2){
					att2 = roll;
				}
			}
			System.out.println();
			System.out.print("Defense Rolls: ");
			for(int i = 0; i < defDice; i++){
				int roll = (int) (Math.random()*6)+1;
				System.out.print(roll + " ");
				if(roll > def1){
					def1 = roll;
				}
				else if(roll > def2){
					def2 = roll;
				}
			}
			System.out.println();
			if(att1 > def1){
				defense.destroyArmies(1);
			}
			else{
				attack.destroyArmies(1);
				attackingUnits--;
			}
			if(att2 != 0 && def2 != 0){
				if(att2 > def2){
					defense.destroyArmies(1);
				}
				else{
					attack.destroyArmies(1);
					attackingUnits--;
				}
			}
			if(defense.getArmies() <= 0){
				System.out.println("\nCountry taken from player " + defense.getPlayer() + " by player " + attack.getPlayer());
				defense.setPlayer(attack.getPlayer());
				defense.setArmies(attackingUnits);
				attack.destroyArmies(attackingUnits);
			}
		}
		else{
			System.out.println("\nInvalid Attack Command.");
		}
	}
	
	public void Fortify(Country first, Country second, int armies){
		if(map.isConnected(first, second) && armies < first.getArmies()){
			System.out.println("\n" + armies + " armies moved during fortification.");
			first.destroyArmies(armies);
			second.addArmies(armies);
		}
		else{
			System.out.println("\nInvalid Fortification");
		}
	}
	
	public int countriesControlled(int player){
		int countries = 0;
		for(int i = 0; i < map.getCountryList().size(); i++){
			if(map.getCountryList().get(i).getPlayer() == player){
				countries++;
			}
		}
		return countries;
	}
	
	public int armiesOwned(int player){
		int armies = 0;
		for(int i = 0; i < map.getCountryList().size(); i++){
			if(map.getCountryList().get(i).getPlayer() == player){
				armies += map.getCountryList().get(i).getArmies();
			}
		}
		return armies;
	}
	
	public boolean isWinner(int player){
		return map.controlsAll(player);
	}
	
	public void placeUnit(Country country){
			country.addArmies(1);
	}
	
	public void currentControl(){
		for(int i = 0; i < map.getCountryList().size(); i++){
			System.out.println("Country " + (i+1) +  " is controlled by player " + map.getCountryList().get(i).getPlayer() + " and has " + map.getCountryList().get(i).getArmies() + " armies.");
		}
	}
}
