package ld37.state;

import ld37.Draw;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EscapedState extends IDState {
	public EscapedState(int id) {
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

		long time = PlayState.clock / 10;
		int hundreds = (int) (time % 100);
		time /= 100;
		int seconds = (int) (time % 60);
		time /= 60;
		int minutes = (int) time;

		Draw.string(100, 80, 0.05f, String.format("Congratulations! You escaped in %d:%02d.%02d\n\nPress escape to exit", minutes, seconds, hundreds), g);

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
		if (exit)
			container.exit();
	}

	private boolean exit = false;

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_ESCAPE)
			exit = true;

	}
}
