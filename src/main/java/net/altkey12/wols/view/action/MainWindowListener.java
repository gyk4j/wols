package net.altkey12.wols.view.action;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindowListener implements WindowListener {

	@Override
	public void windowOpened(WindowEvent e) {
		System.out.println("MainWindowListener.Opened");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("MainWindowListener.Closing");
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("MainWindowListener.Closed");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("MainWindowListener.Iconified");
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("MainWindowListener.Deiconified");
	}

	@Override
	public void windowActivated(WindowEvent e) {
		System.out.println("MainWindowListener.Activated");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		System.out.println("MainWindowListener.Deactivated");
	}

}
