package core.events;

public class TurnOverEvent extends GameEvent {
	private int number;
	
	public TurnOverEvent(int number) {
		this.number = number;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.TURN_OVER;
	}
	
	public int getTurnNumber() {
		return number;
	}

}
