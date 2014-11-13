package ai;

import game.Risk;

public abstract class AI {
	Risk game;
	public AI(Risk game){
		this.game = game;
	}
	public abstract void placeUnit();
	public abstract void takeTurn();
}
