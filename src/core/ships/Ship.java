package core.ships;

import core.BoardTile;
import core.exception.UnallowedEx;

public abstract class Ship {
	private BoardTile position;
	
	public void setPosition(BoardTile pos) {
		this.position = pos;
	}
	
	public BoardTile getPosition() {
		return position;
	}

	public void moveTo(int x, int y) throws UnallowedEx {
		position.getBoard().moveShip(x,y,this);
	}

}
