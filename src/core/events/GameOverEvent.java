package core.events;

public class GameOverEvent extends GameEvent {
	private boolean win;
	
	public GameOverEvent(boolean win) {
		this.win = win;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.GAME_OVER;
	}
	
	public boolean isPlayerWinner() {
		return win;
	}

}
