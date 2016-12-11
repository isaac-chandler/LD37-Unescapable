package ld37;

import ld37.state.InventoryState;
import org.newdawn.slick.Graphics;

public abstract class Item {

	private boolean inspected = false;


	public abstract void render(InventoryState inventory, Graphics g);

	protected abstract void renderInspectedImpl(InventoryState state, Graphics g);

	public void renderInspected(InventoryState state, Graphics g) {
		inspected = true;
		renderInspectedImpl(state, g);
	}

	public boolean isInspected() {
		return inspected;
	}
}
