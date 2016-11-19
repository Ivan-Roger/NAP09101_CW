package core.ships.mothership;

import core.GameWrapper;

public class MotherShipFactory {

	public static MotherShip create(GameWrapper game, ShipBehaviourEnum mode) {
		switch(mode) {
		case DEFEND_MODE: return new MotherShip(game, new ShipBehaviourDefend());
		default: return new MotherShip(game, new ShipBehaviourAttack());
		}
	}
}
