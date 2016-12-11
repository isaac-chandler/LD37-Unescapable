package ld37;

import ld37.state.PlayState;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImageObject extends BoxObject {
	private float xScale = 1, yScale = 1, xTileCount = 1, yTileCount = 1;
	private Image image;

	public ImageObject(float x, float y, String name) {
		box.x = x;
		box.y = y;
		this.image = Images.get(name);
		updateRect();
	}

	@Override
	public void render(PlayState play, Graphics g) {
		image.draw(box.x, box.y, box.x2, box.y2, 0, 0, image.getWidth() * xTileCount, image.getHeight() * yTileCount, g.getColor());
	}

	public float getX() {
		return box.x;
	}

	public void setX(float x) {
		box.x = x;
		updateRect();
	}

	public float getY() {
		return box.y;
	}

	public void setY(float y) {
		box.y = y;
		updateRect();
	}

	public float getxScale() {
		return xScale;
	}

	public void setxScale(float xScale) {
		this.xScale = xScale;
		updateRect();
	}

	public float getyScale() {
		return yScale;
	}

	public void setyScale(float yScale) {
		this.yScale = yScale;
		updateRect();
	}

	public float getxTileCount() {
		return xTileCount;
	}

	public void setxTileCount(float xTileCount) {
		this.xTileCount = xTileCount;
		updateRect();
	}

	public float getyTileCount() {
		return yTileCount;
	}

	public void setyTileCount(float yTileCount) {
		this.yTileCount = yTileCount;
		updateRect();
	}

	public void setName(String name) {
		this.image = Images.get(name);
		updateRect();
	}

	private void updateRect() {
		box.x2 = box.x + image.getWidth() * xScale * xTileCount;
		box.y2 = box.y + image.getHeight() * yScale * yTileCount;
	}

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
//		System.out.println(image.getResourceReference());
	}

	public void setScale(float scale) {
		this.xScale = scale;
		this.yScale = scale;
		updateRect();
	}
}
