package net.altkey12.wols.view.component;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class GradientBackgroundPage extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public GradientBackgroundPage() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Paint p = new GradientPaint(
				0.0f, 
				0.0f, 
				Css.TOP_START, 
				getWidth(), 
				getHeight(), 
				Css.TOP_END,
				true);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(p);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}
}
