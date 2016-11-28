package ui;

import java.util.Scanner;

import core.BoardTile;
import core.GameBoard;
import core.GameWrapper;
import core.events.GameEvent;
import core.exceptions.OutOfBoundsEx;

public class TextUI extends UiWrapper {

	// --- CONSTRUCTORS ---
	public TextUI(GameWrapper game) {
		super(game);
	}

	// --- INHERITED METHODS ---
	@Override
	public void update(GameEvent event) {
		System.out.println(">>> "+event.getType());
		switch (event.getType()) {
		case GAME_START:
			System.out.println("Game started !\n---------------------");
			return;
		case GAME_OVER:
			System.out.println("\n---------------------\nGame ended !");
			return;
		case GAME_QUIT:
			System.exit(0);
		case NEW_TURN:
		case SHIP_SPAWN:
		case SHIP_MOVE:
		case FIGHT_END:
			this.draw();
			return;
		default:
			break;
		}
	}

	// --- PRIVATE METHODS ---
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
					int enemyCount = tile.getEnemies().size();
					boolean motherShip = tile.hasMotherShip();
					
					if (motherShip && enemyCount>0)
						 System.out.print(" # "+(enemyCount>0?enemyCount:" ")+" "+verBorder);
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
	
	
	@SuppressWarnings({ "resource", "unused" })
	private void prompt() {
		System.out.print("END OF TURN - Press enter to continue ... ");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		getGame().play();
	}

}
