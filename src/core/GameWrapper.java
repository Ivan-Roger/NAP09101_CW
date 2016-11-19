package core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import ui.UiWrapper;
import core.actions.Action;
import core.actions.ShipSpawnAction;
import core.exception.GameEx;
import core.exception.InvalidActionEx;
import core.exception.InvalidArgumentEx;
import core.exception.NotInitializedEx;
import core.exception.OutOfBoundsEx;
import core.exception.UnallowedEx;
import core.ships.EnemyShip;
import core.ships.StarFighterShip;
import core.ships.mothership.MotherShip;
import core.ships.mothership.MotherShipFactory;
import core.ships.mothership.ShipBehaviourEnum;

public class GameWrapper {
	
	// --- CONSTANTS ---
	public static final int DEFAULT_BOARD_WIDTH = 4;
	public static final int DEFAULT_BOARD_HEIGHT = 4;
	public static final ShipBehaviourEnum  DEFAULT_MOTHERSHIP_BEHAVIOUR = ShipBehaviourEnum.ATTACK_MODE;
	
	// --- VARIABLES ---
	private ArrayList<UiWrapper> interfaces = new ArrayList<>();
	
	private GameBoard board;
	private MotherShip motherShip;
	private ArrayDeque<Action> actTodo = new ArrayDeque<>();
	private ArrayDeque<Action> actDone = new ArrayDeque<>();
	
	// --- CONSTRUCTORS ---
	public GameWrapper() throws InvalidArgumentEx {
		this(DEFAULT_BOARD_WIDTH, DEFAULT_BOARD_HEIGHT);
	}
	
	public GameWrapper(int width, int height) throws InvalidArgumentEx {
		setBoard(new GameBoard(this, width, height));
		
		// TODO: Use FactoryPattern ?
		motherShip = MotherShipFactory.create(this, DEFAULT_MOTHERSHIP_BEHAVIOUR);
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
		for (UiWrapper ui : interfaces) {
			ui.startGame(board);
		}
	}
	
	// TODO DEBUG: Only for testing purposes (?)
	public void addAction(Action act) throws InvalidArgumentEx {
		if (act==null) throw new InvalidArgumentEx("Action can't be null");
		actTodo.addLast(act);
		System.out.println("ACTION | Registered > "+act);
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
	
	// TODO DEBUG: Public only for testing purposes
	public void tick() throws InvalidActionEx {
		System.out.println(" TICK  | --- New turn ---");
		
		try {
			for (EnemyShip s : board.getEnemyShips()) {
				s.move();
			}
			motherShip.act();
			motherShip.move();
		} catch (NotInitializedEx e) {
			// TODO Unexpected: Ship not spawned
			System.err.println("Ship was not spawned !");
		}
		
		Random alea = new Random();
		if (alea.nextInt(3)!=0) {
			StarFighterShip ship = new StarFighterShip(this);
			try {
				addAction(new ShipSpawnAction(ship, board.getTile(0, 0)));
			} catch (InvalidArgumentEx e) {
				// TODO Exception, Impossible: Ship already spawned
				e.printStackTrace();
			} catch (OutOfBoundsEx e) {
				// TODO Exception: Invalid board. No tile at 0,0
				e.printStackTrace();
			}
		}
		
		while(!actTodo.isEmpty()) {
			performAction();
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
	}
	
	private void performAction() throws InvalidActionEx {
		Action act = actTodo.pollFirst();
		try {
			act.doAction(this);
			actDone.addFirst(act);
			updateInterfaces();
		} catch (GameEx e) {
			throw new InvalidActionEx(e);
		}
	}

	private void updateInterfaces() {
		for (UiWrapper ui : interfaces) {
			ui.updateBoard(board);
		}
	}

	public void end(boolean win) {
		System.out.println("\n---------------------------------------------------------------------------\n"
				+ "\t\t YOU "+(win?"WON":"LOST")+" !");
		System.exit(0);
	}
}
