package core.actions;

import core.GameWrapper;
import core.events.GameEvent;
import core.exceptions.GameEx;

public abstract class Action {
	protected GameWrapper context;

	public abstract GameEvent getDoEvent();
	public abstract GameEvent getUndoEvent();
	
	public abstract void doAction(GameWrapper game) throws GameEx;
	
	public abstract void undoAction(GameWrapper game) throws GameEx;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
}
