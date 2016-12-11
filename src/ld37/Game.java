package ld37;

import ld37.state.EscapedState;
import ld37.state.InventoryState;
import ld37.state.PlayState;
import ld37.state.StartState;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

public class Game extends StateBasedGame {

	private static Game game;
	private static AppGameContainer gc;

	public static float scale;

	public static final int START_STATE_ID = 0;
	public static final int PLAY_STATE_ID = 1;
	public static final int INVENTORY_STATE_ID = 2;
	public static final int NUMBER_SAFE_STATE_ID = 3;
	public static final int COLOR_SAFE_STATE_ID = 4;
	public static final int ESCAPED_STATE_ID = 5;

	public final PlayState play;
	public final StartState start;
	public final InventoryState inventory;
	public final NumberSafeState numberSafe;
	public final ColorSafeState colorSafe;
	public final EscapedState escaped;

	public static Game getGame() {
		return game;
	}

	public static AppGameContainer getGC() {
		return gc;
	}

	public static void main(String[] args) {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("windows")) {
			System.setProperty("org.lwjgl.librarypath", new File("lib/natives/windows").getAbsolutePath());
		} else if (os.contains("nix") || os.contains("nux")) {
			System.setProperty("org.lwjgl.librarypath", new File("lib/natives/linux").getAbsolutePath());
		} else if (os.contains("mac")) {
			System.setProperty("org.lwjgl.librarypath", new File("lib/natives/macosx").getAbsolutePath());
		}


		game = new Game();
		try {
			gc = new AppGameContainer(game);
//			if (true) {
			if (gc.getScreenWidth() < 1600 || gc.getScreenHeight() < 900) {
				gc.setDisplayMode(1024, 576, false);
				scale = 3.2f;
			} else if (gc.getScreenWidth() < 1920 || gc.getScreenHeight() < 1080) {
				gc.setDisplayMode(1280, 720, false);
				scale = 4;
			} else {
				gc.setDisplayMode(1600, 900, false);
				scale = 5;
			}
			gc.setTargetFrameRate(120);
			gc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public Game() {
		super("LD 37");
		start = new StartState(START_STATE_ID);
		play = new PlayState(PLAY_STATE_ID);
		inventory = new InventoryState(INVENTORY_STATE_ID);
		numberSafe = new NumberSafeState(NUMBER_SAFE_STATE_ID);
		colorSafe = new ColorSafeState(COLOR_SAFE_STATE_ID);
		escaped = new EscapedState(ESCAPED_STATE_ID);
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException {
		addState(start);
		addState(play);
		addState(inventory);
		addState(numberSafe);
		addState(colorSafe);
		addState(escaped);
		enterState(START_STATE_ID);
	}

	private boolean screenshot = false;

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_F1)
			screenshot = true;
		super.keyPressed(key, c);
	}

	@Override
	protected void postRenderState(GameContainer container, Graphics g) throws SlickException {
		super.postRenderState(container, g);
		if (screenshot) {
			screenshot = false;
			screenshot(container.getWidth(), container.getHeight(), g);
		}
	}

	private void screenshot(int width, int height, Graphics g) {
		File file = new File("screenshots/screenshot.png");
		int i = 1;
		while (file.exists()) {
			file = new File("screenshots/screenshot" + (i++) + ".png");
		}

		new File("screenshots").mkdirs();

		try {
			Image target = new Image(width, height);
			g.copyArea(target, 0, 0);
			ImageOut.write(target.getFlippedCopy(false, false), file.getAbsolutePath(), false);
			target.destroy();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
