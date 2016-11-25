package core.events;

import core.BoardTile;

public class FightEndEvent extends GameEvent {
	private BoardTile position;
	private int enemyCount;
	private boolean win;
	
	public FightEndEvent(BoardTile position, int enemyCount, boolean win) {
		this.position = position;
		this.enemyCount = enemyCount;
		this.win = win;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.FIGHT_END;
	}

	public BoardTile getPosition() {
		return position;
	}

	public int getEnemyCount() {
		return enemyCount;
	}

	public boolean isPlayerWinner() {
		return win;
	}

}
