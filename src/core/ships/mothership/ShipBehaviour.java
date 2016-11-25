package core.ships.mothership;

import core.BoardTile;

public interface ShipBehaviour {
	
	public boolean act(MotherShip me, BoardTile position);
	
	public ShipBehaviourEnum getBehaviour();

}
