package core.ships.mothership;

import java.util.ArrayList;

import core.BoardTile;
import core.ships.EnemyShip;

public class ShipBehaviourAttack implements ShipBehaviour {

	@SuppressWarnings("unchecked")
	@Override
	public boolean act(MotherShip me, BoardTile position) {
		ArrayList<EnemyShip> enemies = (ArrayList<EnemyShip>) position.getEnemies().clone();		
		if (enemies.size()>=3) {
			System.out.println("ACTION | Attack: Died against "+enemies.size()+" enemies.");
			me.destroy();
			return false;
		} else {
			System.out.println("ACTION | Attack: Killed "+enemies.size()+" enemies.");
			for (EnemyShip s : enemies) {
				s.destroy();
			}
			return true;
		}
	}

	@Override
	public ShipBehaviourEnum getBehaviour() {
		return ShipBehaviourEnum.ATTACK_MODE;
	}

}
