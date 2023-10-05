package net.altkey12.wols.view.component;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class CardComponentListener implements ComponentListener {
	
	private static CardComponentListener instance;
	
	public static ComponentListener getInstance() {
		if(instance == null) {
			instance = new CardComponentListener();
		}
		return instance;
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		System.out.println("Showing: " + e.getSource());
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		System.out.println("Hiding: " + e.getSource());
	}
}
