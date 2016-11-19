package ui.gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.GameBoard;
import core.GameWrapper;

@SuppressWarnings("serial")
public class GameFrameUI extends JFrame {
	private BoardUI boardUi;

	public GameFrameUI(GameWrapper game) {
		super("Sky Wars - Game");
		this.setSize(900, 700);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
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
		
		this.add(body);
	}

	public void start(GameBoard board) {
		this.setVisible(true);
	}

	public void updateBoard(GameBoard board) {
		boardUi.updateBoard(board);
	}
	
}
