package ld37.state;

import ld37.Draw;
import ld37.Item;
import ld37.Game;
import ld37.KeyItem;
import ld37.NoteItem;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class InventoryState extends IDState {


	public List<Item> items = new ArrayList<Item>(1);

	public InventoryState(int id) {
		super(id);
	}

	/**
	 * Initialise the state. It should load any resources it needs at this stage
	 *
	 * @param container The container holding the game
	 * @param game      The game holding this state
	 * @throws SlickException Indicates a failure to initialise a resource for this state
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		items.add(NoteItem.START);
	}

	private static final Color inspectColor = new Color(0, 0, 0, 0.5f);

	/**
	 * Render this state to the game's graphics context
	 *
	 * @param container The container holding the game
	 * @param game      The game holding this state
	 * @param g         The graphics context to render to
	 * @throws SlickException Indicates a failure to render an artifact
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Draw.preRender(g);

		GL11.glPushMatrix();
		GL11.glTranslatef(12.5f, 2.5f, 0);
		for (int y = 0; y < (items.size() + 4) / 5; y++) {
			GL11.glPushMatrix();
			for (int x = 0; x < 5; x++) {
				if (y * 5 + x >= items.size())
					break;

				items.get(y * 5 + x).render(this, g);
				GL11.glTranslatef(60, 0, 0);
			}
			GL11.glPopMatrix();
			GL11.glTranslatef(0, 60, 0);
		}
		GL11.glPopMatrix();

		if (inspecting != null) {
			g.setColor(inspectColor);
			g.fillRect(0, 0, 320, 180);

			g.setColor(Color.white);

			GL11.glPushMatrix();
			GL11.glTranslatef(77.5f, 7.5f, 0);
			GL11.glScalef(3, 3, 1);

			inspecting.renderInspected(this, g);

			GL11.glPopMatrix();
		}

		Draw.postRender();
	}

	/**
	 * Update the state's logic based on the amount of time thats passed
	 *
	 * @param container The container holding the game
	 * @param game      The game holding this state
	 * @param delta     The amount of time thats passed in millisecond since last update
	 * @throws SlickException Indicates an internal error that will be reported through the
	 *                        standard framework mechanism
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		PlayState.clock += delta;

		if (closeInventory) {
			closeInventory = false;
			game.enterState(Game.PLAY_STATE_ID);
		}
	}

	private boolean closeInventory = false;
	private Item inspecting = null;

	@Override
	public void mousePressed(int button, int x, int y) {
		float fx = x / Game.scale;
		float fy = y / Game.scale;

		if (inspecting == null) {
			if (fx >= 10f && fy <= 310f) {
				fx -= 10f;
				int xpos = (int) (fx / 60);
				int ypos = (int) (fy / 60);
				int idx = ypos * 5 + xpos;
				if (idx < items.size())
					inspecting = items.get(idx);
			}
		} else {
			if (fx < 77.5f || fy < 7.5f || fx > 242.5f || fy > 172.5f) {
				inspecting = null;
			}
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_ESCAPE) {
			if (inspecting == null) {
				closeInventory = true;
			} else {
				inspecting = null;
			}
		} else if (key == Keyboard.KEY_I) {
			inspecting = null;
			closeInventory = true;
		}
	}

	public int getKeyCount() {
		int count = 0;
		for (Item item : items) {
			if (item instanceof KeyItem)
				count++;
		}
		return count;
	}
}
