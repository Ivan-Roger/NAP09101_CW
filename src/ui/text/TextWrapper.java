package ui.text;

import core.BoardTile;
import core.GameBoard;
import core.GameWrapper;
import ui.UiWrapper;

public class TextWrapper extends UiWrapper {

	public TextWrapper(GameWrapper game) {
		super(game);
	}

	public void draw() {
		final String horBorder = "---";
		final String verBorder = "|";
		final String cornerBor = "+";
				
		GameBoard board = this.getGame().getBoard();
		int bWidth = board.getWidth(), bHeight = board.getHeight();
		
		String lineSep = cornerBor;
		for (int x=0; x<bWidth; x++) {
			lineSep+=horBorder+cornerBor;
		}

		System.out.println(lineSep);
		for (int y=0; y<bHeight; y++) {
			System.out.print(verBorder);
			for (int x=0; x<bWidth; x++) {
				BoardTile tile = board.getTile(x, y);
				System.out.print(" "+tile.getShips().size()+" "+verBorder);
			}
			System.out.println("\n"+lineSep);
		}
	}

}
