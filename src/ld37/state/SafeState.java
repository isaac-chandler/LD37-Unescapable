package ld37.state;

import ld37.Draw;
import ld37.Game;
import ld37.Images;
import ld37.KeyItem;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class SafeState extends IDState {
	public boolean open = false;
	public Image safeZoom;
	public Image safeOpen;
	public Image key;
	public boolean keyTaken = false;

	public SafeState(int id) {
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
		safeZoom = Images.get("safe_zoom");
		safeOpen = Images.get("safe_open");
		key = Images.get("key");
	}

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

		if (open) {
			g.drawImage(safeOpen, 96, 26, 224, 164, 0, 0, 32, 32);
			if (!keyTaken) {
				g.drawImage(key, 144, 122, 176, 154, 0, 0, 8, 8);
			}
		} else {
			g.drawImage(safeZoom, 96, 26, 224, 164, 0, 0, 32, 32);
			GL11.glPushMatrix();
			GL11.glTranslatef(96, 26, 0);
			GL11.glScalef(4, 4, 1);
			renderClosed(state, container, game, g);
			GL11.glPopMatrix();
		}

		Draw.postRender();
	}

	protected abstract void renderClosed(LockState state, GameContainer container, StateBasedGame game, Graphics g);

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

		if (closeMenu) {
			closeMenu = false;
			game.enterState(Game.PLAY_STATE_ID);
			return;
		}

		if (state != LockState.INCOMPLETE && cooldown > 0) {
			cooldown -= delta;
		}

		if (cooldown <= 0) {
			if (state == LockState.CORRECT) {
				open = true;
			}
			state = LockState.INCOMPLETE;
			cooldown = 1;
			reset();
		}
	}

	protected abstract void reset();

	private boolean closeMenu = false;

	private LockState state = LockState.INCOMPLETE;
	private int cooldown = 1;

	protected enum LockState {
		CORRECT, INCORRECT, INCOMPLETE
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (open && !keyTaken) {
			keyTaken = true;
			Game.getGame().inventory.items.add(new KeyItem());
		} else if (state == LockState.INCOMPLETE) {
			state = panelPressed(button, (x / Game.scale - 96) / 4f, (y / Game.scale - 26) / 4f);
			if (state == LockState.CORRECT || state == LockState.INCORRECT) {
				cooldown = 500;
			}
		}
	}

	protected abstract LockState panelPressed(int button, float x, float y);

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_ESCAPE)
			closeMenu = true;
	}
}
