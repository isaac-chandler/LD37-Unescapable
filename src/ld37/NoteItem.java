package ld37;

import ld37.state.InventoryState;
import ld37.state.StartState;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class NoteItem extends Item {

	public static final String START_NOTE = "Escape the room\nFind all 5 keys\n\nClick to continue";
	public static final float  START_SIZE = 0.02f;

	public static final String BOOK_NOTE = "Red Yellow Green Black";
	public static final String BOOK_HIDDEN_NOTE = "Which is the smallest?\nHow many words in each\ntitle?";
	public static final float  BOOK_SIZE = 0.015f;

	public static final String COUCH_NOTE = "Underneath couch pillow";
	public static final String COUCH_HIDDEN_NOTE = "and table";
	public static final float  COUCH_SIZE = 0.015f;

	public static final String PILLOW_NOTE = "Invisible ink";
	public static final float  PILLOW_SIZE = 0.02f;

	public static final String TABLE_NOTE = "Underneath couch";
	public static final float  TABLE_SIZE = 0.02f;


	public static final NoteItem START = new NoteItem(START_NOTE, START_SIZE);
	public static final InkItem BOOK = new InkItem(BOOK_NOTE, BOOK_HIDDEN_NOTE, BOOK_SIZE);
	public static final InkItem COUCH = new InkItem(COUCH_NOTE, COUCH_HIDDEN_NOTE, COUCH_SIZE);
	public static final InkItem TABLE = new InkItem("", TABLE_NOTE, TABLE_SIZE);
	public static final NoteItem PILLOW = new NoteItem(PILLOW_NOTE, PILLOW_SIZE);

	private String text;
	private float size;

	public NoteItem(String text, float size) {
		this.text = text;
		this.size = size;
	}

	@Override
	public void render(InventoryState inventory, Graphics g) {
		g.drawImage(StartState.writtenPaper, 0, 0, 55, 55, 0, 0, 64, 64);
	}

	@Override
	public void renderInspectedImpl(InventoryState state, Graphics g) {
		g.drawImage(StartState.paper, 0, 0, 55, 55, 0, 0, 64, 64);
		g.setColor(Color.black);
		Draw.string(8, 2, size, text, g);
		g.setColor(Color.white);
	}
}
