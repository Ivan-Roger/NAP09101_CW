package ui.gui;

import core.GameWrapper;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.GameOverEvent;
import core.events.GameStartEvent;
import ui.UiWrapper;

public class GraphicUI extends UiWrapper {
	//private MenuFrameUI menuF;
	private GameFrameUI gameF;

	public GraphicUI(GameWrapper game) {
		super(game);
		gameF = new GameFrameUI(game);
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
	}
}
