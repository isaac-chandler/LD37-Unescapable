package ld37.state;

import org.newdawn.slick.state.BasicGameState;

public abstract class IDState extends BasicGameState {

	private int id;

	public IDState(int id) {
		this.id = id;
	}

	@Override
	public int getID() {
		return id;
	}
}
