package core;

import java.util.ArrayList;

import core.exception.InvalidArgumentEx;

public class GameBoard {
	private int boardWidth;
	private ArrayList<BoardTile> board;
	
	public GameBoard(int width, int height) throws InvalidArgumentEx {
		boardWidth = width;
		board = buildBoard(width, height);
	}
	
	private ArrayList<BoardTile> buildBoard(int width, int height) throws InvalidArgumentEx {
		if (height<1 || width<1) throw new InvalidArgumentEx("Invalid board size");
		ArrayList<BoardTile> res = new ArrayList<>();
		
		res.add(new BoardBlackholeTile(this,0,0));
		for (int i=1; i<width*height; i++) {
			int x = i/width;
			res.add(new BoardTile(this,x,i-x*width));
		}
		
		return res;
	}
	
	public BoardTile getTile(int x, int y) {
		return board.get(y*boardWidth+x);
	}

}
