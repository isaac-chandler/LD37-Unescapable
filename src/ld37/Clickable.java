package ld37;

import ld37.state.PlayState;

public interface Clickable {
	void onClicked(PlayState play, float x, float y, int button);
}
