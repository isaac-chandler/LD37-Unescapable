package ld37;

import ld37.state.PlayState;

public abstract class BoxObject extends GameObject implements Clickable {
	protected Box box = new Box();

	@Override
	public boolean mousePressed(PlayState play, int button, float x, float y) {
		if (box.contains(x, y)) {
			onClicked(play, x - box.x, y - box.y, button);
			return true;
		}
		return false;
	}
}
