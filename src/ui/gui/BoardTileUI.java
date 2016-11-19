package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.BoardTile;
import core.BoardTileType;
import core.ships.EnemyShip;
import core.ships.Ship;

@SuppressWarnings("serial")
public class BoardTileUI extends JPanel {
	private ArrayList<JLabel> shipList;

	public BoardTileUI(BoardTile tile) {
		shipList = new ArrayList<>();
		initContent(tile);
	}

	private void initContent(BoardTile tile) {
		Color backColor = Color.GRAY; // Default background
		if (tile.getType()==BoardTileType.BLACKHOLE) backColor = Color.DARK_GRAY;
		this.setBackground(backColor);
		
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		this.setLayout(new BorderLayout());
		
		this.add(new JLabel(tile.getX()+", "+tile.getY(), JLabel.CENTER), BorderLayout.NORTH);
		
		JPanel shipGrid = new JPanel();
		shipGrid.setLayout(new GridLayout(10,2));
		shipGrid.setBackground(backColor);
		for (int i=0; i<10*2; i++) {
			JLabel ship = new JLabel("", JLabel.CENTER);
			ship.setForeground(Color.LIGHT_GRAY);
			shipList.add(ship);
			shipGrid.add(ship);
		}
		this.add(shipGrid, BorderLayout.CENTER);
	}
	
	public void update(BoardTile tile) {
		ArrayList<EnemyShip> ships = tile.getEnemies();
		int i = 0;
		if (tile.hasMotherShip()) {
			shipList.get(i).setText("MOTHER SHIP");
			i++;
		}
		for (; i<10*2; i++) {
			Ship s = null;
			try {
				s = ships.get(i);
			} catch (IndexOutOfBoundsException e) {}
			shipList.get(i).setText(s!=null?s.getClass().getSimpleName():"");
		}
	}
}
