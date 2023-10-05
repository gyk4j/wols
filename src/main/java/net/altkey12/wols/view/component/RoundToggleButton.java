package net.altkey12.wols.view.component;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

public class RoundToggleButton extends JToggleButton {
	private static final long serialVersionUID = 1L;
	
	public static final int INSET = 4;
	public static final int WIDTH = 2;
	public static final Border BORDER_OFF = BorderFactory.createEmptyBorder(WIDTH, WIDTH, WIDTH, WIDTH);
	public static final BasicStroke stroke = new BasicStroke(WIDTH);

	public RoundToggleButton(Icon icon) {
		super(icon);
		
		setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BORDER_OFF);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		ButtonModel buttonModel = this.getModel();
		if (buttonModel.isSelected()) {
			Graphics2D g2d = (Graphics2D) g;
			
		    //Set  anti-alias!
		    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		            RenderingHints.VALUE_ANTIALIAS_ON); 

		   // Set anti-alias for text
		    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		            RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 

			
			g2d.setStroke(stroke);
			g2d.setColor(Css.COLOR_PAGE);
			g2d.drawOval(WIDTH, WIDTH, getWidth()-(WIDTH*2), getHeight()-(WIDTH*2));
		}
	}

	@Override
	public Insets getInsets() {
		return new Insets(INSET, INSET, INSET, INSET);
	}
	
	
}
