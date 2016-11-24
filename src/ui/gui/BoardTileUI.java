package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
		shipGrid.setLayout(new GridLayout(0,3));
		shipGrid.setBackground(backColor);
		for (int i=0; i<4; i++) {
			JLabel ship = new JLabel("", JLabel.CENTER);
			ship.setForeground(Color.LIGHT_GRAY);
			shipList.add(ship);
			shipGrid.add(ship);
		}
		this.add(shipGrid, BorderLayout.CENTER);
	}
	
	public void update(BoardTile tile) {
		ArrayList<EnemyShip> ships = tile.getEnemies();
		if (tile.hasMotherShip()) {
			try {
				//System.out.println(" DEBUG | Loading image MotherShip");
				BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("assets/MotherShip.png"));
				ImageIcon icon = new ImageIcon(img);
				shipList.get(0).setIcon(icon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else shipList.get(0).setIcon(null);
		
		if (ships.size()>0) {
			EnemyShip s = ships.get(0);
			try {
				//System.out.println(" DEBUG | Loading image for: "+s);
				BufferedImage img = ImageIO.read(this.getClass().getResourceAsStream("assets/"+s.getClass().getSimpleName()+".png"));
				ImageIcon icon = new ImageIcon(img);
				shipList.get(1).setIcon(icon);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else shipList.get(1).setIcon(null);
	}
}
