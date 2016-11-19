package ui;

import core.GameBoard;
import core.GameWrapper;

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
	
	public abstract void updateBoard(GameBoard board);

	public abstract void startGame(GameBoard board);
	
}
