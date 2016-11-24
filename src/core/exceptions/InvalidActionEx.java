package core.exceptions;

@SuppressWarnings("serial")
public class InvalidActionEx extends GameEx {
	private GameEx exception;
	
	public InvalidActionEx(GameEx ex) {
		super("Invalid action: "+ex.getMessage());
		exception = ex;
	}

	public GameEx getException() {
		return exception;
	}
}
