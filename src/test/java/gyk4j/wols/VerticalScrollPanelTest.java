package gyk4j.wols;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Test;

import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.VerticalScrollPanel;

public class VerticalScrollPanelTest {
	@Test
	public void testVerticalScrollPanel() {
		final boolean error = false;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
					fail(e.getMessage());
				} finally {
					createAndShowGUI();
				}
            }
        });
		assertFalse(error);
	}
	
	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Test Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final Dimension size = new Dimension(400, 600);
        frame.setPreferredSize(size);
        frame.setMinimumSize(size);
        frame.setMaximumSize(size);
        frame.setLocationRelativeTo(null);
 
        //Create and set up the content pane.
		JComponent[] panes = {

				new TransparentLabel(
						"<html>1. Item A.1 XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX</html>"),
				new TransparentLabel(
						"<html>2. Item A.2 XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX</html>"),
				new TransparentLabel(
						"<html>3. Item A.2 XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX XXXXXXXXXX</html>"),

				new TransparentLabel("<html><font color=red>1. Item A.1 Red</font></html>"), 
				new TransparentLabel("<html><font color=green>2. Item A.2 Green</font></html>"),
				new TransparentLabel("<html><font color=blue>3. Item A.2 Blue</font></html>"),

				new TransparentLabel("<html><b>1. Item B.1 Bold</b></html>"), 
				new TransparentLabel("<html><i>2. Item B.2 Italic</i></html>"),
				new TransparentLabel("<html><u>3. Item B.2 Underline</u></html>"),

				new TransparentLabel("<html><font size=+2>1. Item C.1 +2</font></html>"), 
				new TransparentLabel("<html><font size=+3>2. Item C.2 +3</font></html>"),
				new TransparentLabel("<html><font size=-2>3. Item C.2 -2</font></html>"),

				new TransparentLabel("<html>1. Item C.1 10 Normal</html>"), 
				new TransparentLabel("<html><font size=+1>2. Item C.2 +1</font></html>"),
				new TransparentLabel("<html><font size=+2>3. Item C.2 +2</font></html>"),

				new TransparentLabel("<html>1. Item C.1 Normal</html>"), 
				new TransparentLabel("<html><font size=-1>2. Item C.2 -1</font></html>"),
				new TransparentLabel("<html><font size=-2>3. Item C.2 -2</font></html>"),

				new TransparentLabel("<html>A. Item C.1</html>"), 
				new TransparentLabel("<html>2. Item C.2</html>"),
				new TransparentLabel("<html>3. Item C.2</html>"),

				new TransparentLabel("<html>B. Item C.1</html>"), 
				new TransparentLabel("<html>2. Item C.2</html>"),
				new TransparentLabel("<html>3. Item C.2</html>"),

				new TransparentLabel("<html>C. Item C.1</html>"), 
				new TransparentLabel("<html>2. Item C.2</html>"),
				new TransparentLabel("<html>3. Item C.2</html>"),

				new TransparentLabel("<html>D. Item C.1</html>"), 
				new TransparentLabel("<html>2. Item C.2</html>"),
				new TransparentLabel("<html>3. Item C.2</html>"),

				new TransparentLabel("<html>E. Item A.1</html>"), 
				new TransparentLabel("<html>2. Item A.2</html>"),
				new TransparentLabel("<html>3. Item A.2</html>"),

				new TransparentLabel("<html>F. Item B.1</html>"), 
				new TransparentLabel("<html>2. Item B.2</html>"),
				new TransparentLabel("<html>3. Item B.2</html>"),

				new TransparentLabel("<html>G. Item A.1</html>"), 
				new TransparentLabel("<html>2. Item A.2</html>"),
				new TransparentLabel("<html>3. Item A.2</html>"),

				new TransparentLabel("<html>H. Item B.1</html>"), 
				new TransparentLabel("<html>2. Item B.2</html>"),
				new TransparentLabel("<html>3. Item B.2</html>"),

				new TransparentLabel("<html>I. Item A.1</html>"), 
				new TransparentLabel("<html>2. Item A.2</html>"),
				new TransparentLabel("<html>3. Item A.2</html>"),

				new TransparentLabel("<html>J. Item B.1</html>"), 
				new TransparentLabel("<html>2. Item B.2</html>"),
				new TransparentLabel("<html>3. Item B.2</html>"),

		};

		JComponent newContentPane = new VerticalScrollPanel(frame, panes);
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}
}
