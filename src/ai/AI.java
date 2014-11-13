package ai;

import game.Risk;

public abstract class AI {
	Risk game;
	public AI(Risk game){
		this.game = game;
	}
	public abstract void placeUnit();
	public abstract void placeReinforcements();
	public abstract void attackPhase();
	public abstract void fortify();
}
