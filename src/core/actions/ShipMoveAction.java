package core.actions;

import java.awt.Point;

import core.BoardTile;
import core.exception.InvalidArgumentEx;
import core.exception.UnallowedEx;
import core.ships.Ship;

public class ShipMoveAction extends Action {
	private Ship ship;
	private Point from;
	private Point to;

	public ShipMoveAction(int x, int y, Ship ship) throws InvalidArgumentEx {
		this.ship = ship;
		
		BoardTile oldPos = ship.getPosition();
		if (oldPos==null) throw new InvalidArgumentEx("Ship doesn't have a position, use ShipSpawnAction");
		this.from = new Point(oldPos.getX(), oldPos.getY());
		
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
		try {
			ship.moveTo((int) from.getX(), (int) from.getY());
		} catch (UnallowedEx e) {
			System.err.println("Can't perform action: "+e.getMessage()); // TODO: throw Exception
		}
	}

}
