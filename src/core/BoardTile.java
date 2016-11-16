package core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import core.exception.OutOfBoundsEx;
import core.ships.Ship;

public class BoardTile {	
	private BoardTileType type;
	private int xPos, yPos;
	private GameBoard myBoard;
	private ArrayList<Ship> ships = new ArrayList<>();
	
	// Constructors
	public BoardTile(GameBoard myBoard, int xPos, int yPos, BoardTileType type) {
		setBoard(myBoard);
		setX(xPos);
		setY(yPos);
		this.type = type;
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

	public List<Ship> getShips() {
		return ships;
	}

	public void addShip(Ship ship) {
		this.ships.add(ship);
	}

	public boolean removeShip(Ship ship) {
		return this.ships.remove(ship);
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
