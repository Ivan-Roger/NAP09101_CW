package main;

import core.Direction;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.exception.InvalidActionEx;
import core.exception.InvalidArgumentEx;
import core.exception.OutOfBoundsEx;
import ui.TextUI;

public class Main {

	public static void main(String[] args) {
		System.out.println("INIT");
		
		try {
			GameWrapper game = new GameWrapper();
			@SuppressWarnings("unused")
			TextUI textUi = new TextUI(game);
			
			game.startGame();
			
			for (int i=0; i<20; i++) {
				Direction direction = Direction.random();
				System.out.println("Moving "+direction);
				try {
					game.addAction(new ShipMoveAction(game.getMotherShip(), direction));
					game.tick();
				} catch (OutOfBoundsEx e) {
					System.err.println("Wrong move");
				}
			}
			
			System.out.println("\n---------------------------------------------------------------------------\n");
			
			while (game.canUndo()) {
				game.undo();
			}
			
			System.out.println("MotherShip: "+game.getMotherShip().getPosition().toPoint());
			
		} catch (InvalidArgumentEx e) {
			System.err.println("Unable to create a Game instance !");
			System.exit(-1);
		} catch (InvalidActionEx e) {
			System.err.println(e.getMessage());
		}
		
		
		
	}

}
