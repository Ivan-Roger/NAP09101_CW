package core.events;

public class GameQuitEvent extends GameEvent {
	
	@Override
	public GameEventType getType() {
		return GameEventType.GAME_QUIT;
	}

}
