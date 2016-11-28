package core;

import java.util.Random;

import core.actions.ShipSpawnAction;
import core.events.ExceptionEvent;
import core.events.NewTurnEvent;
import core.events.TurnOverEvent;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.EnemyShip;
import core.ships.Ship;
import core.ships.StarCruiserShip;
import core.ships.StarFighterShip;
import core.ships.StarShooterShip;

public class GameWorker extends Thread {
	private GameWrapper game;
	
	public GameWorker(GameWrapper game) {
		this.game = game;
	}

	@Override
	public void run() {
		System.out.println("THREAD | Started !");
		game.updateInterfaces(new TurnOverEvent(game.getTurn()));
		while (!game.isOver()) {
			System.out.println("THREAD | Waiting ...");
			
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			game.nextTurn();
			game.updateInterfaces(new NewTurnEvent(game.getTurn()));
			System.out.println(" TICK  | --- New turn ---");
			
			try {
				for (EnemyShip s : game.getBoard().getEnemyShips()) {
					s.move();
				}
				game.getMotherShip().move();
			} catch (NotInitializedEx ex) {
				// Unexpected, Ship not spawned
				System.out.println("EXCEPT | "+ex.getClass().getSimpleName()+": "+ex.getMessage());
				ex.printStackTrace();
				game.updateInterfaces(new ExceptionEvent(ex, true));
				System.exit(-4);
			}
			
			Random alea = new Random();
			if (alea.nextInt(game.getDifficulty())==0) {
				Ship ship;
				switch (alea.nextInt(3)) {
				case 0: ship = new StarFighterShip(game); break;
				case 1: ship = new StarShooterShip(game); break;
				default: ship = new StarCruiserShip(game); break;
				}
				try {
					game.addAction(new ShipSpawnAction(ship, game.getBoard().getTile(0, 0)));
				} catch (InvalidArgumentEx ex) {
					// Impossible: Ship already spawned
					System.out.println("EXCEPT | "+ex.getClass().getSimpleName()+": "+ex.getMessage());
					ex.printStackTrace();
					game.updateInterfaces(new ExceptionEvent(ex, true));
					System.exit(-4);
				} catch (OutOfBoundsEx ex) {
					// Invalid board. No tile at 0,0
					System.out.println("EXCEPT | "+ex.getClass().getSimpleName()+": "+ex.getMessage());
					ex.printStackTrace();
					game.updateInterfaces(new ExceptionEvent(ex, true));
					System.exit(-4);
				}
			}
	
			game.performActions();
			
			game.getMotherShip().act();
			
			game.updateInterfaces(new TurnOverEvent(game.getTurn()));
		}
	}
}
