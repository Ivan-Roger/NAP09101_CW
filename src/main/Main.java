package main;

import core.GameWrapper;
import core.exceptions.InvalidArgumentEx;
import ui.gui.GraphicUI;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		System.out.println(" INIT  | Launching Sky Wars ...");
		
		try {
			GameWrapper game = new GameWrapper();
			//TextUI textUi = new TextUI(game);
			GraphicUI gui = new GraphicUI(game);
			
			game.startGame();
		} catch (InvalidArgumentEx e) {
			System.err.println("Unable to create a Game instance !");
			System.exit(-1);
		}
	}

}
