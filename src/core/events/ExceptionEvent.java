package core.events;

public class ExceptionEvent extends GameEvent {
	private Exception ex;
	private boolean critic;
	
	public ExceptionEvent(Exception ex, boolean critic) {
		this.ex = ex;
		this.critic = critic;
	}

	@Override
	public GameEventType getType() {
		return GameEventType.EXCEPTION;
	}

	public Exception getException() {
		return ex;
	}

	public boolean isCritic() {
		return critic;
	}

}
