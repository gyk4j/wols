package net.altkey12.wols.view.component.news;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.time.LocalDateTime;

import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.Css;

public class NewBadgeLayerUI extends LayerUI<JComponent> {

	private static final long serialVersionUID = 1L;
	
	protected static final URL url = ResourceLoader.getFileFromResource("news_new_icon.png");
	protected static final Image overlay = Toolkit.getDefaultToolkit().getImage(url);
	
	private LocalDateTime lastViewed;
	
	public NewBadgeLayerUI(LocalDateTime lastViewed) {
		this.lastViewed = lastViewed;
	}
	
	public LocalDateTime getLastViewed() {
		return lastViewed;
	}

	public void setLastViewed(LocalDateTime lastViewed) {
		this.lastViewed = lastViewed;
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);

		Graphics2D g2 = (Graphics2D) g.create();
		/*
		//Set  anti-alias!
	    g2.setRenderingHint(
	    		RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON); 

	    // Set anti-alias for text
	    g2.setRenderingHint(
	    		RenderingHints.KEY_TEXT_ANTIALIASING,
	            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    */

		// int w = c.getWidth();
		// int h = c.getHeight();

//		g2.drawImage(overlay, 0, 0, null);
		
		g2.setColor(Css.COLOR_NEWS_NEW_OVERLAY_BACKGROUND);
		
		Polygon polygon = new Polygon();
		polygon.addPoint(0, 25);
		polygon.addPoint(0, 50);
		polygon.addPoint(50, 0);
		polygon.addPoint(25, 0);
		polygon.addPoint(0, 25);
		g2.fillPolygon(polygon);
		
		Font font = new Font(Font.DIALOG, Font.BOLD, 11);   
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.rotate(Math.toRadians(-45), 0, 0);
		Font rotatedFont = font.deriveFont(affineTransform);
		g2.setFont(rotatedFont);
		g2.setColor(Css.COLOR_NEWS_NEW_OVERLAY_FOREGROUND);
		g2.drawString("N E W", 12, 32);
		
		g2.dispose();
	}
}
