package ui;

import core.BoardTile;
import core.GameBoard;
import core.GameWrapper;
import core.exception.OutOfBoundsEx;

public class TextUI extends UiWrapper {

	public TextUI(GameWrapper game) {
		super(game);
	}

	private void draw() {
		final String horBorder = "-----";
		final String verBorder = "|";
		final String cornerBor = "+";
				
		GameBoard board = this.getGame().getBoard();
		int bWidth = board.getWidth(), bHeight = board.getHeight();
		
		String lineSep = cornerBor;
		for (int x=0; x<bWidth; x++) {
			lineSep+=horBorder+cornerBor;
		}

		System.out.println(lineSep);
		try {
			for (int y=0; y<bHeight; y++) {
				System.out.print(verBorder);
				for (int x=0; x<bWidth; x++) {
					BoardTile tile = board.getTile(x, y);
					int enemyCount = tile.getShips().size();
					boolean motherShip = tile.getShips().contains(getGame().getMotherShip());
					if (motherShip) enemyCount-=1;
					
					if (motherShip && enemyCount>0)
						 System.out.print(" "+(motherShip?"#":" ")+" "+(enemyCount>0?enemyCount:" ")+" "+verBorder);
					else if (!(motherShip || enemyCount>0))
						 System.out.print("     "+verBorder);
					else System.out.print("  "+(motherShip?"#":"")+(enemyCount>0?enemyCount:"")+"  "+verBorder);
				}
				System.out.println("\n"+lineSep);
			}
		} catch (OutOfBoundsEx e) {
			System.err.println("Unexpected OutOfBounds Exception.");
		}
	}

	@Override
	public void updateBoard(GameBoard board) {
		this.draw();
	}

}
