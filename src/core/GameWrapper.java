package core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import ui.UiWrapper;
import core.actions.Action;
import core.exception.GameEx;
import core.exception.InvalidActionEx;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;
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
		motherShip = new MotherShip(this);
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
		updateInterfaces();
	}
	
	// TODO DEBUG: Only for testing purposes
	public void addAction(Action act) throws InvalidArgumentEx {
		if (act==null) throw new InvalidArgumentEx("Action can't be null");
		actTodo.addLast(act);
	}
	
	public void undo() throws InvalidActionEx {
		Action act = actDone.pollFirst();
		if (act==null) return;
		try {
			act.undoAction(this);
			updateInterfaces();
		} catch (GameEx e) {
			throw new InvalidActionEx(e);
		}
	}
	
	public boolean canUndo() {
		return !actDone.isEmpty();
	}
	
	// TODO DEBUG: Only for testing purposes
	public void tick() throws InvalidActionEx {
		try {
			motherShip.move();
		} catch (NotInitializedEx e1) {
			System.err.println("Mothership was not spawned !");
		}
		
		Random alea = new Random();
		if (alea.nextInt(3)!=0) {
			
		}
		
		while(!actTodo.isEmpty()) {
			Action act = actTodo.pollFirst();
			try {
				act.doAction(this);
				actDone.addFirst(act);
				updateInterfaces();
			} catch (GameEx e) {
				throw new InvalidActionEx(e);
			}
		}
	}

	// --- PRIVATE METHODS ---
	private void spawnMotherShip() {
		int bW = board.getWidth(), bH = board.getHeight(), x, y;
		Random alea = new Random();
		
		boolean shipSpawned = false;
		while (!shipSpawned) {
			try {
				x = alea.nextInt(bW-1)+1;
				y = alea.nextInt(bH-1)+1;
				
				BoardTile tile = board.getTile(x, y);
				if (tile.getType()==BoardTileType.BLACKHOLE) throw new UnallowedEx("Invalid spawn position.");
				board.spawnShip(getMotherShip(), tile);
				shipSpawned = true;
			} catch (UnallowedEx e) {
				// Ignore
			} catch (OutOfBoundsEx e) {
				System.err.println("[EXCEPT] Random spawn position was invalid.");
			} catch (InvalidArgumentEx e) {
				System.err.println("[EXCEPT] Random spawn position was invalid.");
			}
		}
	}

	private void updateInterfaces() {
		for (UiWrapper ui : interfaces) {
			ui.updateBoard(board);
		}
	}
}
