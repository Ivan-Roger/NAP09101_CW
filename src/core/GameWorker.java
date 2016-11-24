package core;

import java.util.Random;

import core.actions.ShipSpawnAction;
import core.events.NewTurnEvent;
import core.events.TurnOverEvent;
import core.exceptions.InvalidArgumentEx;
import core.exceptions.NotInitializedEx;
import core.exceptions.OutOfBoundsEx;
import core.ships.EnemyShip;
import core.ships.StarFighterShip;

public class GameWorker extends Thread {
	private GameWrapper game;
	
	public GameWorker(GameWrapper game) {
		this.game = game;
	}

	@Override
	public void run() {
		System.out.println("THREAD | Started !");
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
			} catch (NotInitializedEx e) {
				// TODO Unexpected: Ship not spawned
				System.err.println("Ship was not spawned !");
			}
			
			Random alea = new Random();
			if (alea.nextInt(3)!=0) {
				StarFighterShip ship = new StarFighterShip(game);
				try {
					game.addAction(new ShipSpawnAction(ship, game.getBoard().getTile(0, 0)));
				} catch (InvalidArgumentEx e) {
					// TODO Exception, Impossible: Ship already spawned
					e.printStackTrace();
				} catch (OutOfBoundsEx e) {
					// TODO Exception: Invalid board. No tile at 0,0
					e.printStackTrace();
				}
			}
	
			game.performActions();
			
			if (game.getMotherShip().getPosition().getEnemies().size()>0) {
				try {
					Thread.sleep(750);
				} catch (InterruptedException e) {
					// TODO Unable to sleep
					e.printStackTrace();
				}
			}
			
			game.getMotherShip().act();
			
			game.updateInterfaces(new TurnOverEvent(game.getTurn()));
		}
	}
}
