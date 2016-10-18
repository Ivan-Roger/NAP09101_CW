package core;

public class BoardTile {
	private GameBoard myBoard;
	
	// Constructors
	public BoardTile(GameBoard myBoard) {
		setMyBoard(myBoard);
	}

	// Getters and Setters
	protected GameBoard getMyBoard() {
		return myBoard;
	}

	private void setMyBoard(GameBoard myBoard) {
		this.myBoard = myBoard;
	}

}
