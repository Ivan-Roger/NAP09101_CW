package core.events;

import core.BoardTile;
import core.ships.Ship;

public class ShipRemovedEvent extends GameEvent {
	private Ship ship;
	private BoardTile position;
	
	public ShipRemovedEvent(Ship ship) {
		this.ship = ship;
		this.position = ship.getPosition();
	}

	@Override
	public GameEventType getType() {
		return GameEventType.SHIP_REMOVED;
	}

	public Ship getShip() {
		return ship;
	}

	public BoardTile getPosition() {
		return position;
	}

}
