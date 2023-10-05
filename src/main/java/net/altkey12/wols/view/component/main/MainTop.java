package net.altkey12.wols.view.component.main;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.TransparentButton;
import net.altkey12.wols.view.component.TransparentLabel;

public class MainTop extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JComponent parent;
	
	JLabel logo = new TransparentLabel();
//	JPanel logo = new JPanel();
	JLabel banner = new TransparentLabel();
	JButton close = new TransparentButton("X");
	
	public MainTop(JComponent parent) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setParent(parent);
		
		ImageIcon icon = ResourceLoader.getImageIcon(Css.LOGO);
		logo.setIcon(icon);
		
//		logo.setMinimumSize(new Dimension(64, 64));
//		logo.setPreferredSize(new Dimension(64, 64));
//		logo.setMaximumSize(new Dimension(64, 64));
//		logo.setLayout(new GridLayout(2,2));
//		logo.setBorder(BorderFactory.createLineBorder(Css.BACKGROUND_SELECTED, 2));
//		logo.add(createLogo('W'));
//		logo.add(createLogo('O'));
//		logo.add(createLogo('L'));
//		logo.add(createLogo('S'));
		
		banner.setForeground(Css.BACKGROUND_SELECTED);
		banner.setFont(Css.FONT_BANNER);
		banner.setHorizontalAlignment(SwingConstants.CENTER);
		banner.setHorizontalTextPosition(SwingConstants.RIGHT);
		banner.setText("<html><center><p><u>W</u>ireless@SGx <u>O</u>n <br/><u>L</u>inux <u>S</u>ystems</p></center></html>");
		
		close.setHorizontalAlignment(SwingConstants.RIGHT);
		close.setFont(Css.FONT_LOGO);
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().closeWindow();
			}
			
		});
		
		add(logo);
		add(Box.createHorizontalGlue());
		add(banner);
		add(Box.createHorizontalGlue());
		add(close);
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

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}

	/*
	private JLabel createLogo(char c) {
		JLabel l = new JLabel(Character.toString(c));
		l.setFont(Css.FONT_LOGO);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setOpaque(true);
		l.setBorder(javax.swing.BorderFactory.createLineBorder(Css.BACKGROUND_SELECTED, 2));
		l.setForeground(Css.COLOR_DEFAULT_FOREGROUND);
		l.setBackground(Css.BACKGROUND_UNSELECTED);
		return l;
	}
	*/
	
}
