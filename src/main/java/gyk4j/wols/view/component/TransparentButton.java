package gyk4j.wols.view.component;

import java.awt.Color;

import javax.swing.JButton;

public class TransparentButton extends JButton {

	private static final long serialVersionUID = 1L;

	public TransparentButton(String text) {
		this(text, Color.BLACK);
	}
	
	public TransparentButton(String text, Color foregroundColor) {
		super(text);
		setFont(Css.FONT_14PT);
		setForeground(foregroundColor);
		setOpaque(false);
		setContentAreaFilled(false);
        setFocusPainted(false);
//        setBorderPainted( false );
        setBorder(Css.TRANSPARENT_BORDER);
	}
}
