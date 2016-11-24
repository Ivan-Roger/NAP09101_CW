package core;

import java.util.ArrayList;

import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.EnemyShip;
import core.ships.Ship;

public class GameBoard {
	private GameWrapper game;
	private int boardWidth;
	private ArrayList<BoardTile> board;
	private ArrayList<EnemyShip> ships;
	
	public GameBoard(GameWrapper game, int width, int height) throws InvalidArgumentEx {
		this.game = game;
		boardWidth = width;
		board = buildBoard(width, height);
		ships = new ArrayList<>();
	}
	
	private ArrayList<BoardTile> buildBoard(int width, int height) throws InvalidArgumentEx {
		if (height<1 || width<1) throw new InvalidArgumentEx("Invalid board size");
		ArrayList<BoardTile> res = new ArrayList<>();
		
		res.add(new BoardTile(this,0,BoardTileType.BLACKHOLE));
		for (int i=1; i<width*height; i++) {
			res.add(new BoardTile(this,i,BoardTileType.NORMAL));
		}
		
		return res;
	}
	
	public BoardTile getTile(int index) throws OutOfBoundsEx {
		try {
			BoardTile tile = board.get(index);
			if (tile==null) throw new OutOfBoundsEx("No such tile");
			return tile;
		} catch (IndexOutOfBoundsException e) {
			throw new OutOfBoundsEx("No such tile");
		}
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
	
	public int getSize() {
		return this.board.size();
	}
	
	public int getWidth() {
		return this.boardWidth;
	}

	public int getHeight() {
		return this.board.size()/this.boardWidth;
	}

	public ArrayList<EnemyShip> getEnemyShips() {
		return this.ships;
	}
	
	public void moveShip(Ship ship, BoardTile pos) throws NotInitializedEx, InvalidArgumentEx {
		if (pos==null) throw new InvalidArgumentEx("Invalid position.");
		
		BoardTile oldPos = ship.getPosition();
		if (oldPos==null) throw new NotInitializedEx("Ship not spawned.");
		oldPos.removeShip(ship);
		
		ship.setPosition(pos);
		pos.addShip(ship);
	}

	public void spawnShip(Ship ship, BoardTile pos) throws InvalidArgumentEx {
		if (pos==null) throw new InvalidArgumentEx("Invalid position.");
		
		if (ship instanceof EnemyShip) ships.add((EnemyShip) ship);
		ship.setPosition(pos);
		pos.addShip(ship);
	}

	public void removeShip(Ship ship) {
		if (ship instanceof EnemyShip) ships.remove((EnemyShip) ship);
		if (ships.size()==0) game.end(true);
		try {
			ship.getPosition().removeShip(ship);
		} catch (InvalidArgumentEx e) {
			// TODO Exception, Impossible: Invalid type of ship
			e.printStackTrace();
		}
		ship.setPosition(null);
	}

}
