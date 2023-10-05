package gyk4j.wols.view.component.hotspots;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.GradientButton;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.main.MainPanel;

public class LoadingMap extends TransparentPanel {

	protected static final int LOADING_SPLASH_DELAY = 1000;

	private static final int LIST_ALL_HOTSPOTS_BORDER_WIDTH = 8;

	private static final long serialVersionUID = 1L;
	
	JLabel lblMessage = new TransparentLabel("Please wait while we prepare the hotspots map");
	JProgressBar pgbLoading = new JProgressBar();
	JButton btnListAllHotspots = new GradientButton("List All Hotspots", Color.WHITE, Css.TOP_START, Css.TOP_END);

	Timer timer;
	
	public LoadingMap() {
		super();
		setLayout(new BorderLayout());
		
		pgbLoading.setIndeterminate(true);
		btnListAllHotspots.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				next();
			}
			
		});
		
		this.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				
				Controller.getInstance().loadUpdate();
				
				timer = new Timer(LOADING_SPLASH_DELAY, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (timer.isRunning()) {
							timer.stop();
							timer = null;
						}
						next();
					}
					
				});
				
				timer.start();
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {}

			@Override
			public void ancestorMoved(AncestorEvent event) {}
			
		});
		
		JPanel p = new TransparentPanel();
		p.add(Box.createVerticalGlue());
		p.add(BoxUtils.createCenteredBox(lblMessage));
		p.add(Box.createVerticalStrut(16));
		p.add(BoxUtils.createCenteredBox(pgbLoading));
		p.add(Box.createVerticalGlue());
		
		add(p, BorderLayout.CENTER);

		JPanel ps = new TransparentPanel();
		ps.setLayout(new GridLayout(1,1));
		ps.setBorder(BorderFactory.createEmptyBorder(
				LIST_ALL_HOTSPOTS_BORDER_WIDTH, 
				LIST_ALL_HOTSPOTS_BORDER_WIDTH, 
				LIST_ALL_HOTSPOTS_BORDER_WIDTH, 
				LIST_ALL_HOTSPOTS_BORDER_WIDTH));
		ps.add(btnListAllHotspots);

		add(ps, BorderLayout.SOUTH);
	}
	
	protected void next() {
		JComponent page = MainPanel.getInstance().getActiveTabPage();
		if(page instanceof HotspotsPage) {
			((HotspotsPage) page).next();
		}
	}	
}
