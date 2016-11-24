package core.events;

import core.BoardTile;
import core.ships.Ship;

public class FightEvent extends GameEvent {
	private BoardTile position;
	
	public FightEvent(BoardTile position) {
		this.position = position;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.FIGHT;
	}

	public BoardTile getPosition() {
		return position;
	}

}
