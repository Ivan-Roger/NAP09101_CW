package core.ships;

import core.Direction;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;

public class MotherShip extends Ship {

	public MotherShip(GameWrapper game) {
		super(game);
	}

	@Override
	public void move() throws NotInitializedEx {
		if (position==null) throw new NotInitializedEx("Ship doesn't have a position yet. Spawn first.");

		boolean isValid = false;
		while (isValid) {
			try {
				ShipMoveAction action = new ShipMoveAction(this, Direction.random());
				isValid = true; // Exiting the loop
				
				game.addAction(action);
			} catch (OutOfBoundsEx e) {
				// Ignore: Stay in the loop
			} catch (InvalidArgumentEx e) {
				e.printStackTrace();
			}
		}
	}
	
}
