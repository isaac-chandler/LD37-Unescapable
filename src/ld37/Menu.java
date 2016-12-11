package ld37;

import ld37.state.PlayState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Menu implements Clickable {

	public static abstract class MenuAction {
		public abstract void clicked(PlayState play);

		private String text;
		private float width;

		public MenuAction(String text) {
			this.text = text;
			this.width = PlayState.FONT.getWidth(text);
		}

		public String getText() {
			return text;
		}

		public float getWidth() {
			return width;
		}
	}

	private MenuAction[] actions;
	private float fontSize;
	protected Box box = new Box();

	public Menu(float x, float y, float fontSize, MenuAction... actions) {
		this.fontSize = fontSize;
		this.actions = actions;
		box.x = x;
		box.y = y;
		float maxWidth = 0;
		for (MenuAction action : actions) {
			maxWidth = Math.max(maxWidth, action.getWidth() * fontSize);
		}
		box.x2 = box.x + maxWidth + 2;
		box.y2 = box.y + PlayState.FONT.getLineHeight() * fontSize * actions.length + 2;
	}

	public void render(PlayState play, Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(box.x, box.y, box.x2 - box.x, box.y2 - box.y);
		g.setColor(Color.white);
		for (int i = 0; i < actions.length; i++) {
			Draw.string(box.x + 1, box.y + 1 + PlayState.FONT.getLineHeight() * fontSize * i, fontSize, actions[i].getText(), g);
		}
	}

	public boolean mousePressed(PlayState play, int button, float x, float y) {
		if (box.contains(x, y)) {
			onClicked(play, x - box.x, y - box.y, button);
			return true;
		}
		return false;
	}

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
		if (x >= 1 && y >= 1 && x <= box.x2 - box.x - 1 && y <= box.y2 - box.y - 1) {
			x -= 1;
			y -= 1;
			int ypos = (int) (y / (PlayState.FONT.getLineHeight() * fontSize));
			if (ypos < actions.length) {
				if (x <= actions[ypos].getWidth() * fontSize) {
					actions[ypos].clicked(play);
					play.menu = null;
				}
			}
		}
	}
}
