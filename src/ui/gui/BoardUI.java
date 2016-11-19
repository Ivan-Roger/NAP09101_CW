package ui.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.GameBoard;
import core.exception.OutOfBoundsEx;

@SuppressWarnings("serial")
public class BoardUI extends JPanel {
	private ArrayList<BoardTileUI> tiles;

	public BoardUI(GameBoard board) {
		tiles = new ArrayList<>();
		initContent(board);
	}

	private void initContent(GameBoard board) {
		this.setLayout(new GridLayout(board.getWidth(), board.getHeight()));

		try {
			for (int i=0; i<board.getSize(); i++) {
				BoardTileUI tile = new BoardTileUI(board.getTile(i));
				tiles.add(tile);
				this.add(tile);
			}
		} catch (OutOfBoundsEx e) {
			// TODO Exception, Board malformed. throw InitError
			e.printStackTrace();
		}
	}

	public void updateBoard(GameBoard board) {
		try {
			for (int i=0; i<board.getSize(); i++) {
					tiles.get(i).update(board.getTile(i));
			}
		} catch (OutOfBoundsEx e) {
			// TODO Unexpected exception, display error message and quit
			e.printStackTrace();
		}
	}
}
