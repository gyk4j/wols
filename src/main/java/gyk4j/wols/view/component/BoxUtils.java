package gyk4j.wols.view.component;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class BoxUtils {
	public enum LineSpacing {
		THIN(8),
		NORMAL(16),
		FAT(32);
		
		public final int height;
		
		private LineSpacing(int height) {
			this.height = height;
		}
		
		public int getHeight() {
			return height;
		}
	}
	
	public static JPanel createFilledBox(Component c) {
		JPanel box = new TransparentPanel();
		box.setLayout(new BoxLayout(box, BoxLayout.LINE_AXIS));
		box.add(c);
		
		return box;
	}
	
	public static Box createLeftBox(Component c) {
		Box box = Box.createHorizontalBox();
		box.add(c);
		box.add(Box.createHorizontalGlue());
		return box;
	}
	
	public static Box createCenteredBox(Component c) {
		Box box = Box.createHorizontalBox();
//		box.setBorder(BorderFactory.createLineBorder(Color.PINK));
		box.add(Box.createHorizontalGlue());
		box.add(c);
		box.add(Box.createHorizontalGlue());
		return box;
	}
	
	public static Box createRightBox(Component c) {
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(c);
		return box;
	}
	
	public static Box createLeftRightBox(Component left, Component right) {
		Box box = Box.createHorizontalBox();
		box.add(left);
		box.add(Box.createHorizontalGlue());
		box.add(right);
		return box;
	}
	
	public static Box createTopBox(Component c) {
		Box box = Box.createVerticalBox();
		box.add(c);
		box.add(Box.createVerticalGlue());
		return box;
	}
	
	public static Box createMiddleBox(Component c) {
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(c);
		box.add(Box.createVerticalGlue());
		return box;
	}
	
	public static Box createBottomBox(Component c) {
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(c);
		return box;
	}
	
	public static Component createLineSpacing(LineSpacing spacing) {
		return Box.createRigidArea(new Dimension(0, spacing.getHeight()));
	}
	
	public static Component createColumnSpacing() {
		return Box.createRigidArea(new Dimension(16, 0));
	}
	
}
