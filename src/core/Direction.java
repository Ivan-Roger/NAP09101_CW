package core;

import java.awt.Point;
import java.util.Random;

public enum Direction {
	TOP_LEFT,
	TOP,
	TOP_RIGHT,
	LEFT,
	RIGHT,
	BOTTOM_LEFT,
	BOTTOM,
	BOTTOM_RIGHT;

	public int getOffsetX() {
		switch (this) {
		case TOP_LEFT: return -1;
		case LEFT: return -1;
		case BOTTOM_LEFT: return -1;
		case TOP_RIGHT: return 1;
		case RIGHT: return 1;
		case BOTTOM_RIGHT: return 1;
		default: return 0;
		}
	}

	public int getOffsetY() {
		switch (this) {
		case TOP: return -1;
		case TOP_LEFT: return -1;
		case TOP_RIGHT: return -1;
		case BOTTOM: return 1;
		case BOTTOM_LEFT: return 1;
		case BOTTOM_RIGHT: return 1;
		default: return 0;
		}
	}
	
	public static Direction random() {
		Random alea = new Random();
		switch (alea.nextInt(8)) {
		case 0: return TOP_LEFT;
		case 1: return TOP;
		case 2: return TOP_RIGHT;
		case 3: return LEFT;
		case 4: return RIGHT;
		case 5: return BOTTOM_LEFT;
		case 6: return BOTTOM;
		default: return BOTTOM_RIGHT;
		}
	}
	
	public static Direction fromRelativePoint(Point p) {		
		if (p.y<0) {
			if (p.x<0) return TOP_LEFT;
			else if (p.x>0) return TOP_RIGHT;
			else return TOP;
		} else if (p.y>0) {
			if (p.x<0) return BOTTOM_LEFT;
			else if (p.x>0) return BOTTOM_RIGHT;
			else return BOTTOM;
		} else {
			if (p.x<0) return LEFT;
			else if (p.x>0) return RIGHT;
			else return null; // CENTER
		}
	}
}
