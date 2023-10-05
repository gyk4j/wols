package net.altkey12.wols.view.component.main;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import net.altkey12.wols.view.component.Css;

public class MainLeft extends JPanel {

	private static final long serialVersionUID = 1L;
	
	JComponent parent;
	protected MainLeftButtons mainLeftButtons = new MainLeftButtons();
	protected MainLeftConfigInfo mainLeftConfigInfo = new MainLeftConfigInfo();
	
	public MainLeft(JComponent parent) {
		super();
		setParent(parent);
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setOpaque(true);
		this.setBackground(Css.BACKGROUND_UNSELECTED);
		
		add(getMainLeftButtons());
//		add(Box.createVerticalGlue());
		JSeparator separator = new JSeparator();
		separator.setOpaque(true);
		separator.setBackground(Css.BACKGROUND_UNSELECTED);
		separator.setBorder(BorderFactory.createEmptyBorder());
		add(separator);
//		add(Box.createVerticalGlue());
		add(getMainLeftConfigInfo());
	}

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}

	public MainLeftButtons getMainLeftButtons() {
		return mainLeftButtons;
	}

	public void setMainLeftButtons(MainLeftButtons mainLeftButtons) {
		this.mainLeftButtons = mainLeftButtons;
	}

	public MainLeftConfigInfo getMainLeftConfigInfo() {
		return mainLeftConfigInfo;
	}

	public void setMainLeftConfigInfo(MainLeftConfigInfo mainLeftConfigInfo) {
		this.mainLeftConfigInfo = mainLeftConfigInfo;
	}
}
