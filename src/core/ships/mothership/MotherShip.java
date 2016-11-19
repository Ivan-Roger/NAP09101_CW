package core.ships.mothership;

import core.Direction;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;
import core.ships.Ship;

public class MotherShip extends Ship {
	private ShipBehaviour behaviour;

	public MotherShip(GameWrapper game, ShipBehaviour behaviour) {
		super(game);
		setBehaviour(behaviour);
	}

	public void setBehaviour(ShipBehaviour behaviour) {
		this.behaviour = behaviour;
	}
	
	@Override
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

	public void act() {
		behaviour.act(this, position);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		game.end(false);
	}
	
}
