package ld37;

import ld37.state.PlayState;

public class BookshelfObject extends ImageObject {
	public enum Book {
		RED("red", new Box(0, 0, 3, 1), new Box(0, 2, 2, 3)),
		GREEN("green", new Box(0, 1, 3, 2)),
		BLACK("black", new Box(0, 3, 4, 5), new Box(0, 7, 3, 8)),
		BLUE("blue", new Box(0, 5, 3, 6)),
		YELLOW("yellow", new Box(0, 6, 2, 7));

		public final String name;
		private final Box[] boxes;
		public boolean taken = false;

		Book(String name, Box... boxes) {
			this.name = name;
			this.boxes = boxes;
		}

		public boolean isInBook(float x, float y) {
			for (Box box : boxes) {
				if (box.contains(x, y))
					return true;
			}
			return false;
		}

		public static Book getBook(float x, float y) {
			for (Book book : values()) {
				if (book.isInBook(x, y))
					return book;
			}
			return null;
		}
	}

	public BookshelfObject(float x, float y, float scale) {
		super(x, y, "bookshelf");
		setScale(scale);
	}

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
		final Book book = Book.getBook(x / getxScale(), y / getyScale());
		if (book != null && !book.taken) {
			final BookItem item;
			switch (book) {
				case RED:
					item = BookItem.RED_BOOK;
					break;
				case GREEN:
					item = BookItem.GREEN_BOOK;
					break;
				case BLUE:
					item = BookItem.BLUE_BOOK;
					break;
				case BLACK:
					item = BookItem.BLACK_BOOK;
					break;
				case YELLOW:
					item = BookItem.YELLOW_BOOK;
					break;
				default:
					item = BookItem.BLUE_BOOK;
					break;
			}
			play.menu = new Menu(box.x + x, box.y + y, 0.03f, new Menu.MenuAction("Take " + book.name + " book") {
				@Override
				public void clicked(PlayState play) {
					book.taken = true;
					Game.getGame().inventory.items.add(item);
					play.messages.add(new PlayState.Message("Got " + book.name + " book", 1000));
				}
			});
		}
	}
}
