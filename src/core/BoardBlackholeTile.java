package core;

import core.exception.UnallowedEx;
import core.ships.EnemyShip;
import core.ships.Ship;

public class BoardBlackholeTile extends BoardTile {

	public BoardBlackholeTile(GameBoard myBoard, int xPos, int yPos) {
		super(myBoard, xPos, yPos);
	}
	
	@Override
	public void addShip(Ship ship) throws UnallowedEx {
		if (!EnemyShip.class.isAssignableFrom(ship.getClass())) throw new UnallowedEx("MotherShip can't go on this Tile ("+super.getX()+","+super.getY()+")");
		super.addShip(ship);
	}

}
