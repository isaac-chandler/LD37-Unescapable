package ld37;

import ld37.state.SafeState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class ColorSafeState extends SafeState {
	public ColorSafeState(int id) {
		super(id);
	}

	@Override
	protected void renderClosed(LockState state, GameContainer container, StateBasedGame game, Graphics g) {
		switch (state) {
			case CORRECT:
				g.setColor(new Color(100, 150, 100));
				break;
			case INCORRECT:
				g.setColor(new Color(180, 60, 60));
				break;
			case INCOMPLETE:
			default:
				g.setColor(Color.black);
				break;
		}

		g.fillRect(5, 3, 22, 5);

		g.setColor(Color.yellow);
		g.fillRect(7, 9, 8, 8);

		g.setColor(Color.red);
		g.fillRect(17, 9, 8, 8);

		g.setColor(Color.green);
		g.fillRect(7, 19, 8, 8);

		g.setColor(Color.black);
		g.fillRect(17, 19, 8, 8);

		g.setColor(Color.white);
	}

	@Override
	protected void reset() {

	}

	private static final Box[] boxes = new Box[] {
			new Box(7, 9, 15, 17),
			new Box(17, 9, 25, 17),
			new Box(7, 19, 15, 27),
			new Box(17, 19, 25, 27),
	};

	@Override
	protected LockState panelPressed(int button, float x, float y) {
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i].contains(x, y)) {
				if (i == 0)
					return LockState.CORRECT;
				else
					return LockState.INCORRECT;
			}
		}

		return LockState.INCOMPLETE;
	}

	private static String setCharAt(String s, int index, char character) {
		return s.substring(0, index) + character + s.substring(index + 1);
	}
}
