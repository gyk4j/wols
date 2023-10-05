package gyk4j.wols.view.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

public class GradientButton extends FlatButton {

	private static final long serialVersionUID = 1L;
	
	protected Color backgroundStartColor;
	protected Color backgroundEndColor;

	public GradientButton(String text, Color foregroundColor, Color backgroundStartColor, Color backgroundEndColor) {
		super(text, foregroundColor, backgroundStartColor);
		this.backgroundStartColor = backgroundStartColor;
		this.backgroundEndColor = backgroundEndColor;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Paint p = new GradientPaint(
				0.0f, 
				0.0f, 
				backgroundStartColor, 
				getWidth(), 
				getHeight(), 
				backgroundEndColor,
				true);
		
		Graphics2D g2d = (Graphics2D) g.create();		
		g2d.setPaint(p);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		
		Font f = Css.FONT_14PT.deriveFont(Font.BOLD);
		FontMetrics metrics = g.getFontMetrics(f);
		int x = getBounds().x + (getBounds().width - metrics.stringWidth(getText())) / 2;
		int y = getBounds().y + ((getBounds().height - metrics.getHeight() + metrics.getAscent()) / 2);
		g2d.setFont(f);
		g2d.setColor(getForeground());
		g2d.setPaint(getForeground());
		g2d.drawString(getText(), x, y);
		
		g2d.dispose();
	}
}
