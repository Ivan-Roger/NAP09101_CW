package core.actions;

import core.BoardTile;
import core.Direction;
import core.GameWrapper;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;
import core.ships.Ship;

public class ShipMoveAction extends Action {
	private Ship ship;
	private BoardTile from;
	private BoardTile to;

	public ShipMoveAction(Ship ship, Direction direction) throws InvalidArgumentEx, OutOfBoundsEx {
		this.ship = ship;
		
		BoardTile oldPos = ship.getPosition();
		if (oldPos==null) throw new InvalidArgumentEx("Ship doesn't have a position, use ShipSpawnAction");
		this.from = oldPos;
		this.to = oldPos.getNeighbour(direction);
	}
	
	@Override
	public void doAction(GameWrapper game) throws InvalidArgumentEx, NotInitializedEx {
		game.getBoard().moveShip(ship, to);
	}

	@Override
	public void undoAction(GameWrapper game) throws InvalidArgumentEx, NotInitializedEx {
		game.getBoard().moveShip(ship, from);
	}

}
