package core.ships;

import core.GameWrapper;

public class StarFighterShip extends EnemyShip {

	public StarFighterShip(GameWrapper game) {
		super(game);
	}

	@Override
	public EnemyShipType getType() {
		return EnemyShipType.STAR_FIGHTER;
	}

}
