package core;

public class BoardTile {
	private int xPos, yPos;
	private GameBoard myBoard;
	
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

}
