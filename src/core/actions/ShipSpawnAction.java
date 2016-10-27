package core.actions;

import java.awt.Point;

import core.exception.InvalidArgumentEx;
import core.exception.UnallowedEx;
import core.ships.Ship;

public class ShipSpawnAction extends Action {
	private Ship ship;
	private Point to;

	public ShipSpawnAction(int x, int y, Ship ship) throws InvalidArgumentEx {
		this.ship = ship;
		if (ship.getPosition()!=null) throw new InvalidArgumentEx("Ship has a position, use ShipMoveAction");
		
		this.to = new Point(x,y);
	}
	
	@Override
	public void doAction() {
		try {
			ship.moveTo((int) to.getX(), (int) to.getY());
		} catch (UnallowedEx e) {
			System.err.println("Can't perform action: "+e.getMessage()); // TODO: throw Exception
		}
	}

	@Override
	public void undoAction() {
		ship.getPosition().getBoard().removeShip(ship);
	}

}
