package game;

import ai.AI;
import ai.ContinentControlAI;
//import ai.JekDestroyer;
import map.Map;
import mapgeneration.makeDefaultMap;

public class RiskDriver {
	public static void main(String[] args){
		new RiskDriver();
	}
	public RiskDriver(){
		int players = 4, startingUnits = 30;
		Map map = makeDefaultMap.makeMap();
		Risk game = new Risk(map, players);
		game.randomStart();
		int curPlayer = 0, round = 0;
		boolean[] eliminated = new boolean[players];
		AI[] ai = new AI[players];
		for(int i = 0; i < ai.length; i++){
			ai[i] = new ContinentControlAI(game, i);
			eliminated[i] = false;
		}
		//ai[0] = new ContinentControlAI(game,0);
		//ai[1] = new JekDestroyer(game,1);
		
		startingUnits -= game.countriesControlled(players-1);
		for(int i = 0; i < startingUnits; i++){
			for(int j = 0; j < players; j++){
				if(i == startingUnits-1){
					if(game.countriesControlled(j) == game.countriesControlled(players-1)){
						ai[j].placeUnit();
					}
				}
				else{
					ai[j].placeUnit();
				}
			}
		}
		System.out.println("Starting units placed.");
		for(int i = 0; i < players; i++){
			System.out.println("Player " + i + " currently controls " + game.countriesControlled(i) + " countries, with a total of " + game.armiesOwned(i) + " armies.");
		}
		curPlayer = -1;
		do{
			/*try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}*/
			curPlayer++;
			curPlayer %= players;
			if(curPlayer == 0){
				System.out.println("\nRound " + round + ".");
				round++;
			}
			if(!eliminated[curPlayer]){
				System.out.println("\nPlayer " + curPlayer + "'s turn.");
				System.out.println("Player " + curPlayer + " currently controls " + game.countriesControlled(curPlayer) + " countries, with a total of " + game.armiesOwned(curPlayer) + " armies.");
				System.out.println("Player " + curPlayer + " gets " + game.getMap().getReinforcements(curPlayer) + " reinforcements.");
				ai[curPlayer].placeReinforcements();
				ai[curPlayer].attackPhase();
				ai[curPlayer].fortify();
			}
			for(int i = 0; i < eliminated.length; i++){
				if(!eliminated[i] && game.countriesControlled(i) == 0){
					System.out.println("\nPlayer " + curPlayer + " eliminated player " + i + ".");
					eliminated[i] = true;
				}
			}
		}while(!game.isWinner(curPlayer) && round < 10000);
		System.out.println("\nPlayer " + curPlayer + " wins!");
		
	}
}
