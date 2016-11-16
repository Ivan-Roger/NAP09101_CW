package core.actions;

import core.BoardTile;
import core.GameWrapper;
import core.exception.InvalidArgumentEx;
import core.exception.OutOfBoundsEx;
import core.ships.Ship;

public class ShipSpawnAction extends Action {
	private Ship ship;
	private BoardTile pos;

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

}
