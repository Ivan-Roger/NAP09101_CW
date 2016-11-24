package core.events;

import core.BoardTile;
import core.ships.Ship;

public class ShipMoveEvent extends GameEvent {
	private Ship ship;
	private BoardTile origin;
	private BoardTile destination;

	public ShipMoveEvent(Ship ship, BoardTile origin, BoardTile destination) {
		this.ship = ship;
		this.origin = origin;
		this.destination = destination;
	}
	
	@Override
	public GameEventType getType() {
		return GameEventType.SHIP_MOVE;
	}

	public Ship getShip() {
		return ship;
	}

	public BoardTile getOrigin() {
		return origin;
	}

	public BoardTile getDestination() {
		return destination;
	}

}
