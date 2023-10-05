package net.altkey12.wols.view.component.app;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.main.MainPanel;

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static MainFrame instance = null;
	
	ScenePanel pnlScenes = new ScenePanel();

	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
		}
		return instance;
	}
	
	public static void setWaitCursor(boolean wait) {
		int cursor = (wait)? Cursor.WAIT_CURSOR: Cursor.DEFAULT_CURSOR;
		getInstance().setCursor(Cursor.getPredefinedCursor(cursor));
	}
	
	private MainFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*
		if (!this.isDisplayable()) {
			setUndecorated(true);
			MoveListener listener = new MoveListener();
			this.addMouseListener(listener);
			this.addMouseMotionListener(listener);
		}
		*/

		this.setMinimumSize(Css.WINDOW_SIZE);
		this.setMaximumSize(Css.WINDOW_SIZE);
		this.setPreferredSize(Css.WINDOW_SIZE);
		setTitle(Css.APP_TITLE);
		
		
		// Set window icon.
		ImageIcon icon = ResourceLoader.getImageIcon(Css.ICON);
		Image image = icon.getImage();
		setIconImage(image);
		
		Controller controller = Controller.getInstance();
		boolean termsAndConditions = controller.loadTermsAndConditionsAgree();
		boolean privacy = controller.loadPrivacyAgree();
		
		if(termsAndConditions && privacy) {
			getContentPane().add(MainPanel.getInstance());
		}
		else {
			getContentPane().add(pnlScenes);
		}
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public ScenePanel getScenes() {
		return pnlScenes;
	}

	public void setScenes(ScenePanel scenes) {
		this.pnlScenes = scenes;
	}
	
	public void nextScene() {
		getScenes().next();
	}

	public void closeWindow() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public class MoveListener implements MouseListener, MouseMotionListener {
        
        private Point pressedPoint;
        private Rectangle frameBounds;
        
        @Override
        public void mouseClicked(MouseEvent event) {
        }

        @Override
        public void mousePressed(MouseEvent event) {
            this.frameBounds = MainFrame.this.getBounds();
            this.pressedPoint = event.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {
            moveJFrame(event);
        }

        @Override
        public void mouseEntered(MouseEvent event) {
        }

        @Override
        public void mouseExited(MouseEvent event) {
        }

        @Override
        public void mouseDragged(MouseEvent event) {
            moveJFrame(event);
        }

        @Override
        public void mouseMoved(MouseEvent event) {
        }
        
        private void moveJFrame(MouseEvent event) {
            Point endPoint = event.getPoint();
            
			if (pressedPoint != null) {
				int xDiff = endPoint.x - pressedPoint.x;
				int yDiff = endPoint.y - pressedPoint.y;
				frameBounds.x += xDiff;
				frameBounds.y += yDiff;
				MainFrame.this.setBounds(frameBounds);
			}
        }
        
    }
}
