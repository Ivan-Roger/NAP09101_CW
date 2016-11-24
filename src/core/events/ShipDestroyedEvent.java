package core.events;

import core.BoardTile;
import core.ships.Ship;

public class ShipDestroyedEvent extends GameEvent {
	private Ship ship;
	private BoardTile position;
	
	public ShipDestroyedEvent(Ship ship) {
		this.ship = ship;
		this.position = ship.getPosition();
	}

	@Override
	public GameEventType getType() {
		return GameEventType.SHIP_DESTROYED;
	}

	public Ship getShip() {
		return ship;
	}

	public BoardTile getPosition() {
		return position;
	}

}
