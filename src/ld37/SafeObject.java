package ld37;

import ld37.state.PlayState;

public class SafeObject extends ImageObject {

	private int stateID;

	public SafeObject(float x, float y, int stateID) {
		super(x, y, "safe");
		this.stateID = stateID;
	}

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
		Game.getGame().enterState(stateID);
	}
}
