package ld37.state;

import ld37.*;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayState extends IDState {

	public List<GameObject> objects = new CopyOnWriteArrayList<GameObject>();
	public Menu menu = null;
	public static long clock = 0;

	public static AngelCodeFont FONT;
	public MenuObject exit;
	public ImageObject shelves;
	public MenuObject couch;
	public ImageObject glow;
	public MenuObject lamp;
	public ImageObject table;
	public ImageObject tableKey;
	public ImageObject tableNote;
	public ImageObject carpet;
	public ItemObject bookNote;
	public ItemObject couchNote;
	public ItemObject pillowKey;
	public ItemObject couchKey;
	public SafeObject numberSafe;
	public SafeObject colorSafe;
	public ImageObject floor;
	public ImageObject pillow = null;
	public ImageObject pillow2 = null;
	public ItemObject pillowNote = null;
	public boolean lightOn = false;

	public PlayState(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer container, final StateBasedGame game) throws SlickException {
		FONT = new AngelCodeFont("res/font.fnt", "res/font.png");

		glow = new ImageObject(152, 93, "glow") {
			@Override
			public void render(PlayState play, Graphics g) {
				if (lightOn) {
					g.setColor(new Color(1, 1, 1, 0.3f));
					super.render(play, g);
					g.setColor(Color.white);
				}
			}

			@Override
			public boolean mousePressed(PlayState play, int button, float x, float y) {
				return false;
			}
		};
		objects.add(glow);

		exit = new MenuObject(180, 0, "keyhole") {
			@Override
			public Menu createMenu(PlayState play, float x, float y) {
				if (Game.getGame().inventory.getKeyCount() < 5) {
					return new Menu(x, y, 0.03f, new Menu.MenuAction("Need " + (5 - Game.getGame().inventory.getKeyCount()) + " more keys") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
						}
					});
				} else {
					return new Menu(x, y, 0.03f, new Menu.MenuAction("Escape!") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
							game.enterState(Game.ESCAPED_STATE_ID);
						}
					});
				}
			}
		};
		objects.add(exit);


		shelves = new BookshelfObject(0, 20, 2);
		objects.add(shelves);

		couch = new MenuObject(128, 58, "couch") {
			@Override
			public Menu createMenu(PlayState play, float x, float y) {
				if (NoteItem.COUCH.isInspected()) {
					if (pillow == null) {
						return new Menu(x, y, 0.03f, new Menu.MenuAction("Take pillow off") {
							@Override
							public void clicked(PlayState play) {
								play.menu = null;
								couch.setName("couch1");
								pillow = new ImageObject(104, 94, "pillow");
								addAbove(carpet, pillow);
								pillowNote = new ItemObject(141, 74, "paper_small", NoteItem.PILLOW, new Message("Got note", 1000));
								addAbove(couch, pillowNote);
							}
						});
					} else if (pillow2 == null) {
						return new Menu(x, y, 0.03f, new Menu.MenuAction("Take other pillow off") {
							@Override
							public void clicked(PlayState play) {
								play.menu = null;
								couch.setName("couch2");
								pillow2 = new ImageObject(98, 88, "pillow");
								addAbove(pillow, pillow2);
								pillowKey = new ItemObject(168, 77, "key", new KeyItem(), new Message("Got key", 1000));
								addAbove(couch, pillowKey);
							}
						});
					} else if (NoteItem.TABLE.isInspected() && NoteItem.TABLE.revealed) {
						return new Menu(x, y, 0.03f, new Menu.MenuAction("Move couch") {
							@Override
							public void clicked(PlayState play) {
								play.menu = null;
								couch.setY(couch.getY() - 24);
							}
						});
					}
				}
				return null;
			}
		};
		objects.add(couch);

		lamp = new MenuObject(154, 98, "lamp") {
			@Override
			public Menu createMenu(PlayState play, float x, float y) {
				if (lightOn && NoteItem.PILLOW.isInspected() && !NoteItem.BOOK.revealed) {
					return new Menu(x, y, 0.03f, new Menu.MenuAction(lightOn ? "Turn off" : "Turn on") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
							play.lightOn = !play.lightOn;
						}
					}, new Menu.MenuAction("Reveal invisible ink") {
								@Override
								public void clicked(PlayState play) {
									play.menu = null;
									NoteItem.BOOK.revealed = true;
									NoteItem.COUCH.revealed = true;
									play.messages.add(new Message("2 notes updated", 1000));
								}
							});
				} else if (lightOn && NoteItem.TABLE.isInspected() && !NoteItem.TABLE.revealed) {
					return new Menu(x, y, 0.03f, new Menu.MenuAction(lightOn ? "Turn off" : "Turn on") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
							play.lightOn = !play.lightOn;
						}
					}, new Menu.MenuAction("Reveal invisible ink") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
							NoteItem.TABLE.revealed = true;
							play.messages.add(new Message("1 note updated", 1000));
						}
					});
				} else
					return new Menu(x, y, 0.03f, new Menu.MenuAction(lightOn ? "Turn off" : "Turn on") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
							play.lightOn = !play.lightOn;
						}
					});
			}
		};
		objects.add(lamp);

		table = new MenuObject(144, 96, "table") {
			@Override
			public Menu createMenu(PlayState play, float x, float y) {
				if (NoteItem.COUCH.isInspected() && NoteItem.COUCH.revealed) {
					return new Menu(x, y, 0.03f, new Menu.MenuAction("Move table") {
						@Override
						public void clicked(PlayState play) {
							play.menu = null;
							table.setX(table.getX() + 32);
							lamp.setX(lamp.getX() + 32);
							glow.setX(glow.getX() + 32);
						}
					});
				}
				return null;
			}
		};
		objects.add(table);

		tableKey = new ItemObject(148, 100, "key", new KeyItem(), new Message("Got key", 1000));
		objects.add(tableKey);

		tableNote = new ItemObject(153, 102, "paper_small", NoteItem.TABLE, new Message("Got note", 1000));
		objects.add(tableNote);

		couchKey = new ItemObject(170, 68, "key", new KeyItem(), new Message("Got key", 1000));
		objects.add(couchKey);

		carpet = new CarpetObject(112, 48);
		objects.add(carpet);

		bookNote = new ItemObject(200, 120, "paper_small", NoteItem.BOOK, new Message("Got note", 1000));
		objects.add(bookNote);

		couchNote = new ItemObject(112, 48, "paper_small", NoteItem.COUCH, new Message("Got note", 1000));
		objects.add(couchNote);

		numberSafe = new SafeObject(100, 0, Game.NUMBER_SAFE_STATE_ID);
		numberSafe.setScale(0.5f);
		objects.add(numberSafe);

		colorSafe = new SafeObject(120, 0, Game.COLOR_SAFE_STATE_ID);
		colorSafe.setScale(0.5f);
		objects.add(colorSafe);

		floor = new ImageObject(0, 0, "floor");
		floor.setxTileCount(20);
		floor.setyTileCount(11.25f);
		objects.add(floor);

		messages.add(new Message("Press I to open inventory", -1));
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		Draw.preRender(g);


		for (int i = objects.size() - 1; i >= 0; i--) {
			objects.get(i).render(this, g);
		}

		if (menu != null)
			menu.render(this, g);

		if (currentMessage != null) {
			g.setColor(new Color(1f, 1f, 1f, (float) currentMessage.duration / currentMessage.maxDuration));
			Draw.string(5, 5, 0.06f, currentMessage.string, g);
			g.setColor(Color.white);
		}

		long time = clock / 10;
		int hundreds = (int) (time % 100);
		time /= 100;
		int seconds = (int) (time % 60);
		time /= 60;
		int minutes = (int) time;

		Draw.string(5, 160, 0.06f, String.format("%d:%02d.%02d", minutes, seconds, hundreds), g);

		Draw.postRender();
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		clock += delta;

		if (currentMessage == null)
			currentMessage = messages.poll();

		if (currentMessage != null) {
			if (currentMessage.maxDuration == -1) {
				if (openInventory)
					currentMessage = null;
			} else {
				currentMessage.duration -= delta;
				if (currentMessage.duration <= 0)
					currentMessage = null;
			}
		}

		if (openInventory) {
			openInventory = false;
			game.enterState(Game.INVENTORY_STATE_ID);
		}

		for (GameObject object : objects) {
			object.update(this, delta / 1000f);
		}
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (menu != null) {
			if (menu.mousePressed(this, button, x / Game.scale, y / Game.scale))
				return;
			else
				menu = null;
		}

		for (Iterator<GameObject> iterator = objects.iterator(); iterator.hasNext(); ) {
			GameObject object = iterator.next();
			if (object.mousePressed(this, button, x / Game.scale, y / Game.scale))
				return;
		}
	}

	public void replace(GameObject old, GameObject new_) {
		int i = objects.indexOf(old);
		if (i != -1) {
			objects.set(i, new_);
		}
	}

	public void addAbove(GameObject old, GameObject new_) {
		int i = objects.indexOf(old);
		if (i != -1) {
			objects.add(i, new_);
		}
	}

	private boolean openInventory = false;

	@Override
	public void keyPressed(int key, char c) {
		if (key == Keyboard.KEY_ESCAPE) {
			menu = null;
		}

		if (key == Keyboard.KEY_I) {
			menu = null;
			openInventory = true;
		}
	}

	private Message currentMessage = null;
	public Queue<Message> messages = new LinkedList<Message>();

	public static class Message {
		public String string;
		public int duration;
		public int maxDuration;

		public Message(String string, int duration) {
			this.string = string;
			this.duration = duration;
			this.maxDuration = duration;
		}
	}
}
