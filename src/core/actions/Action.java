package core.actions;

import core.GameWrapper;

public abstract class Action {
	protected GameWrapper context;
	
	public abstract void doAction();
	
	public abstract void undoAction();

}
