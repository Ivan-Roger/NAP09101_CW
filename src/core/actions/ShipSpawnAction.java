package core.actions;

import core.BoardTile;
import core.GameWrapper;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.ShipRemovedEvent;
import core.events.ShipSpawnEvent;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.Ship;

public class ShipSpawnAction extends Action {
	private Ship ship;
	private BoardTile pos;

	public GameEventType getEventType() { return GameEventType.SHIP_SPAWN; }

	public ShipSpawnAction(Ship ship, BoardTile pos) throws InvalidArgumentEx {
		this.ship = ship;
		if (ship.getPosition()!=null) throw new InvalidArgumentEx("Ship has a position, use ShipMoveAction");
		if (pos==null) throw new InvalidArgumentEx("Invalid position.");
		
		this.pos = pos;
	}
	
	@Override
	public void doAction(GameWrapper game) throws OutOfBoundsEx, InvalidArgumentEx {
		game.getBoard().spawnShip(ship, pos);
	}

	@Override
	public void undoAction(GameWrapper game) {
		game.getBoard().removeShip(ship);
	}
	
	@Override
	public String toString() {
		return super.toString()+": "+ship+" at ["+pos.getX()+","+pos.getY()+"]";
	}

	@Override
	public GameEvent getDoEvent() {
		return new ShipSpawnEvent(ship);
	}

	@Override
	public GameEvent getUndoEvent() {
		return new ShipRemovedEvent(ship);
	}

}
