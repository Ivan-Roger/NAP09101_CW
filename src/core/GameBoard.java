package core;

import java.util.ArrayList;

import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;
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
		
		res.add(new BoardTile(this,0,0,BoardTileType.BLACKHOLE));
		for (int i=1; i<width*height; i++) {
			int y = i/width;
			res.add(new BoardTile(this,i-y*width,y,BoardTileType.NORMAL));
		}
		
		return res;
	}
	
	public BoardTile getTile(int x, int y) throws OutOfBoundsEx {
		if (x<0 || x>=boardWidth) throw new OutOfBoundsEx("No such tile");
		try {
			BoardTile tile = board.get(y*boardWidth+x);
			if (tile==null) throw new OutOfBoundsEx("No such tile");
			return tile;
		} catch (IndexOutOfBoundsException e) {
			throw new OutOfBoundsEx("No such tile");
		}
	}

	public int getWidth() {
		return this.boardWidth;
	}

	public int getHeight() {
		return this.board.size()/this.boardWidth;
	}
	
	public void moveShip(Ship ship, BoardTile pos) throws NotInitializedEx, InvalidArgumentEx {
		if (pos==null) throw new InvalidArgumentEx("Invalid position.");
		
		BoardTile oldPos = ship.getPosition();
		if (oldPos==null) throw new NotInitializedEx("Ship not spawned.");
		oldPos.removeShip(ship);
		
		// TODO: Update the ship here too.
		ship.setPosition(pos);
		pos.addShip(ship);
	}

	public void spawnShip(Ship ship, BoardTile pos) throws InvalidArgumentEx {
		if (pos==null) throw new InvalidArgumentEx("Invalid position.");
		// TODO: Add into local list
		ship.setPosition(pos);
		pos.addShip(ship);
	}

	public void removeShip(Ship ship) {
		// TODO: Remove from local list
		ship.getPosition().removeShip(ship);
	}

}
