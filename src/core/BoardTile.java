package core;

import java.awt.Point;
import java.util.ArrayList;

import core.exceptions.InvalidArgumentEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.EnemyShip;
import core.ships.Ship;
import core.ships.mothership.MotherShip;

public class BoardTile {	
	private BoardTileType type;
	private int index;
	private GameBoard myBoard;
	private ArrayList<EnemyShip> enemies;
	private boolean motherShip;
	
	// Constructors
	public BoardTile(GameBoard myBoard, int index, BoardTileType type) {
		setBoard(myBoard);
		setIndex(index);
		this.type = type;
		enemies = new ArrayList<>();
	}

	// Getters and Setters
	private void setBoard(GameBoard myBoard) {
		this.myBoard = myBoard;
	}
	
	public GameBoard getBoard() {
		return myBoard;
	}

	private void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public int getX() {
		return index%myBoard.getWidth();
	}

	public int getY() {
		return index/myBoard.getWidth();
	}

	public ArrayList<EnemyShip> getEnemies() {
		return enemies;
	}

	public void addShip(Ship ship) throws InvalidArgumentEx {
		if (ship instanceof EnemyShip)
			this.enemies.add((EnemyShip) ship);
		else if (ship instanceof MotherShip)
			this.motherShip = true;
		else throw new InvalidArgumentEx("Unknown type of ship");
	}

	public boolean removeShip(Ship ship) throws InvalidArgumentEx {
		if (ship instanceof EnemyShip)
			return this.enemies.remove((EnemyShip) ship);
		else if (ship instanceof MotherShip) {
			if (!this.motherShip) return false;
			this.motherShip = false;
			return true;
		} else throw new InvalidArgumentEx("Unknown type of ship");
	}

	public boolean hasMotherShip() {
		return this.motherShip;
	}
	
	public BoardTileType getType() {
		return type;
	}
	
	public Point toPoint() {
		return new Point(getX(), getY());
	}
	
	public BoardTile getNeighbour(Direction direction) throws OutOfBoundsEx {
		return myBoard.getTile(getX()+direction.getOffsetX(), getY()+direction.getOffsetY());
	}

}
