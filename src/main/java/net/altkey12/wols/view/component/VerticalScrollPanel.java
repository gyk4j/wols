package net.altkey12.wols.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VerticalScrollPanel extends TransparentPanel {
	
	private static final long serialVersionUID = 1L;
	
	JPanel lstItems = new TransparentPanel();
	JScrollPane scpPane = new JScrollPane(lstItems);
	
	public VerticalScrollPanel(Component parent, Component[] components) {
		super();
		
		setLayout(new BorderLayout());
		
		lstItems.setOpaque(true);
		lstItems.setBackground(Color.WHITE);
		
		add(scpPane, BorderLayout.CENTER);
		scpPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scpPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		int width = 0, height = 0;
		
		width = parent.getPreferredSize().width;
		System.out.println(width);
		for(Component c: components) {
			JPanel p = new TransparentPanel();
			p.add(BoxUtils.createLeftBox(c));
			Component l = c;
			
			height += l.getPreferredSize().height * (Math.ceil((double) l.getPreferredSize().width / (double) width));
			lstItems.add(p);
		}
		
		Dimension size = new Dimension(width, height);
		lstItems.setPreferredSize(size);
		lstItems.setMaximumSize(size);
		lstItems.setMinimumSize(size);
	}
}
