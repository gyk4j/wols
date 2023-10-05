package net.altkey12.wols.view.component;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class Highlighter {
	/**
	 * 
	 * @param c 
	 * @param state true = Valid OK, false = Invalid
	 */
	public static void highlight(Component c, boolean state) {
		Color fg = (state)? Css.COLOR_PASS_FOREGROUND: Css.COLOR_FAIL_FOREGROUND;
		Color bg = (state)? Css.COLOR_PASS_BACKGROUND: Css.COLOR_FAIL_BACKGROUND;
		
		if (c instanceof JPanel) {
			JPanel p = (JPanel) c;
			Component[] children = p.getComponents();
			for (Component child : children) {
				highlight(child, state);
			}
		} else if (c instanceof JSpinner) {
			JSpinner s = (JSpinner) c;
			JComponent e = s.getEditor();
			highlight(e, state);
		} else if (c instanceof JComponent && !(c instanceof JLabel)) {
			JComponent comp = (JComponent) c;
			comp.setOpaque(true);
			c.setForeground(fg);
			c.setBackground(bg);
		}
	}
	
	public static void unhighlight(Component c) {
		final Color fg = Css.COLOR_DEFAULT_FOREGROUND;
		final Color bg = Css.COLOR_DEFAULT_BACKGROUND;
		
		if (c instanceof JPanel) {
			JPanel p = (JPanel) c;
			Component[] children = p.getComponents();
			for (Component child : children) {
				unhighlight(child);
			}
		} else if (c instanceof JSpinner) {
			JSpinner s = (JSpinner) c;
			JComponent e = s.getEditor();
			unhighlight(e);
		} else if (c instanceof JComponent && !(c instanceof JLabel)){
			JComponent comp = (JComponent) c;
			comp.setOpaque(true);
			c.setForeground(fg);
			c.setBackground(bg);
		}
	}
}
