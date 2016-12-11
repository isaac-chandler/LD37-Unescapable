package ld37;

import ld37.state.PlayState;

public abstract class MenuObject extends ImageObject {
	public MenuObject(float x, float y, String name) {
		super(x, y, name);
	}

	public abstract Menu createMenu(PlayState play, float x, float y);

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
		play.menu = createMenu(play, box.x + x, box.y + y);
	}
}
