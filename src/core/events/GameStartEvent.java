package core.events;

import core.GameBoard;

public class GameStartEvent extends GameEvent {
	private GameBoard board;
	
	public GameStartEvent(GameBoard board) {
		this.board = board;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.GAME_START;
	}
	
	public GameBoard getBoard() {
		return board;
	}

}
