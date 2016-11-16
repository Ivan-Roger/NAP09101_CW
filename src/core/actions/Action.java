package core.actions;

import core.GameWrapper;
import core.exception.GameEx;

public abstract class Action {
	protected GameWrapper context;
	
	public abstract void doAction(GameWrapper game) throws GameEx;
	
	public abstract void undoAction(GameWrapper game) throws GameEx;

}
