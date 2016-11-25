package core.ships;

import java.awt.Point;

import core.BoardTile;
import core.Direction;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.events.ShipDestroyedEvent;
import core.events.ShipRemovedEvent;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;

public abstract class Ship {
	private GameWrapper game;
	private BoardTile position;
	private boolean alive;
	
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
	
	public boolean isAlive() {
		return alive;
	}

	public void move() throws NotInitializedEx {
		if (position==null) throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");

		boolean isValid = false;
		while (!isValid) {
			try {
				ShipMoveAction action = new ShipMoveAction(this, Direction.random());
				isValid = true; // Exiting the loop
				
				game.addAction(action);
			} catch (OutOfBoundsEx e) {
				// Ignore: Stay in the loop
			} catch (InvalidArgumentEx e) {
				throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");
			}
		}
	}

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
		game.updateInterfaces(new ShipDestroyedEvent(this));
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {} // TODO: Ignore, Log error maybe ?
		
		BoardTile deathPosition = position;
		game.getBoard().removeShip(this);
		game.updateInterfaces(new ShipRemovedEvent(this, deathPosition));
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+(position!=null && alive?"["+position.getX()+","+position.getY()+"]":"")+(!alive?"[DEAD]":"");
	}
	
	protected GameWrapper getGame() {
		return game;
	}
}
