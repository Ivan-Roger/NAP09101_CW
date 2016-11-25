package core.ships;

import core.GameWrapper;
import core.exceptions.NotInitializedEx;

public class StarShooterShip extends EnemyShip {

	public StarShooterShip(GameWrapper game) {
		super(game);
	}

	@Override
	public EnemyShipType getType() {
		return EnemyShipType.STAR_SHOOTER;
	}

}
