package gyk4j.wols.view;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.app.MainFrame;

public class GuiView implements IUserInterface {
	public static final Logger LOGGER = LoggerFactory.getLogger(GuiView.class);
	
	protected MainFrame frame;

	@Override
	public void start() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
	
	private void createAndShowGUI() {
		try {
			String osname = System.getProperty("os.name");
			if(osname != null) {
				osname = osname.toLowerCase();
			
				if(osname.indexOf("windows") != -1) {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				else if(osname.indexOf("linux") != -1) {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
				}
			}
		} catch (UnsupportedLookAndFeelException e) {
			LOGGER.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			LOGGER.error(e.getMessage());
		} catch (InstantiationException e) {
			LOGGER.error(e.getMessage());
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage());
		}
		
		frame = MainFrame.getInstance();
		frame.setVisible(true);
		
		Controller.getInstance().updateOnLaunch();
	}

	@Override
	public void stop() {
		LOGGER.info("Stopping...");
	}
}
