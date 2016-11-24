package core.ships.mothership;

import core.BoardTile;

public class ShipBehaviourDefend implements ShipBehaviour {

	@Override
	public void act(MotherShip me, BoardTile position) {
		if (position.getEnemies().size()==0) return;
		
		if (position.getEnemies().size()==1) {
			System.out.println("ACTION | Defend: Killed 1 enemy.");
			position.getEnemies().get(0).destroy();
		} else {
			System.out.println("ACTION | Defend: Died");
			me.destroy();
		}
	}

}
