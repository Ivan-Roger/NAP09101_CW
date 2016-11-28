package main;

import core.GameWrapper;
import core.exceptions.InvalidArgumentEx;
import ui.TextUI;
import ui.gui.MenuFrameUI;

public class Main {

	public static void main(String[] args) {
		System.out.println(" INIT  | Launching Sky Wars ...");
		if (args.length>0 && args[0].equals("-text"))
			 text();
		else graphic();
	}

	@SuppressWarnings("unused")
	private static void text() {
		try {
			GameWrapper game = new GameWrapper();
			TextUI ui = new TextUI(game);
			game.startGame();
		} catch (InvalidArgumentEx ex) {
			ex.printStackTrace();
		}
	}

	public static void graphic() {
		MenuFrameUI menu = new MenuFrameUI();
		menu.setVisible(true);
	}
}
