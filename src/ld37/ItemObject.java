package ld37;

import ld37.state.PlayState;

public class ItemObject extends ImageObject{

	private Item item;
	private PlayState.Message message;

	public ItemObject(float x, float y, String name, Item item, PlayState.Message message) {
		super(x, y, name);
		this.item = item;
		this.message = message;
	}

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
		if (Game.getGame().inventory.items.add(item)) {
			play.objects.remove(this);
			play.messages.add(message);
		}
	}
}
