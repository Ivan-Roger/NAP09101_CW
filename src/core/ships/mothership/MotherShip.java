package core.ships.mothership;

import core.GameWrapper;
import core.events.FightEvent;
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

	public void act() {
		if (getPosition().getEnemies().size()==0) return;
		getGame().updateInterfaces(new FightEvent(getPosition()));
		
		behaviour.act(this, getPosition());
	}
	
	@Override
	public void destroy() {
		super.destroy();
		getGame().end(false);
	}
	
}
