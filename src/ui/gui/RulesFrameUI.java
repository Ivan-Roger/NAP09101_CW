package ui.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class RulesFrameUI extends JFrame {
	private JTextArea rulesArea;
	
	public RulesFrameUI() {
		super("Sky Wars - Rules");
		this.setSize(550, 650);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		initContent();
	}

	private void initContent() {
		this.setLayout(new BorderLayout());
		
		JPanel titleP = new JPanel();
		titleP.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
		JLabel title = new JLabel("Sky Wars", JLabel.CENTER);
		title.setFont(title.getFont().deriveFont(Font.BOLD, 30));
		title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		titleP.add(title);
		this.add(titleP, BorderLayout.NORTH);
		
		rulesArea = new JTextArea();
		rulesArea.setEditable(false);
		rulesArea.setFont(new Font("Monospace", Font.PLAIN, 15));
		rulesArea.setWrapStyleWord(true);
		rulesArea.setLineWrap(true);
		rulesArea.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
		rulesArea.setText("LOADING");
		
		this.add(rulesArea, BorderLayout.CENTER);
	}
	
	public void display() {
		this.setVisible(true);
		if (rulesArea.getText().equals("LOADING")) rulesArea.setText(getText());
	}
	
	private String getText() {
		InputStream inputStream = this.getClass().getResourceAsStream("Rules-Credits.txt");
		Scanner scanner = new Scanner(inputStream);
		scanner.useDelimiter("\\A");
	    String res = scanner.hasNext() ? scanner.next() : "";
	    scanner.close();
	    try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR:\n"+e.getMessage();
		}
	    return res;
	}
}
