package core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import ui.UiWrapper;
import core.actions.Action;
import core.events.ExceptionEvent;
import core.events.GameEvent;
import core.events.GameOverEvent;
import core.events.GameQuitEvent;
import core.events.GameStartEvent;
import core.events.ShipSpawnEvent;
import core.exceptions.GameEx;
import core.exceptions.InvalidActionEx;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.OutOfBoundsEx;
import core.exceptions.UnallowedEx;
import core.ships.mothership.MotherShip;
import core.ships.mothership.MotherShipFactory;
import core.ships.mothership.ShipBehaviourEnum;

public class GameWrapper {
	
	// --- CONSTANTS ---
	public static final int DEFAULT_BOARD_WIDTH = 4;
	public static final int DEFAULT_BOARD_HEIGHT = 4;
	public static final int DEFAULT_DIFFICULTY = 3;
	public static final ShipBehaviourEnum  DEFAULT_MOTHERSHIP_BEHAVIOUR = ShipBehaviourEnum.ATTACK_MODE;
	
	// --- VARIABLES ---
	private ArrayList<UiWrapper> interfaces = new ArrayList<>();
	private GameWorker myWorker;
	
	private int difficulty;
	private GameBoard board;
	private MotherShip motherShip;
	private int turn; 
	private boolean gameOver = true;
	private ArrayDeque<Action> actTodo = new ArrayDeque<>();
	private ArrayDeque<Action> actDone = new ArrayDeque<>();
	
	// --- CONSTRUCTORS ---
	public GameWrapper() throws InvalidArgumentEx {
		this(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT, DEFAULT_DIFFICULTY);
	}
	
	public GameWrapper(int width, int height) throws InvalidArgumentEx {
		this(width, height, DEFAULT_DIFFICULTY);
	}
	
	public GameWrapper(int width, int height, int difficulty) throws InvalidArgumentEx {
		setBoard(new GameBoard(this, width, height));
		this.difficulty = difficulty;
		
		motherShip = MotherShipFactory.create(this, DEFAULT_MOTHERSHIP_BEHAVIOUR);
		myWorker = new GameWorker(this);
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
	
	public boolean isOver() {
		return gameOver;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void nextTurn() {
		turn++;
	}
	
	public int getDifficulty() {
		return difficulty;
	}

	// --- PUBLIC METHODS ---
	public void startGame() {
		updateInterfaces(new GameStartEvent(board));
		spawnMotherShip();
		gameOver = false;
		turn = 0;
		myWorker.start();
	}
	
	public void addAction(Action act) throws InvalidArgumentEx {
		if (act==null) throw new InvalidArgumentEx("Action can't be null");
		actTodo.addLast(act);
		//System.out.println("ACTION | Registered > "+act);
	}
	
	public void undo() throws InvalidActionEx {
		Action act = actDone.pollFirst();
		if (act==null) return;
		try {
			act.undoAction(this);
			updateInterfaces(act.getUndoEvent());
		} catch (GameEx e) {
			throw new InvalidActionEx(e);
		}
	}
	
	public boolean canUndo() {
		return !actDone.isEmpty();
	}
	
	public void play() {
		if (gameOver) return;
		if (!myWorker.isAlive()) return;
		System.out.println("WRAPPER| Notify thread ...");
		synchronized (myWorker) {
			myWorker.notify();
		}
	}
	
	public void performActions() {
		while (!actTodo.isEmpty()) {
			Action act = actTodo.pollFirst();
			try {
				act.doAction(this);
				actDone.addFirst(act);
				updateInterfaces(act.getDoEvent());
			} catch (Exception ex) {
				System.out.println("EXCEPT | "+ex.getClass().getSimpleName()+": "+ex.getMessage());
				ex.printStackTrace();
				updateInterfaces(new ExceptionEvent(ex, false));
			}
		}
	}

	public void updateInterfaces(GameEvent event) {
		for (UiWrapper ui : interfaces) {
			ui.update(event);
		}
	}

	public void end(boolean win) {
		System.out.println("\n---------------------------------------------------------------------------\n"
				+ "\t\t YOU "+(win?"WON":"LOST")+" THE GAME !");
		gameOver=true;
		updateInterfaces(new GameOverEvent(win));
	}

	public void stopGame() {
		System.out.println("\t\t Player left the game."
				+ "\n---------------------------------------------------------------------------");
		gameOver=true;
		updateInterfaces(new GameQuitEvent());
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
				board.spawnShip(motherShip, tile);
				shipSpawned = true;
			} catch (UnallowedEx e) {
				// Ignore
			} catch (OutOfBoundsEx e) {
				System.err.println("[EXCEPT] Random spawn position was invalid.");
			} catch (InvalidArgumentEx e) {
				System.err.println("Mothership already spawned.");
			}
		}
		updateInterfaces(new ShipSpawnEvent(motherShip));
	}
}
