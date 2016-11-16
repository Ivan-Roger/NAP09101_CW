package core.ships;

import core.BoardTile;
import core.Direction;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;

public abstract class Ship {
	protected GameWrapper game;
	protected BoardTile position;
	
	public Ship(GameWrapper game) {
		this.game = game;
	}
	
	public void setPosition(BoardTile pos) {
		this.position = pos;
	}
	
	public BoardTile getPosition() {
		return position;
	}

	public abstract void move() throws NotInitializedEx;

}
