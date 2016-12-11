package ld37.state;

import ld37.Draw;
import ld37.Images;
import ld37.Game;
import ld37.NoteItem;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class StartState extends IDState {

	public static Image paper;
	public static Image writtenPaper;

	public StartState(int id) {
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
		paper = Images.get("paper");
		writtenPaper = Images.get("paper_written");
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
		GL11.glPushMatrix();
		GL11.glTranslatef(77.5f, 7.5f, 0);
		GL11.glScalef(3, 3, 1);

		NoteItem.START.renderInspectedImpl(null, g);

		GL11.glPopMatrix();
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
		if (clicked)
			game.enterState(Game.PLAY_STATE_ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
	}

	private boolean clicked = false;

	@Override
	public void mousePressed(int button, int x, int y) {
		clicked = true;
	}
}
