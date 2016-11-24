package core.ships;

import core.Direction;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.mothership.MotherShip;

public abstract class EnemyShip extends Ship {

	public EnemyShip(GameWrapper game) {
		super(game);
	}

	/* // Clever move towards MotherShip
	@Override
	public void move() throws NotInitializedEx {
		if (position==null) throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");
		
		MotherShip enemy = this.game.getMotherShip();		
		Direction dir = Direction.fromRelativePoint(relativePosition(enemy));
		
		if (dir!=null) try {
			game.addAction(new ShipMoveAction(this, dir));
		} catch (OutOfBoundsEx e) {
			// TODO Unexpected: Error when moving
			e.printStackTrace();
		} catch (InvalidArgumentEx e) {
			throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");
		}
	}
 	// */
}
