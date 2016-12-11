package ld37;

import ld37.state.InventoryState;
import ld37.state.PlayState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class BookItem extends Item {

	public static final BookItem RED_BOOK = new BookItem("red", "Opposite sides", 0.02f, 15, 8, 0, 8);
	public static final BookItem GREEN_BOOK = new BookItem("green", "Of  mice and men", 0.02f, 15, 8, 0, 3);
	public static final BookItem BLUE_BOOK = new BookItem("blue", "Biography of Barenziah \nVol. I-III", 0.014f, 13, 8, 0, 9);
	public static final BookItem BLACK_BOOK = new BookItem("black", "Carpet: A History", 0.02f, 10, 3, 0, 6);
	public static final BookItem YELLOW_BOOK = new BookItem("yellow", "Mystery on Corner St.", 0.013f, 16, 13, 10, 17);

	private String text;
	private float size;
	private Image image;
	private Image zoomImage;
	private float textX, textY;
	private int highLightStart, highlightEnd;
	private Color textColor = Color.black;

	public BookItem(String book, String text, float size, float textX, float textY, int highLightStart, int highlightEnd) {
		this.text = text;
		this.size = size;
		this.textX = textX;
		this.textY = textY;
		this.highLightStart = highLightStart;
		this.highlightEnd = highlightEnd;
		image = Images.get(book + "_book");
		zoomImage = Images.get(book + "_book_zoom");
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	@Override
	public void render(InventoryState inventory, Graphics g) {
		g.drawImage(image, 0, 0, 55, 55, 0, 0, 64, 64);
		inspected = true;
	}

	private boolean inspected  = false;

	@Override
	public void renderInspectedImpl(InventoryState state, Graphics g) {
		g.drawImage(zoomImage, 0, 0, 55, 55, 0, 0, 64, 64);
		g.setColor(textColor);
		String pre = text.substring(0, highLightStart);
		Draw.string(textX, textY, size, pre, g);
		String highlight = text.substring(highLightStart, highlightEnd);
		g.setColor(new Color(210, 184, 70));
		Draw.string(textX + PlayState.FONT.getWidth(pre) * size, textY, size, highlight, g);
		String post = text.substring(highlightEnd);
		g.setColor(textColor);
		Draw.string(textX + (PlayState.FONT.getWidth(pre) + PlayState.FONT.getWidth(highlight)) * size, textY, size, post, g);
		g.setColor(Color.white);
	}

	public boolean isInspected() {
		return inspected;
	}
}
