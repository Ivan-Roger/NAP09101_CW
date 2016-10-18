package core;

import java.util.ArrayList;

import core.exception.InvalidArgumentEx;

public class GameBoard {
	private ArrayList<ArrayList<BoardTile>> board;
	
	public GameBoard(int width, int height) throws InvalidArgumentEx {
		board = buildBoard(width, height);
	}
	
	private ArrayList<ArrayList<BoardTile>> buildBoard(int width, int height) throws InvalidArgumentEx {
		if (height<1 || width<1) throw new InvalidArgumentEx("Invalid board size");
		ArrayList<ArrayList<BoardTile>> res = new ArrayList<>();
		
		// First row
		ArrayList<BoardTile> firstRow = new ArrayList<>();
		firstRow.add(new BoardBlackholeTile(this));
		for (int j=1; j<width; j++) {
			firstRow.add(new BoardTile(this));
		}
		res.add(firstRow);
		
		// Other rows
		for (int i=1; i<height; i++) {
			ArrayList<BoardTile> newRow = new ArrayList<>();
			for (int j=0; j<width; j++) {
				newRow.add(new BoardTile(this));
			}
			res.add(newRow);
		}
		
		return res;
	}

}
