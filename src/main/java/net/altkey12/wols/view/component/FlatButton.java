package net.altkey12.wols.view.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class FlatButton extends JButton {
	
	private static final long serialVersionUID = 1L;

	public FlatButton(String text) {
		this(text, Color.WHITE, Color.DARK_GRAY);
	}
	
	public FlatButton(String text, Color foregroundColor, Color backgroundColor) {
		super(text);
		setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(foregroundColor);
        setBackground(backgroundColor);
        setBorder(Css.BUTTON_BORDER);
        setFont(Css.FONT_14PT);
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}
