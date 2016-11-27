package ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.GameBoard;
import core.events.FightEndEvent;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.ShipDestroyedEvent;
import core.events.ShipMoveEvent;
import core.events.ShipRemovedEvent;
import core.events.ShipSpawnEvent;
import core.exceptions.OutOfBoundsEx;

@SuppressWarnings("serial")
public class BoardUI extends JPanel {
	private ArrayList<BoardTileUI> tiles;
	
	private static HashMap<String,ImageIcon> shipIcons = new HashMap<>();
	private static ImageIcon blownShipIcon;

	public BoardUI(GameBoard board) {
		tiles = new ArrayList<>();
		initContent(board);
	}

	private void initContent(GameBoard board) {
		this.setLayout(new BorderLayout());

		JPanel xAxis = new JPanel();
		xAxis.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
		xAxis.setLayout(new GridLayout(1, board.getWidth()));
		for (int i=0; i<board.getWidth(); i++) {
			xAxis.add(new JLabel(""+i, JLabel.CENTER));
		}
		this.add(xAxis, BorderLayout.NORTH);
		
		JPanel yAxis = new JPanel();
		yAxis.setPreferredSize(new Dimension(18,18));
		yAxis.setLayout(new GridLayout(board.getHeight(), 1));
		for (int i=0; i<board.getHeight(); i++) {
			yAxis.add(new JLabel(""+i, JLabel.CENTER));
		}
		this.add(yAxis, BorderLayout.WEST);

		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(board.getHeight(), board.getWidth()));
		try {
			for (int i=0; i<board.getSize(); i++) {
				BoardTileUI tile = new BoardTileUI(board.getTile(i));
				tiles.add(tile);
				grid.add(tile);
			}
		} catch (OutOfBoundsEx e) {
			// TODO Exception, Board malformed. throw InitError
			e.printStackTrace();
		}
		this.add(grid, BorderLayout.CENTER);
	}

	public void update(GameEvent evt) {
		if (evt.getType()==GameEventType.SHIP_SPAWN) {
			ShipSpawnEvent event = (ShipSpawnEvent) evt;
			tiles.get(event.getPosition().getIndex()).addShip(event.getShip());
		} else if (evt.getType()==GameEventType.SHIP_MOVE) {
			ShipMoveEvent event = (ShipMoveEvent) evt;
			tiles.get(event.getOrigin().getIndex()).removeShip(event.getShip());
			tiles.get(event.getDestination().getIndex()).addShip(event.getShip());
		} else if (evt.getType()==GameEventType.SHIP_DESTROYED) {
			ShipDestroyedEvent event = (ShipDestroyedEvent) evt;
			tiles.get(event.getPosition().getIndex()).destroyShip(event.getShip());
		} else if (evt.getType()==GameEventType.FIGHT_END) {
			FightEndEvent event = (FightEndEvent) evt;
			tiles.get(event.getPosition().getIndex()).resetTile(event.getPosition());
		} else if (evt.getType()==GameEventType.SHIP_REMOVED) {
			ShipRemovedEvent event = (ShipRemovedEvent) evt;
			tiles.get(event.getPosition().getIndex()).removeShip(event.getShip());
		}
	}
	
	public static ImageIcon getShipIcon(String ship) {
		ImageIcon res = shipIcons.get(ship);
		if (res==null) {
			BufferedImage img;
			try {
				img = ImageIO.read(BoardUI.class.getResourceAsStream("assets/"+ship+".png"));
				res = new ImageIcon(img);
				shipIcons.put(ship, res);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	public static Icon getBlownShipIcon() {
		if (blownShipIcon==null) {
			try {
				BufferedImage img = ImageIO.read(BoardUI.class.getResourceAsStream("assets/BlownShip.png"));
				blownShipIcon = new ImageIcon(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return blownShipIcon;
	}
	
}
