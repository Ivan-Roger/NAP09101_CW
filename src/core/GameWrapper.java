package core;

import core.exception.InvalidArgumentEx;

public class GameWrapper {
	private GameBoard board;

	public GameWrapper(int width, int height) throws InvalidArgumentEx {
		this.board = new GameBoard(width, height);
	}
}
