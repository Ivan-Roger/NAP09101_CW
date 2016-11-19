package core.ships;

import java.awt.Point;

import core.BoardTile;
import core.GameWrapper;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;

public abstract class Ship {
	protected GameWrapper game;
	protected BoardTile position;
	protected boolean alive;
	
	public Ship(GameWrapper game) {
		this.game = game;
		alive = true;
	}
	
	public void setPosition(BoardTile pos) {
		this.position = pos;
	}
	
	public BoardTile getPosition() {
		return position;
	}

	public abstract void move() throws NotInitializedEx;

	public Point relativePosition(Ship ship) {
		return relativePosition(ship.getPosition());
	}

	public Point relativePosition(BoardTile target) {
		return relativePosition(target.toPoint());
	}

	public Point relativePosition(Point target) {
		int targetX = target.x;
		int targetY = target.y;
		return new Point(targetX-position.getX(), targetY-position.getY());
	}
	
	public void destroy() {
		alive = false;
		try {
			position.removeShip(this);
		} catch (InvalidArgumentEx e) {
			// TODO Exception, Impossible: Invalid type of ship
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+(position!=null?"["+position.getX()+","+position.getY()+"]":"");
	}
}
