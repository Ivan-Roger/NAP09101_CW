package ui.gui;

import core.GameBoard;
import core.GameWrapper;
import ui.UiWrapper;

public class GraphicUI extends UiWrapper {
	//private MenuFrameUI menuF;
	private GameFrameUI gameF;

	public GraphicUI(GameWrapper game) {
		super(game);
		gameF = new GameFrameUI(game);
	}

	@Override
	public void startGame(GameBoard board) {
		gameF.start(board);
	}

	@Override
	public void updateBoard(GameBoard board) {
		gameF.updateBoard(board);
	}
}
