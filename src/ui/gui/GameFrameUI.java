package ui.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.GameBoard;
import core.GameWrapper;
import core.events.GameEvent;
import core.events.GameEventType;

@SuppressWarnings("serial")
public class GameFrameUI extends JFrame {
	private boolean gameOver = true;
	private BoardUI boardUi;
	private ControlsUI controlsUi;

	public GameFrameUI(GameWrapper game) {
		super("Sky Wars - Game");
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		// TODO: Clean this listener
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				if (gameOver) {
					System.exit(0);
					// TODO: Return to menu
				}
				
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit ?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (result==JOptionPane.YES_OPTION) System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
		initContent(game);
	}
	
	private void initContent(GameWrapper game) {
		JPanel body = new JPanel();
		body.setLayout(new BorderLayout());
		
		GameBoard board = game.getBoard();
		boardUi = new BoardUI(board);
		body.add(boardUi, BorderLayout.CENTER);
		
		controlsUi = new ControlsUI(game);
		body.add(controlsUi, BorderLayout.EAST);
		
		this.add(body);
	}

	public void start(GameBoard board) {
		this.setVisible(true);
		gameOver = false;
	}

	public void update(GameEvent event) {
		GameEventType type = event.getType();
		if (
			type == GameEventType.GAME_START		||
			type == GameEventType.NEW_TURN			||
			type == GameEventType.SHIP_SPAWN		||
			type == GameEventType.FIGHT_START		||
			type == GameEventType.FIGHT_END			||
			type == GameEventType.TURN_OVER			||
			type == GameEventType.GAME_OVER
		) {
			controlsUi.update(event);
		}

		if (
			type == GameEventType.SHIP_SPAWN		||
			type == GameEventType.SHIP_MOVE			||
			type == GameEventType.SHIP_DESTROYED	||
			type == GameEventType.FIGHT_END			||
			type == GameEventType.SHIP_REMOVED
		) {
			boardUi.update(event);
		}
	}

	public void end(boolean won) {
		gameOver = true;
		JOptionPane.showMessageDialog(null, "You "+(won?"won":"lost")+" the game !", "Sky Wars - Game Over", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
