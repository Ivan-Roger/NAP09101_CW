package core;

import java.util.ArrayList;
import java.util.List;

import core.exception.UnallowedEx;

public class BoardTile {
	private int xPos, yPos;
	private GameBoard myBoard;
	private ArrayList<Ship> ships;
	
	// Constructors
	public BoardTile(GameBoard myBoard, int xPos, int yPos) {
		setMyBoard(myBoard);
		setX(xPos);
		setY(yPos);
	}

	// Getters and Setters
	protected GameBoard getMyBoard() {
		return myBoard;
	}

	private void setMyBoard(GameBoard myBoard) {
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

	public void addShip(Ship ship) throws UnallowedEx {
		this.ships.add(ship);
	}

}
