package core.events;

public abstract class GameEvent {
	
	public abstract GameEventType getType();
	
	public String toString() {
		return getType().toString();
	}
	
}
