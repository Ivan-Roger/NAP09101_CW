package core.ships.mothership;

import core.BoardTile;

public class ShipBehaviourDefend implements ShipBehaviour {

	@Override
	public void act(MotherShip me, BoardTile position) {
		if (position.getEnemies().size()==1) {
			position.getEnemies().get(0).destroy();
		} else {
			me.destroy();
		}
	}

}
