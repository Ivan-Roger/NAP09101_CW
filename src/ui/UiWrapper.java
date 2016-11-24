package ui;

import core.GameWrapper;
import core.events.GameEvent;

public abstract class UiWrapper {
	
	private GameWrapper game;
	
	public UiWrapper(GameWrapper game) {
		setGame(game);
		game.registerUi(this);
	}

	private void setGame(GameWrapper game) {
		this.game = game;
	}

	protected GameWrapper getGame() {
		return game;
	}

	public abstract void update(GameEvent event);
	
}
