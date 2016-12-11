package ld37;

import ld37.state.PlayState;

public class CarpetObject extends ImageObject {

	private CarpetState state = CarpetState.FIRST;

	public CarpetObject(float x, float y) {
		super(x, y, CarpetState.FIRST.texture);
	}

	private enum CarpetState {
		THIRD("carpet2", null, "Lift other corner", new Box(88, 72, 96, 80), new Box(0, 0, 8, 8)) {
			@Override
			public boolean canBeSwitchedTo(PlayState play) {
				return NoteItem.BOOK.isInspected() &&
						BookItem.RED_BOOK.isInspected() &&
						BookItem.YELLOW_BOOK.isInspected() &&
						BookItem.GREEN_BOOK.isInspected() &&
						BookItem.YELLOW_BOOK.isInspected() &&
						BookItem.BLACK_BOOK.isInspected();
			}
		},
		SECOND("carpet1", THIRD, "Check under", new Box(88, 72, 96, 80)) {
			@Override
			public boolean canBeSwitchedTo(PlayState play) {
				return true;
			}
		},
		FIRST("CARPET", SECOND, "") {
			@Override
			public boolean canBeSwitchedTo(PlayState play) {
				return false;
			}
		};

		public final String texture;
		public final CarpetState next;
		public final String message;
		private final Box[] exclude;

		CarpetState(String texture, CarpetState next, String message, Box... exclude) {
			this.texture = texture;
			this.next = next;
			this.message = message;
			this.exclude = exclude;
		}

		public boolean shouldExclude(float x, float y) {
			for (Box box : exclude)
				if (box.contains(x, y))
					return true;
			return false;
		}

		public abstract boolean canBeSwitchedTo(PlayState play);
	}

	@Override
	public boolean mousePressed(PlayState play, int button, float x, float y) {
		if (box.contains(x, y) && !state.shouldExclude(x - box.x, y - box.y)) {
			onClicked(play, x - box.x, y - box.y, button);
			return true;
		}
		return false;
	}

	@Override
	public void onClicked(PlayState play, float x, float y, int button) {
		if (state.next != null) {
			if (state.next.canBeSwitchedTo(play)) {
				play.menu = new Menu(x + box.x, y + box.y, 0.03f, new Menu.MenuAction(state.next.message) {

					@Override
					public void clicked(PlayState play) {
						state = state.next;
						setName(state.texture);
					}
				});
			}
		}
	}
}
