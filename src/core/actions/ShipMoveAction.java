package core.actions;

import core.BoardTile;
import core.Direction;
import core.GameWrapper;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.ShipMoveEvent;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.Ship;

public class ShipMoveAction extends Action {
	private Ship ship;
	private BoardTile from;
	private BoardTile to;
	private Direction dir;

	public GameEventType getEventType() { return GameEventType.SHIP_MOVE; }
	
	public ShipMoveAction(Ship ship, Direction direction) throws InvalidArgumentEx, OutOfBoundsEx {
		this.ship = ship;
		this.dir = direction;
		
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
	
	@Override
	public String toString() {
		return super.toString()+": "+ship+" going "+dir;
	}

	@Override
	public GameEvent getDoEvent() {
		return new ShipMoveEvent(ship, from, to);
	}

	@Override
	public GameEvent getUndoEvent() {
		return new ShipMoveEvent(ship, to, from);
	}

}
