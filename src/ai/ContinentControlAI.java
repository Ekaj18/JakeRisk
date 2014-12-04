package ai;

import java.util.ArrayList;

import map.Country;
import game.Risk;

public class ContinentControlAI extends AI {
	int startContinent = 0;
	public ContinentControlAI(Risk game, int player) {
		super(game, player);
		double[][] continents = new double[game.getMap().getContinentBonuses().size()][2];
		for(int i = 0; i < continents.length; i++){
			continents[i][0] = 0;
			continents[i][1] = 0;
		}
		for(int j = 0; j < game.getMap().getCountryList().size(); j++){
			if(game.getMap().getCountryList().get(j).getPlayer() == player){
				continents[game.getMap().getCountryList().get(j).getContinent()][0]++;
			}
			continents[game.getMap().getCountryList().get(j).getContinent()][1]++;
		}
		for(int k = 0; k < continents.length; k++){
			if(continents[k][0]/continents[k][1] > continents[startContinent][0]/continents[startContinent][1]){
				startContinent = k;
			}
		}
	}

	@Override
	public void placeUnit() {
		Country country = new Country(-1);;
		int least = 0;
		for(int i = 0; i < game.getMap().getCountryList().size(); i++){
			if((least == 0 || game.getMap().getCountryList().get(i).getArmies() <= least) && 
					game.getMap().getCountryList().get(i).getContinent() == startContinent && 
					game.getMap().getCountryList().get(i).getPlayer() == player){
				least = game.getMap().getCountryList().get(i).getArmies();
				country = game.getMap().getCountryList().get(i);
			}
		}
		game.placeUnit(country);
	}

	@Override
	public void placeReinforcements() {
		ArrayList<Country> countries = new ArrayList<>();
		ArrayList<Integer> armies = new ArrayList<>();
		int reinforcements = game.getMap().getReinforcements(player);
		if(totalControl(startContinent)){
			//probably change this later
			for(int i = 0; i < game.getMap().getCountryList().size(); i++){
				if(game.getMap().getCountryList().get(i).getPlayer() == player){
					countries.add(game.getMap().getCountryList().get(i));
					armies.add(0);
				}
			}	
		}
		else{
			for(int i = 0; i < game.getMap().getCountryList().size(); i++){
				if(game.getMap().getCountryList().get(i).getContinent() == startContinent && game.getMap().getCountryList().get(i).getPlayer() == player){
					countries.add(game.getMap().getCountryList().get(i));
					armies.add(0);
				}
			}
			if(countries.size() == 0){
				for(int i = 0; i < game.getMap().getCountryList().size(); i++){
					if(game.getMap().getCountryList().get(i).getPlayer() == player){
						countries.add(game.getMap().getCountryList().get(i));
						armies.add(0);
					}
				}				
			}
		}
		//when totalControl if is changed move this while into the above else statement
		while(reinforcements > 0){
			int least = 0, leastIndex = 0;
			for(int i = 0; i < countries.size(); i++){
				if(least == 0 || least > countries.get(i).getArmies() + armies.get(i)){
					leastIndex = i;
					least = countries.get(i).getArmies() + armies.get(i);
				}
			}
			armies.set(leastIndex, armies.get(leastIndex)+1);
			reinforcements--;
		}
		game.placeReinforcements(countries, armies, player);
	}

	private boolean totalControl(int continent) {
		for(int i = 0; i < game.getMap().getCountryList().size(); i++){
			if(game.getMap().getCountryList().get(i).getContinent() == continent && game.getMap().getCountryList().get(i).getPlayer() != player){
				return false;
			}
		}
		return true;
	}

	@Override
	public void attackPhase() {
		boolean done = false;
		if(totalControl(startContinent)){
			//eventually change this to be more effective
			while(!done){
				done = true;
				for(int i = 0; i < game.getMap().getCountryList().size(); i++){
					Country cur = game.getMap().getCountryList().get(i);
					if(cur.getPlayer() == player && cur.getArmies() > 1){
						for(int j = 0; j < cur.getBorders().size(); j++){
							if(cur.getBorders().get(j).getPlayer() != player){
								game.attack(cur, cur.getBorders().get(j), cur.getArmies()-1);
								if(cur.getArmies() <= 1){
									j = cur.getBorders().size();
								}
								done = false;
							}
						}
					}
				}
			}
		}
		else{
			while(!done){
				done = true;
				for(int i = 0; i < game.getMap().getCountryList().size(); i++){
					Country cur = game.getMap().getCountryList().get(i);
					if(cur.getContinent() == startContinent && cur.getPlayer() == player && cur.getArmies() > 1){
						for(int j = 0; j < cur.getBorders().size(); j++){
							if(cur.getBorders().get(j).getPlayer() != player){
								game.attack(cur, cur.getBorders().get(j), cur.getArmies()-1);
								if(cur.getArmies() <= 1){
									j = cur.getBorders().size();
								}
								done = false;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void fortify() {
		Country first = new Country(-1);
		Country second = new Country(-1);
		int mostDanger = 0;
		for(int i = 0; i < game.getMap().getCountryList().size(); i++){
			if(game.getMap().getCountryList().get(i).getPlayer() == player){
				boolean friendlyBorders = true;
				int danger = 0;
				for(int j = 0; j < game.getMap().getCountryList().get(i).getBorders().size(); j++){
					if(game.getMap().getCountryList().get(i).getBorders().get(j).getPlayer() != player){
						friendlyBorders = false;
					}
					else{
						danger += game.getMap().getCountryList().get(i).getBorders().get(j).getArmies();
					}
				}				
				if(friendlyBorders){
					first = game.getMap().getCountryList().get(i);
				}
				else if(danger > mostDanger){
					second = game.getMap().getCountryList().get(i);
					mostDanger = danger;
				}
			}
		}
		if(first.getArmies() > 1){
			game.Fortify(first, second, first.getArmies()-1);
		}
	}
}
