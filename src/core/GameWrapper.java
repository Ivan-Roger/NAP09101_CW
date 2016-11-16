package core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import ui.UiWrapper;
import core.actions.Action;
import core.exception.InvalidArgumentEx;
import core.exception.UnallowedEx;
import core.ships.MotherShip;

public class GameWrapper {
	
	// --- CONSTANTS ---
	public static final int DEFAULT_BOARD_WIDTH = 4;
	public static final int DEFAULT_BOARD_HEIGHT = 4;
	
	// --- VARIABLES ---
	private ArrayList<UiWrapper> interfaces = new ArrayList<>();
	
	private GameBoard board;
	private MotherShip motherShip;
	private ArrayDeque<Action> actTodo = new ArrayDeque<>();
	private ArrayDeque<Action> actDone = new ArrayDeque<>();
	
	// --- CONSTRUCTORS ---
	public GameWrapper() throws InvalidArgumentEx {
		setBoard(new GameBoard(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT));
		
		// TODO: Use FactoryPattern ?
		motherShip = new MotherShip();
	}

	// --- ACCESSORS ---
	public GameBoard getBoard() {
		return board;
	}

	private void setBoard(GameBoard board) {
		this.board = board;
	}
	
	public void registerUi(UiWrapper ui) {
		interfaces.add(ui);
	}
	
	public MotherShip getMotherShip() {
		return motherShip;
	}

	// --- PUBLIC METHODS ---
	public void startGame() {
		spawnMotherShip();		
	}
	
	// TODO DEBUG: Only for testing purposes
	public void addAction(Action act) {
		actTodo.addLast(act);
	}
	
	public void undo() {
		Action act = actDone.pollFirst();
		if (act!=null) {
			act.undoAction();
		}
	}
	
	// TODO DEBUG: Only for testing purposes
	public void tick() {
		Action act = actTodo.pollFirst();
		if (act!=null) {
			act.doAction();
			actDone.addFirst(act);
		}
	}

	// --- PRIVATE METHODS ---
	private void spawnMotherShip() {
		int bW = board.getWidth(), bH = board.getHeight(), x, y;
		Random alea = new Random();
		
		boolean shipSpawned = false;
		while (!shipSpawned) {
			try {
				x = alea.nextInt(bW);
				y = alea.nextInt(bH);
				
				board.moveShip(x,y,getMotherShip());
				shipSpawned = true;
			} catch (UnallowedEx ex) {
				// Ignore
			}
		}
	}
}
