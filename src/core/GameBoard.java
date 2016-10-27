package core;

import java.util.ArrayList;

import core.exception.InvalidArgumentEx;
import core.exception.UnallowedEx;
import core.ships.Ship;

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
			int y = i/width;
			res.add(new BoardTile(this,i-y*width,y));
		}
		
		return res;
	}
	
	public BoardTile getTile(int x, int y) {
		return board.get(y*boardWidth+x);
	}

	public int getWidth() {
		return this.boardWidth;
	}

	public int getHeight() {
		return this.board.size()/this.boardWidth;
	}
	
	public void moveShip(int x, int y, Ship ship) throws UnallowedEx {
		BoardTile oldPos = ship.getPosition();
		if (oldPos!=null)
			oldPos.removeShip(ship);
		
		// TODO: Save the ship here too.
		BoardTile pos = getTile(x, y);
		ship.setPosition(pos);
		pos.addShip(ship);
	}

	public void removeShip(Ship ship) {
		// TODO: Remove from local list
		ship.getPosition().removeShip(ship);
	}

}
