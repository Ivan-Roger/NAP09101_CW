package ui.gui.components;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class JIntegerInput extends JFormattedInput {
	private static final char[] ALLOWED_CHARS = {'0','1','2','3','4','5','6','7','8','9','-'};
	private int max;
	private int min;
	private Color foreground;

	public JIntegerInput() {
		this(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public JIntegerInput(int min) {
		this(min, Integer.MAX_VALUE);
	}
	
	public JIntegerInput(int min, int max) {
		this.min = min;
		this.max = max;
		this.foreground = getForeground();

		final JIntegerInput input = this;
		this.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				boolean found = false;
				for (char c : ALLOWED_CHARS) {
					if (e.getKeyChar()==c)
						found = true;
				}
				if (!found) {
					e.consume();
					return;
				}
				input.setForeground(checkValue(e)?foreground:Color.RED);
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});
		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {}

			@Override
			public void focusLost(FocusEvent e) {
				correctValue();
			}
		});
	}
	
	public int getValue() {
		return Integer.parseInt(this.getText());
	}
	
	private boolean checkValue(String valTxt) {
		System.out.println("INPUT > "+valTxt);
		try {
			int val = Integer.parseInt(valTxt);
			if (val<min || val>max) return false;
		} catch(NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	private boolean checkValue() {
		return checkValue(this.getText());
	}
	
	private boolean checkValue(KeyEvent e) {
		String valTxt = this.getText();
		if (e.getKeyChar()!='')
			valTxt+=e.getKeyChar();
		return checkValue(valTxt);
	}
	
	private void correctValue() {
		this.setForeground(foreground);
		if (checkValue()) return;

		String valTxt = this.getText();
		try {
			int val = Integer.parseInt(valTxt);
			if (val<min) this.setValue(min);
			if (val>max) this.setValue(max);
		} catch(NumberFormatException ex) {
			this.setValue((max-min)/2);
		}
	}
	
	@Override
	public void setForeground(Color c) {
		super.setForeground(c);
		foreground = c;
	}
	
	private void setValue(int i) {
		this.setText(""+i);
	}
}
