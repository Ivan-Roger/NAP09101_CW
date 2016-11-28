package ui.gui;

import javax.swing.JOptionPane;

import core.GameWrapper;
import core.events.ExceptionEvent;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.GameOverEvent;
import core.events.GameStartEvent;
import ui.UiWrapper;

public class GraphicUI extends UiWrapper {
	private GameFrameUI gameF;
	private MenuFrameUI menuF;

	public GraphicUI(GameWrapper game) {
		super(game);
		gameF = new GameFrameUI(game, this);
	}
	
	public GraphicUI(GameWrapper game, MenuFrameUI menu) {
		this(game);
		setMenu(menu);
	}

	@Override
	public void update(GameEvent evt) {
		GameEventType type = evt.getType();
		if (
			type == GameEventType.GAME_START
		) {
			GameStartEvent event = (GameStartEvent) evt;
			gameF.start(event.getBoard());
		}

		if (
			type == GameEventType.GAME_START		||
			type == GameEventType.NEW_TURN			||
			type == GameEventType.TURN_OVER			||
			type == GameEventType.FIGHT_START		||
			type == GameEventType.FIGHT_END			||
			type == GameEventType.SHIP_SPAWN		||
			type == GameEventType.SHIP_MOVE			||
			type == GameEventType.SHIP_DESTROYED 	||
			type == GameEventType.SHIP_REMOVED 		||
			type == GameEventType.GAME_OVER
		) {
			gameF.update(evt);
		}
		
		if (
			type == GameEventType.GAME_OVER
		) {
			GameOverEvent event = (GameOverEvent) evt;
			boolean won = event.isPlayerWinner();
			gameF.end(won);
		}
		
		if (
			type == GameEventType.GAME_QUIT
		) {
			menuF.setVisible(true);
			gameF.setVisible(false);
		}
		
		if (
			type == GameEventType.EXCEPTION
		) {
			ExceptionEvent event = (ExceptionEvent) evt;
			Exception ex = event.getException();
			getGame().stopGame();
			JOptionPane.showMessageDialog(null, (event.isCritic()?"FATAL - ":"")+"Unexpected error:\n\n"+ex.getClass().getName()+": "+ex.getMessage(), (event.isCritic()?"FATAL ERROR":"Error"), (event.isCritic()?JOptionPane.ERROR_MESSAGE:JOptionPane.WARNING_MESSAGE));
		}		
	}

	public void setMenu(MenuFrameUI menu) {
		this.menuF = menu;
	}

	public MenuFrameUI getMenu() {
		return this.menuF;
	}

	public RulesFrameUI getRules() {
		return this.menuF.getRules();
	}
}
