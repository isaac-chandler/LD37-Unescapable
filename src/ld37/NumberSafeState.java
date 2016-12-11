package ld37;

import ld37.state.SafeState;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class NumberSafeState extends SafeState {
	public NumberSafeState(int id) {
		super(id);
	}

	private int pointer = 2;
	private String current = "  -  -  -  - ";

	private static final String CORRECT = "  2  4  4  3 ";

	@Override
	protected void renderClosed(LockState state, GameContainer container, StateBasedGame game, Graphics g) {
		switch (state) {
			case CORRECT:
				g.setColor(Color.green);
				break;
			case INCORRECT:
				g.setColor(Color.red);
				break;
			case INCOMPLETE:
			default:
					break;
		}

		Draw.string(5, 3, 0.02f, current, g);

		g.setColor(Color.white);
		Draw.string(7, 9, 0.02f, " 1", g);
		Draw.string(14, 9, 0.02f, " 2", g);
		Draw.string(21, 9, 0.02f, " 3", g);
		Draw.string(7, 15, 0.02f, " 4", g);
		Draw.string(14, 15, 0.02f, " 5", g);
		Draw.string(21, 15, 0.02f, " 6", g);
		Draw.string(7, 21, 0.02f, " 7", g);
		Draw.string(14, 21, 0.02f, " 8", g);
		Draw.string(21, 21, 0.02f, " 9", g);
		Draw.string(14, 27, 0.02f, " 0", g);
	}

	@Override
	protected void reset() {
		current = "  -  -  -  - ";
		pointer = 2;
	}

	private static final Box[] boxes = new Box[] {
			new Box(14, 27, 18, 31),
			new Box(7, 9, 11, 13),
			new Box(14, 9, 18, 13),
			new Box(21, 9, 25, 13),
			new Box(7, 15, 11, 19),
			new Box(14, 15, 18, 19),
			new Box(21, 15, 25, 19),
			new Box(7, 21, 11, 25),
			new Box(14, 21, 18, 25),
			new Box(21, 21, 25, 25),
	};

	@Override
	protected LockState panelPressed(int button, float x, float y) {
		for (int i = 0; i < 10; i++) {
			if (boxes[i].contains(x, y)) {
				current = setCharAt(current, pointer, (char) (i + 0x30));

				pointer += 3;
				if (pointer > 11) {
					if (current.equals(CORRECT)) {
						return LockState.CORRECT;
					} else {
						return LockState.INCORRECT;
					}
				} else {
					return LockState.INCOMPLETE;
				}
			}
		}
		return LockState.INCOMPLETE;
	}

	private static String setCharAt(String s, int index, char character) {
		return s.substring(0, index) + character + s.substring(index + 1);
	}
}
