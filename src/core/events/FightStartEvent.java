package core.events;

import core.BoardTile;

public class FightStartEvent extends GameEvent {
	private BoardTile position;
	
	public FightStartEvent(BoardTile position) {
		this.position = position;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.FIGHT_START;
	}

	public BoardTile getPosition() {
		return position;
	}

}
