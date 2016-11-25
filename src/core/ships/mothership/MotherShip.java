package core.ships.mothership;

import core.BoardTile;
import core.GameWrapper;
import core.events.FightEndEvent;
import core.events.FightStartEvent;
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
	
	public ShipBehaviourEnum getBehaviour() {
		return this.behaviour.getBehaviour();
	}

	public void act() {
		int enemyCount = getPosition().getEnemies().size();
		if (enemyCount==0) return;
		getGame().updateInterfaces(new FightStartEvent(getPosition()));
		
		BoardTile pos = getPosition();
		boolean win = behaviour.act(this, getPosition());
		getGame().updateInterfaces(new FightEndEvent(pos, enemyCount, win));
	}
	
	@Override
	public void destroy() {
		super.destroy();
		getGame().end(false);
	}
	
}
