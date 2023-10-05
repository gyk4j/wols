package gyk4j.wols.view.component.main;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import gyk4j.wols.view.action.MainBarLeftButtonMouseListener;
import gyk4j.wols.view.action.PageAction;
import gyk4j.wols.view.component.Css;

public class MainLeftButton extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private JComponent parent;
	private boolean active;

	public MainLeftButton(JComponent parent, String text, Icon icon) {
		super(text, icon, SwingConstants.LEFT);
		setParent(parent);
//		super();
//		this.setText(text);
//		this.setIcon(icon);
//		this.setHorizontalAlignment(LEFT);
		this.setOpaque(true); // For background color to work.
		this.setBackground(Css.BACKGROUND_UNSELECTED);
		this.setFont(Css.FONT_14PT);
//		this.setContentAreaFilled(false);
		this.getActionMap().put("click", new PageAction());
		this.addMouseListener(new MainBarLeftButtonMouseListener());
		this.setActive(false);
	}
	
	public MainLeftButton(JComponent parent, String text, Icon icon, boolean active) {
		this(parent, text, icon);
		setActive(active);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		this.setBackground((isActive()? Css.BACKGROUND_SELECTED: Css.BACKGROUND_UNSELECTED));
	}

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}
}
