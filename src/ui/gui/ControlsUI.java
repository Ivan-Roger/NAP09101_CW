package ui.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import core.GameWrapper;
import core.events.FightEvent;
import core.events.GameEvent;
import core.events.GameEventType;
import core.events.NewTurnEvent;

@SuppressWarnings("serial")
public class ControlsUI extends JPanel {
	private GameWrapper game;
	
	private JLabel turnLbl;
	private JButton playBtn;
	private JTextArea gameStats;
	private JTextArea gameLog;

	public ControlsUI(GameWrapper game) {
		this.game = game;
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
		centerPanel.setLayout(new GridLayout(2,1));
		
		// CENTER - Cell 1 >> Stats
		gameStats = new JTextArea();
		gameStats.setEditable(false);
		gameStats.setBackground(this.getBackground());
		centerPanel.add(gameStats);
		
		// CENTER - Cell 2 >> Log
		gameLog = new JTextArea();
		gameLog.setEditable(false);
		JScrollPane logPane = new JScrollPane(gameLog);
		logPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		centerPanel.add(logPane);
		
		this.add(centerPanel, BorderLayout.CENTER);
		
		// BOTTOM -- PLAY button
		playBtn = new JButton("PLAY");
		playBtn.setEnabled(false);
		playBtn.setPreferredSize(new Dimension(300, 50));
		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playBtn.setEnabled(false);
				game.play();
			}
		});
		this.add(playBtn, BorderLayout.SOUTH);
	}

	public void update(GameEvent evt) {
		GameEventType type = evt.getType();
		if (type == GameEventType.GAME_START) {
			playBtn.setEnabled(true);
		} else if (type == GameEventType.NEW_TURN) {
			NewTurnEvent event = (NewTurnEvent) evt;
			turnLbl.setText("Turn "+event.getTurnNumber());
			gameLog.setText("--- TURN "+event.getTurnNumber()+" ---");
		} else if (type == GameEventType.TURN_OVER) {
			playBtn.setEnabled(!game.isOver());
		} else if (type == GameEventType.SHIP_DESTROYED) {
			gameStats.setText("Enemies: "+game.getBoard().getEnemyShips().size());
		} else if (type == GameEventType.SHIP_SPAWN) {
			gameStats.setText("Enemies: "+game.getBoard().getEnemyShips().size());
		} else if (type == GameEventType.FIGHT) {
			FightEvent event = (FightEvent) evt;
			gameLog.append("\nFight against "+event.getPosition().getEnemies().size()+" enemies.");
		}
	}
	
}
