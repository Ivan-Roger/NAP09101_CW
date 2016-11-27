package ui.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import core.GameWrapper;
import core.exceptions.InvalidArgumentEx;

@SuppressWarnings("serial")
public class MenuFrameUI extends JFrame {
	private JFormattedTextField setWidthInput;
	private JFormattedTextField setHeightInput;
	private JComboBox<String> setDifficultyInput;
	private RulesFrameUI rulesF;
	
	public MenuFrameUI() {
		super("Sky Wars - Menu");
		this.setSize(500, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initContent();
		rulesF = new RulesFrameUI();
	}

	private void initContent() {
		this.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Sky Wars", JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 50));
		title.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
		this.add(title, BorderLayout.NORTH);
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(0,1));
		center.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
		
		JPanel topButtonsPanel = new JPanel();
		topButtonsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
		topButtonsPanel.setLayout(new GridLayout(5,1));
		
		JButton quitBtn = new JButton("QUIT");
		quitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		topButtonsPanel.add(quitBtn);
		
		topButtonsPanel.add(new JLabel());
		
		JButton rulesBtn = new JButton("RULES");
		rulesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rulesF.display();
			}
		});
		topButtonsPanel.add(rulesBtn);
		
		topButtonsPanel.add(new JLabel());
		
		JButton startBtn = new JButton("START");
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		topButtonsPanel.add(startBtn);
		
		center.add(topButtonsPanel);
		
		JPanel gameSettings = new JPanel();
		gameSettings.setBorder(BorderFactory.createTitledBorder("Settings"));
		gameSettings.setLayout(new GridLayout(5,2));
		gameSettings.add(new JLabel());
		gameSettings.add(new JLabel());
		
		JLabel setWidthLbl = new JLabel("Board width:", JLabel.RIGHT);
		setWidthLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
		gameSettings.add(setWidthLbl);
		
		setWidthInput = new JFormattedTextField(NumberFormat.getNumberInstance());
		setWidthInput.setText(""+GameWrapper.DEFAULT_BOARD_WIDTH);
		gameSettings.add(setWidthInput);
		
		JLabel setHeightLbl = new JLabel("Board height:", JLabel.RIGHT);
		setHeightLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
		gameSettings.add(setHeightLbl);
		
		setHeightInput = new JFormattedTextField(NumberFormat.getNumberInstance());
		setHeightInput.setText(""+GameWrapper.DEFAULT_BOARD_HEIGHT);
		gameSettings.add(setHeightInput);
		
		JLabel setEnemyLbl = new JLabel("Enemy ratio:", JLabel.RIGHT);
		setEnemyLbl.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 20));
		gameSettings.add(setEnemyLbl);
		
		String[] setDifficultyOpts = {"Hard : 1/2", "Medium : 1/3", "Easy : 1/4"};
		setDifficultyInput = new JComboBox<>(setDifficultyOpts);
		gameSettings.add(setDifficultyInput);

		gameSettings.add(new JLabel());
		gameSettings.add(new JLabel());
		center.add(gameSettings);
		
		this.add(center, BorderLayout.CENTER);
		
		JLabel credits = new JLabel("Made by Ivan ROGER - 2016", JLabel.CENTER);
		credits.setFont(credits.getFont().deriveFont(Font.PLAIN));
		this.add(credits, BorderLayout.SOUTH);
		
	}
	
	private void startGame() {
		int width = Integer.parseInt(setWidthInput.getText());
		int height = Integer.parseInt(setHeightInput.getText());
		int difficulty = setDifficultyInput.getSelectedIndex()+2;
		GameWrapper game;
		try {
			game = new GameWrapper(width, height, difficulty);
		} catch (InvalidArgumentEx ex) {
			JOptionPane.showMessageDialog(null,
				"Invalid settings, unable to start game :\n"+ex.getMessage(),
				"Invalid settings", JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		GraphicUI ui = new GraphicUI(game, this);
		ui.setMenu(this);
		game.startGame();
		this.setVisible(false);
	}
	
	public RulesFrameUI getRules() {
		return rulesF;
	}

}
