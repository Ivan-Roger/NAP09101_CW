package main;

import ui.text.TextWrapper;
import core.GameWrapper;
import core.actions.ShipMoveAction;
import core.exception.InvalidArgumentEx;

public class Main {

	public static void main(String[] args) {
		System.out.println("INIT");
		
		try {
			GameWrapper game = new GameWrapper();
			TextWrapper textUi = new TextWrapper(game);
			game.registerUi(textUi);			
			game.startGame();
			
			textUi.draw();
			System.out.println("MotherShip: "+game.getMotherShip().getPosition().toPoint());
			
			game.addAction(new ShipMoveAction(1,3,game.getMotherShip()));
			game.tick();
			
			textUi.draw();
			System.out.println("MotherShip: "+game.getMotherShip().getPosition().toPoint());
			
			game.undo();
			
			textUi.draw();
			System.out.println("MotherShip: "+game.getMotherShip().getPosition().toPoint());
			
		} catch (InvalidArgumentEx e) {
			System.err.println("Unable to create a Game instance !");
			System.exit(-1);
		}
		
		
		
	}

}
