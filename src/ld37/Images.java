package ld37;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

public class Images {

	private static Image notFound;

	static {
		try {
			notFound = new Image("res/floor.png", false, GL11.GL_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
			System.exit(2);
		}
	}

	private static final Map<String, Image> images = new HashMap<String, Image>();

	public static Image get(String name) {
		Image image = images.get(name);
		if (image == null) {
			try {
				image = new Image("res/" + name + ".png", false, GL11.GL_NEAREST);
				if (!name.equals("floor")) {
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, image.getTexture().getTextureID());
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
				}
				images.put(name, image);
			} catch (SlickException e) {
				image = notFound;
				e.printStackTrace();
			}
		}
		return image;
	}
}
