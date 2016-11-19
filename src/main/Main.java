package main;

import core.GameWrapper;
import core.exception.InvalidActionEx;
import core.exception.InvalidArgumentEx;
import ui.TextUI;
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
			
			for (;;) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Remove: For the DEBUG sleep
					e.printStackTrace();
				}
				game.tick();
			}
			
			/*
			while (game.canUndo()) {
				game.undo();
			}
			*/
		} catch (InvalidArgumentEx e) {
			System.err.println("Unable to create a Game instance !");
			System.exit(-1);
		} catch (InvalidActionEx e) {
			System.err.println(e.getMessage());
		}
	}

}
