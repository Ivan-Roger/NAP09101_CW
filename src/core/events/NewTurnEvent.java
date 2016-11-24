package core.events;

public class NewTurnEvent extends GameEvent {
	private int number;
	
	public NewTurnEvent(int number) {
		this.number = number;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.NEW_TURN;
	}
	
	public int getTurnNumber() {
		return number;
	}

}
