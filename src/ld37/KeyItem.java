package ld37;

import ld37.state.InventoryState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class KeyItem extends Item {

	private static Image key = Images.get("key");

	@Override
	public void render(InventoryState inventory, Graphics g) {
		g.drawImage(key, 0, 0, 55, 55, 0, 0, 8, 8);
	}

	@Override
	public void renderInspectedImpl(InventoryState state, Graphics g) {
		render(state, g);
	}
}
