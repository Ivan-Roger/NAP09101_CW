package ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.GameWrapper;
import core.events.FightEndEvent;
import core.events.FightStartEvent;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.GameOverEvent;
import core.events.NewTurnEvent;
import core.events.ShipSpawnEvent;
import core.ships.EnemyShip;
import core.ships.mothership.ShipBehaviourAttack;
import core.ships.mothership.ShipBehaviourDefend;
import core.ships.mothership.ShipBehaviourEnum;

@SuppressWarnings("serial")
public class ControlsUI extends JPanel {
	private GameWrapper game;
	private GraphicUI ui;
	
	private JLabel turnLbl;
	private JLabel statEnemiesAlive;
	private JLabel statEnemiesKilled;
	private JLabel statPlayerWon;
	private JTextArea gameLog;
	private JButton playBtn;
	private JButton modeAttack;
	private JButton modeDefend;
	private ActionListener playListener;

	public ControlsUI(GameWrapper game, GraphicUI ui) {
		this.game = game;
		this.ui = ui;
		initContent();
	}

	private void initContent() {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(300,700));
		
		// TOP -- Turn
		turnLbl = new JLabel("Turn 0", JLabel.CENTER);
		turnLbl.setFont(turnLbl.getFont().deriveFont(Font.BOLD, 30));
		this.add(turnLbl, BorderLayout.NORTH);
		
		// CENTER -- Stats and Log
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
		
		// CENTER - TOP >> Stats
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new GridLayout(3,1));
		statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
		statsPanel.setBackground(this.getBackground());
		
		statEnemiesAlive = new JLabel("Enemies alive: 0");
		statsPanel.add(statEnemiesAlive);

		statEnemiesKilled = new JLabel("Enemies killed: 0");
		statsPanel.add(statEnemiesKilled);
		
		statPlayerWon = new JLabel("   ", JLabel.CENTER);
		statPlayerWon.setFont(statPlayerWon.getFont().deriveFont(Font.BOLD, 25));
		statsPanel.add(statPlayerWon);
		
		centerPanel.add(statsPanel, BorderLayout.NORTH);
		
		// CENTER - BOTTOM >> Log
		gameLog = new JTextArea();
		gameLog.setEditable(false);
		gameLog.setBackground(this.getBackground());
		JScrollPane logPane = new JScrollPane(gameLog);
		logPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		logPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		centerPanel.add(logPane, BorderLayout.CENTER);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		// BOTTOM -- Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		
		JButton rulesBtn = new JButton("Rules");
		rulesBtn.addActionListener(new ActionListener() { // TODO: Clean Listener
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.getRules().display();
			}
		});
		gbc.gridy = 0;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 0.0;
		buttonsPanel.add(rulesBtn, gbc);
		
		JButton undoBtn = new JButton("UNDO");
		undoBtn.addActionListener(new ActionListener() { // TODO: Clean Listener
			@Override
			public void actionPerformed(ActionEvent e) {
				if (game.canUndo()) {
					game.undo();
				}
			}
		});
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 0.0;
		buttonsPanel.add(undoBtn, gbc);

		modeAttack = new JButton("Attack mode");
		modeAttack.setEnabled(false);
		modeAttack.addActionListener(new ActionListener() { // TODO: Clean Listener
			@Override
			public void actionPerformed(ActionEvent e) {
				modeAttack.setEnabled(false);
				modeDefend.setEnabled(true);
				game.getMotherShip().setBehaviour(new ShipBehaviourAttack());
				gameLog.append("\nSwitching mode to ATTACK.");
			}
		});
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;
		buttonsPanel.add(modeAttack, gbc);
		
		modeDefend = new JButton("Defend mode");
		modeDefend.setEnabled(false);
		modeDefend.addActionListener(new ActionListener() { // TODO: Clean Listener
			@Override
			public void actionPerformed(ActionEvent e) {
				modeAttack.setEnabled(true);
				modeDefend.setEnabled(false);
				game.getMotherShip().setBehaviour(new ShipBehaviourDefend());
				gameLog.append("\nSwitching mode to DEFEND.");
			}
		});
		gbc.gridx++;
		buttonsPanel.add(modeDefend, gbc);

		playBtn = new JButton("PLAY");
		playBtn.setEnabled(false);
		playBtn.setPreferredSize(new Dimension(300, 50));
		playBtn.addActionListener(getPlayListener());
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		gbc.weightx = 0.0;
		buttonsPanel.add(playBtn, gbc);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	public void update(GameEvent evt) {
		GameEventType type = evt.getType();
		if (type == GameEventType.GAME_START) {
			playBtn.setEnabled(true);
			modeAttack.setEnabled(game.getMotherShip().getBehaviour()==ShipBehaviourEnum.DEFEND_MODE);
			modeDefend.setEnabled(game.getMotherShip().getBehaviour()==ShipBehaviourEnum.ATTACK_MODE);
			
		} else if (type == GameEventType.NEW_TURN) {
			NewTurnEvent event = (NewTurnEvent) evt;
			turnLbl.setText("Turn "+event.getTurnNumber());
			gameLog.setText("--- TURN "+event.getTurnNumber()+" ---");
			
		} else if (type == GameEventType.TURN_OVER) {
			playBtn.setEnabled(true);
			
		} else if (type == GameEventType.SHIP_SPAWN) {
			ShipSpawnEvent event = (ShipSpawnEvent) evt;
			statEnemiesAlive.setText("Enemies alive: "+game.getBoard().getEnemyShips().size());
			statEnemiesKilled.setText("Enemies killed: "+game.getBoard().getKillCount());
			if (event.getShip() instanceof EnemyShip)
				gameLog.append("\nNew enemy: "+event.getShip().getClass().getSimpleName()+" appeared at "+event.getPosition().getX()+", "+event.getPosition().getY()+".");
			
		} else if (type == GameEventType.FIGHT_START) {
			FightStartEvent event = (FightStartEvent) evt;
			gameLog.append("\nStarting fight against "+event.getPosition().getEnemies().size()+" enemies ...");
			
		} else if (type == GameEventType.FIGHT_END) {
			FightEndEvent event = (FightEndEvent) evt;
			int enemyCount = event.getEnemyCount();
			gameLog.append("\nYou "+(event.isPlayerWinner()?"destroyed":"have been killed by")+" "+enemyCount+" enemies.");
			statEnemiesAlive.setText("Enemies alive: "+game.getBoard().getEnemyShips().size());
			statEnemiesKilled.setText("Enemies killed: "+game.getBoard().getKillCount());
			
		} else if (type == GameEventType.GAME_OVER) {
			GameOverEvent event = (GameOverEvent) evt;
			statPlayerWon.setText("YOU "+(event.isPlayerWinner()?"WON":"LOST")+" !");
			playBtn.removeActionListener(playListener);
			modeAttack.setEnabled(false);
			modeDefend.setEnabled(false);
			playBtn.setText("QUIT");
			playBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					game.stopGame();
				}
			});
		}
	}
	
	private ActionListener getPlayListener() {
		playListener = new ActionListener() { // TODO: Clean Listener
			@Override
			public void actionPerformed(ActionEvent e) {
				playBtn.setEnabled(false);
				game.play();
			}
		};
		return playListener;
	}
}
