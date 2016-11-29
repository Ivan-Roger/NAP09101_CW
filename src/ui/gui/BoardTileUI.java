package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.BoardTile;
import core.BoardTileType;
import core.ships.EnemyShipType;
import core.ships.Ship;
import core.ships.mothership.MotherShip;

@SuppressWarnings("serial")
public class BoardTileUI extends JPanel {
	private GraphicUI ui;
	private HashMap<String, JLabel> shipList;
	private HashMap<String, JLabel> shipCountList;

	public BoardTileUI(BoardTile tile, GraphicUI ui) {
		this.ui = ui;
		shipList = new HashMap<>();
		shipCountList = new HashMap<>();
		initContent(tile);
	}

	private void initContent(BoardTile tile) {
		Color backColor = Color.GRAY; // Default background
		if (tile.getType()==BoardTileType.BLACKHOLE) backColor = Color.DARK_GRAY;
		this.setBackground(backColor);
		
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		this.setLayout(new BorderLayout());
		
		if (tile.getType()==BoardTileType.BLACKHOLE) {
			JLabel typeLbl = new JLabel("Black hole", JLabel.CENTER);
			typeLbl.setForeground(Color.LIGHT_GRAY);
			this.add(typeLbl, BorderLayout.SOUTH);
		}
		
		JPanel shipsPanel = new JPanel();
		shipsPanel.setLayout(new GridLayout(1,2));
		shipsPanel.setBackground(backColor);
		
		JLabel motherShip = new JLabel("", JLabel.CENTER);
		try {
			motherShip.setIcon(BoardUI.getShipIcon(MotherShip.class.getSimpleName()));
		} catch (IOException ex) {
			ui.displayException(ex, true);
		}
		motherShip.setVisible(false);
		shipList.put(MotherShip.class.getSimpleName(), motherShip);
		shipsPanel.add(motherShip);
		
		JPanel enemyShipsPanel = new JPanel();
		enemyShipsPanel.setBackground(backColor);
		enemyShipsPanel.setLayout(new GridLayout(3,2));
		for (EnemyShipType type : EnemyShipType.values()) {
			JLabel ship = new JLabel("", JLabel.CENTER);
			try {
				ship.setIcon(BoardUI.getShipIcon(type.getClassName()));
			} catch (IOException ex) {
				ui.displayException(ex, true);
			}
			ship.setVisible(false);
			shipList.put(type.getClassName(), ship);
			enemyShipsPanel.add(ship);
			
			JLabel shipCount = new JLabel("x0", JLabel.CENTER);
			shipCount.setForeground(Color.LIGHT_GRAY);
			shipCount.setVisible(false);
			shipCountList.put(type.getClassName(), shipCount);
			enemyShipsPanel.add(shipCount);
		}
		shipsPanel.add(enemyShipsPanel);
		
		this.add(shipsPanel, BorderLayout.CENTER);
	}
	
	public void addShip(Ship ship) {
		shipList.get(ship.getClass().getSimpleName()).setVisible(true);
		if (!(ship instanceof MotherShip)) {
			JLabel count = shipCountList.get(ship.getClass().getSimpleName());
			int oldCount = Integer.parseInt(count.getText().substring(1));
			count.setText("x"+(oldCount+1));
			if (oldCount==0) count.setVisible(true);
		}
	}
	
	public void destroyShip(Ship ship) {
		try {
			shipList.get(ship.getClass().getSimpleName()).setIcon(BoardUI.getBlownShipIcon());
		} catch (IOException ex) {
			ui.displayException(ex, true);
		}
	}
	
	public void resetTile(BoardTile tile) {
		for (EnemyShipType type : EnemyShipType.values()) {
			try {
				shipList.get(type.getClassName()).setIcon(BoardUI.getShipIcon(type.getClassName()));
			} catch (IOException ex) {
				ui.displayException(ex, true);
			}
		}
	}
	
	public void removeShip(Ship ship) {
		if (ship instanceof MotherShip) {
			shipList.get(ship.getClass().getSimpleName()).setVisible(false);
		} else {
			JLabel count = shipCountList.get(ship.getClass().getSimpleName());
			int newCount = Integer.parseInt(count.getText().substring(1))-1;
			count.setText("x"+newCount);
			if (newCount==0) {
				count.setVisible(false);
				shipList.get(ship.getClass().getSimpleName()).setVisible(false);
			}
		}
	}
}
