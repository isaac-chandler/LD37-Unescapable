package ld37;

import ld37.state.InventoryState;
import ld37.state.StartState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class InkItem extends Item {

	public static final Image inkPaper = Images.get("paper_ink");

	public boolean revealed = false;
	private String text;
	private String hiddenText;
	private float size;

	public InkItem(String text, String hiddenText, float size) {
		this.text = text;
		this.size = size;
		this.hiddenText = hiddenText;
	}

	@Override
	public void render(InventoryState inventory, Graphics g) {
		if (revealed)
			g.drawImage(inkPaper, 0, 0, 55, 55, 0, 0, 64, 64);
		else
			g.drawImage(StartState.writtenPaper, 0, 0, 55, 55, 0, 0, 64, 64);
	}

	@Override
	public void renderInspectedImpl(InventoryState state, Graphics g) {
		g.drawImage(StartState.paper, 0, 0, 55, 55, 0, 0, 64, 64);
		g.setColor(Color.black);
		Draw.string(8, 2, size, text, g);
		if (revealed) {
			g.setColor(new Color(68, 77, 116));
			Draw.string(8, 10, size, hiddenText, g);
		}
		g.setColor(Color.white);
	}
}
