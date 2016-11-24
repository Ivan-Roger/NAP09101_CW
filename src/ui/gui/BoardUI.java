package ui.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import core.GameBoard;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.ShipMoveEvent;
import core.events.ShipRemovedEvent;
import core.events.ShipSpawnEvent;
import core.exceptions.OutOfBoundsEx;

@SuppressWarnings("serial")
public class BoardUI extends JPanel {
	private ArrayList<BoardTileUI> tiles;

	public BoardUI(GameBoard board) {
		tiles = new ArrayList<>();
		initContent(board);
	}

	private void initContent(GameBoard board) {
		this.setLayout(new GridLayout(board.getHeight(), board.getWidth()));

		try {
			for (int i=0; i<board.getSize(); i++) {
				BoardTileUI tile = new BoardTileUI(board.getTile(i));
				tiles.add(tile);
				this.add(tile);
			}
		} catch (OutOfBoundsEx e) {
			// TODO Exception, Board malformed. throw InitError
			e.printStackTrace();
		}
	}

	public void update(GameEvent evt) {
		if (evt.getType()==GameEventType.SHIP_SPAWN) {
			ShipSpawnEvent event = (ShipSpawnEvent) evt;
			tiles.get(event.getPosition().getIndex()).update(event.getPosition());
		} else if (evt.getType()==GameEventType.SHIP_MOVE) {
			ShipMoveEvent event = (ShipMoveEvent) evt;
			tiles.get(event.getOrigin().getIndex()).update(event.getOrigin());
			tiles.get(event.getDestination().getIndex()).update(event.getDestination());
		} else if (evt.getType()==GameEventType.SHIP_REMOVED) {
			ShipRemovedEvent event = (ShipRemovedEvent) evt;
			tiles.get(event.getPosition().getIndex()).update(event.getPosition());
		}
	}
}
