package ld37;

import ld37.state.PlayState;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;

public class Draw {
	public static void string(float x, float y, float size, String text, Graphics g) {
		String[] strings = text.split("\n");
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(size, size, 1);
		for (String s : strings) {
			g.drawString(s, 0, 0);
			GL11.glTranslatef(0, PlayState.FONT.getLineHeight(), 0);
		}
		GL11.glPopMatrix();
	}

	public static void preRender(Graphics g) {
		g.setFont(PlayState.FONT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glPushMatrix();
		GL11.glScalef(Game.scale, Game.scale, 1);
	}

	public static void postRender() {
		GL11.glPopMatrix();
	}
}
