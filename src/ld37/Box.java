package ld37;

public class Box {
	public float x, y, x2, y2;

	public Box() {

	}

	public Box(float x, float y, float x2, float y2) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	public boolean contains(float x, float y) {
		return x >= this.x && x <= this.x2 && y >= this.y && y <= this.y2;
	}
}
