package core.ships;

import core.GameWrapper;

public class StarCruiserShip extends EnemyShip {

	public StarCruiserShip(GameWrapper game) {
		super(game);
	}

	@Override
	public EnemyShipType getType() {
		return EnemyShipType.STAR_CRUISER;
	}

}
