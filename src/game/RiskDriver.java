package game;

import map.Map;
import mapgeneration.makeDefaultMap;

public class RiskDriver {
	public static void main(String[] args){
		int players = 4, startingUnits = 30;
		Map map = makeDefaultMap.makeMap();
		Risk game = new Risk(map, players);
		game.randomStart();
		int curPlayer = 0;
		//AIPlayer[] ai = new AIPlayer[players];
		//populate ai
		startingUnits -= game.countriesControlled(players-1);
		for(int i = 0; i < startingUnits; i++){
			for(int j = 0; j < players; j++){
				if(i == startingUnits-1){
					if(game.countriesControlled(j) == game.countriesControlled(players-1)){
						//ai[i].placeUnit(game);
					}
				}
				else{
					//ai[i].placeUnit(game);
				}
			}
		}
		do{
			//ai[curPlayer%players].takeTurn(game)
			curPlayer++;
		}while(!game.isWinner(curPlayer%players));
		System.out.println("Player " + curPlayer%players + " wins!");
	}
}
