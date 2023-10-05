package gyk4j.wols.view.action;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import gyk4j.wols.view.component.main.MainLeftButton;
import gyk4j.wols.view.component.main.MainPanel;

public class MainBarLeftButtonMouseListener implements MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		MainLeftButton source = (MainLeftButton) e.getSource();
		MainPanel.getInstance().showPage(source.getText());
//		MainLeftButton component = (MainLeftButton) e.getComponent();
		
//		MainLeftButtons parent = (MainLeftButtons) source.getParent();
//		parent.setActive(source);
		
//		source.setActive(true);
//		System.out.println(String.format("Source: %s, Component: %s", source.getText(), component.getText()));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("mousePressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
//		System.out.println("mouseReleased");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
//		System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent e) {
//		System.out.println("mouseExited");
	}
}
