package ai;

import game.Risk;

public abstract class AI {
	Risk game;
	int player;
	public AI(Risk game, int player){
		this.game = game;
		this.player = player;
		System.out.println("Player " + player + " created.");
	}
	public abstract void placeUnit();
	public abstract void placeReinforcements();
	public abstract void attackPhase();
	public abstract void fortify();
}
