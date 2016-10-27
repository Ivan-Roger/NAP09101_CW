package ui;

import core.GameWrapper;

public abstract class UiWrapper {
	
	private GameWrapper game;
	
	public UiWrapper(GameWrapper game) {
		setGame(game);
	}

	private void setGame(GameWrapper game) {
		this.game = game;
	}

	protected GameWrapper getGame() {
		return game;
	}
	
}
