package core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import core.exception.InvalidArgumentEx;
import core.exception.OutOfBoundsEx;
import core.ships.EnemyShip;
import core.ships.Ship;
import core.ships.mothership.MotherShip;

public class BoardTile {	
	private BoardTileType type;
	private int xPos, yPos;
	private GameBoard myBoard;
	private ArrayList<EnemyShip> enemies;
	private boolean motherShip;
	
	// Constructors
	public BoardTile(GameBoard myBoard, int xPos, int yPos, BoardTileType type) {
		setBoard(myBoard);
		setX(xPos);
		setY(yPos);
		this.type = type;
		enemies = new ArrayList<>();
	}

	// Getters and Setters
	public GameBoard getBoard() {
		return myBoard;
	}

	private void setBoard(GameBoard myBoard) {
		this.myBoard = myBoard;
	}

	public int getX() {
		return xPos;
	}

	private void setX(int xPos) {
		this.xPos = xPos;
	}

	public int getY() {
		return yPos;
	}

	private void setY(int yPos) {
		this.yPos = yPos;
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
		return new Point(this.xPos, this.yPos);
	}
	
	public BoardTile getNeighbour(Direction direction) throws OutOfBoundsEx {
		return myBoard.getTile(xPos+direction.getOffsetX(), yPos+direction.getOffsetY());
	}

}
