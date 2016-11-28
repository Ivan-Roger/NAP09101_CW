package core.ships;

import core.GameWrapper;
/* // Clever move towards MotherShip
import core.Direction;
import core.actions.ShipMoveAction;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.mothership.MotherShip;
// */

public abstract class EnemyShip extends Ship {

	public EnemyShip(GameWrapper game) {
		super(game);
	}
	
	public abstract EnemyShipType getType();

	/* // Clever move towards MotherShip
	@Override
	public void move() throws NotInitializedEx {
		if (position==null) throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");
		
		MotherShip enemy = this.game.getMotherShip();		
		Direction dir = Direction.fromRelativePoint(relativePosition(enemy));
		
		if (dir!=null) try {
			game.addAction(new ShipMoveAction(this, dir));
		} catch (OutOfBoundsEx e) {
			e.printStackTrace();
		} catch (InvalidArgumentEx e) {
			throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");
		}
	}
 	// */
}
