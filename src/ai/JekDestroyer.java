package ai;

import java.util.ArrayList;

import game.Risk;
import map.Map;
import map.Country;

public class JekDestroyer extends AI {

	private Map worldMap;
	private ArrayList<ArrayList<Country>> ContinentList;
	private ArrayList<Connection> ConnectionList;
	private Country ArmyBase;
	private int continentFocus;

	public JekDestroyer(Risk game, int player) {
		super(game, player);

		worldMap = game.getMap();
		continentSetUp();
		placeUnitSetUp();
	}

	public void continentSetUp() {

		ContinentList = new ArrayList<>();
		ConnectionList = new ArrayList<>();

		ArrayList<Country> setUpMap = worldMap.getCountryList();
		int continentNumber = -1;
		for (int i = 0; i < setUpMap.size(); i++) {
			if (setUpMap.get(i).getContinent() != continentNumber) {
				ContinentList.add(new ArrayList<Country>());
				continentNumber = setUpMap.get(i).getContinent();
			}

			ContinentList.get(continentNumber).add(setUpMap.get(i));

			for (int j = 0; j < setUpMap.get(i).getBorders().size(); j++) {
				if (setUpMap.get(i).getContinent() != setUpMap.get(i).getBorders().get(j).getContinent()) {
					ConnectionList.add(new Connection(setUpMap.get(i), setUpMap.get(i).getBorders().get(j)));
				}
			}
		}
	}
	
	public void placeUnitSetUp(){
		ArrayList<Country> largest = new ArrayList<>();
		int size = 0;
		int mostEnemies = 0;
		
		for (int i = 0; i < ContinentList.size(); i++) {
			if (ContinentList.get(i).size() > size) {
				largest = ContinentList.get(i);
				size = ContinentList.get(i).size();
			}
		}
		
		for (int i = 0; i < largest.size(); i++) {
			if (largest.get(i).getPlayer() == player) {
				if (largest.get(i).getBorders().size() >= 5) {
					ArmyBase = largest.get(i);
					i = 1000;
				} else if (largest.get(i).getBorders().size() > mostEnemies) {
					ArmyBase = largest.get(i);
					mostEnemies = largest.get(i).getBorders().size();
				}
			}
		}
		
		ArrayList<Country> armyBase = new ArrayList<>();
		armyBase.add(ArmyBase);
		ArrayList<Integer> armies = new ArrayList<>();
		armies.add(game.getMap().getReinforcements(player));
		//Units are meant to be placed one at a time during the starting unit placement
		//game.placeReinforcements(armyBase, armies, player);
		continentFocus = ArmyBase.getContinent();		
	}

	@Override
	public void placeUnit() {
		game.placeUnit(ArmyBase);
	}

	@Override
	public void placeReinforcements() {
		if (ArmyBase.getPlayer() == player) {
			for (int i = 0; i < ArmyBase.getBorders().size(); i++) {
				if (ArmyBase.getBorders().get(i).getPlayer() != player) {
					i = 10000;

					ArrayList<Country> armyBase = new ArrayList<>();
					armyBase.add(ArmyBase);
					ArrayList<Integer> armies = new ArrayList<>();
					armies.add(game.getMap().getReinforcements(player));

					game.placeReinforcements(armyBase, armies, player);
				}
			}
		} else {
			boolean friendlyAdj = false;
			for (int i = 0; i < ArmyBase.getBorders().size(); i++) {
				if (ArmyBase.getBorders().get(i).getPlayer() == player) {
					friendlyAdj = true;
					ArmyBase = ArmyBase.getBorders().get(i);

					ArrayList<Country> armyBase = new ArrayList<>();
					armyBase.add(ArmyBase);
					ArrayList<Integer> armies = new ArrayList<>();
					armies.add(game.getMap().getReinforcements(player));

					//this sometimes happens more than once per turn
					game.placeReinforcements(armyBase, armies, player);
				}
			}
			if (friendlyAdj == false) {

				ArrayList<Country> largest = ContinentList.get(ArmyBase.getContinent());
				int mostEnemies = 0;

				for (int i = 0; i < largest.size(); i++) {
					if (largest.get(i).getPlayer() == player) {
						if (largest.get(i).getBorders().size() >= 5) {
							ArmyBase = largest.get(i);
							i = 1000;
						} else if (largest.get(i).getBorders().size() > mostEnemies) {
							ArmyBase = largest.get(i);
							mostEnemies = largest.get(i).getBorders().size();
						}
					}
				}

				ArrayList<Country> armyBase = new ArrayList<>();
				armyBase.add(ArmyBase);
				ArrayList<Integer> armies = new ArrayList<>();
				armies.add(game.getMap().getReinforcements(player));

				game.placeReinforcements(armyBase, armies, player);
			}
		}
	}

	public boolean updateArmyBase(Country possibleVictory) {
		if (possibleVictory.getPlayer() == player) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void attackPhase() {
		boolean ableToAttack = true;

		while (ableToAttack) {
			int minArmies = 10000;
			Country possibleAttack = ArmyBase;
			boolean enemiesNear = true;
			//what you had
			//for (int j = 0; j < ArmyBase.getBorders().get(j).getPlayer(); j++){
			//what I think you meant
			for (int j = 0; j < ArmyBase.getBorders().size(); j++) {
				if (ArmyBase.getBorders().get(j).getPlayer() != player && ArmyBase.getBorders().get(j).getContinent() == continentFocus) {
					enemiesNear = true;
				} else {
					ableToAttack = false;
				}
				if (enemiesNear) {
					for (int i = 0; i < ArmyBase.getBorders().size(); i++) {
						if (ArmyBase.getBorders().get(i).getContinent() == continentFocus) {
							if (ArmyBase.getBorders().get(i).getPlayer() != player) {
								if (ArmyBase.getBorders().get(i).getArmies() == 1) {
									possibleAttack = ArmyBase.getBorders().get(i);
									i = 10000;
								} else if (ArmyBase.getBorders().get(i).getArmies() < minArmies) {
									possibleAttack = ArmyBase.getBorders().get(i);
									minArmies = ArmyBase.getBorders().get(i).getArmies();
								}
							}
						}
					}
					//what you had
					//game.attack(possibleAttack, ArmyBase,ArmyBase.getArmies() - 2);
					//what I think you meant
					while(ArmyBase.getArmies() > 2 && possibleAttack.getPlayer() != player){
						game.attack(ArmyBase,possibleAttack,ArmyBase.getArmies() - 2);
					}
					if (updateArmyBase(possibleAttack)) {
						for (int i = 0; i < possibleAttack.getBorders().size(); i++) {
							if (possibleAttack.getBorders().get(i).getPlayer() != player) {
								ArmyBase = possibleAttack;
								i = 10000;
							}
						}
						if (ArmyBase != possibleAttack) {
							if (possibleAttack.getArmies() > 3){
								//HACKS I'm not sure what you're doing here. The attack method moves units to areas you conquer automatically though.
								//game.Fortify(possibleAttack, ArmyBase,possibleAttack.getArmies() - 2);
							}
						}
					} else {
						ableToAttack = false;
					}

					if (ArmyBase.getArmies() < 2) {
						ableToAttack = false;
					}

				}
			}
		}
	}

	@Override
	public void fortify() {
		for(int i = 0; i < ArmyBase.getBorders().size(); i++){
			if(ArmyBase.getBorders().get(i).getPlayer() != player){
				return;
			}
		}

		boolean taken = true;	
		Country REBELS = ArmyBase;
		for(int i = 0; i < ContinentList.get(continentFocus).size(); i++){
			if(ContinentList.get(continentFocus).get(i).getPlayer() != player){
				taken = false;
				REBELS = ContinentList.get(continentFocus).get(i);
				for(int j = 0; j < REBELS.getBorders().size(); j++){
					if(REBELS.getBorders().get(j).getPlayer() == player && game.getMap().isConnected(ArmyBase, REBELS)){
						game.Fortify(ArmyBase, REBELS.getBorders().get(j), ArmyBase.getArmies() - 2);
						ArmyBase = REBELS.getBorders().get(j);
						return;
					}
				}
			}
		}
		if(taken){
			for(int i = 0; i < ConnectionList.size(); i++){
				if(ConnectionList.get(i).getContinent1() == continentFocus){
					for(int j = 0; j < ContinentList.get(ConnectionList.get(i).getContinent2()).size(); i++){
						if(ContinentList.get(ConnectionList.get(i).getContinent2()).get(j).getPlayer() != player){
							continentFocus = ConnectionList.get(i).getContinent2();
							game.Fortify(ArmyBase, ConnectionList.get(i).getCountry1(), ArmyBase.getArmies() - 2);
							ArmyBase = ConnectionList.get(i).getCountry1();
							return;
						}

					}
				}
			}
		}
	}
}
