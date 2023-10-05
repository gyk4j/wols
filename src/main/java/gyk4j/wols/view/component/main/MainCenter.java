package gyk4j.wols.view.component.main;

import javax.swing.JComponent;

import gyk4j.wols.view.component.TransparentPanel;

public class MainCenter extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	JComponent parent;
	
	private MainCenterPages pages;

	public MainCenter(JComponent parent) {
		super();
		setParent(parent);
		pages = new MainCenterPages();
		add(pages);
	}

	public MainCenterPages getPages() {
		return pages;
	}

	public void setPages(MainCenterPages pages) {
		this.pages = pages;
	}

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}
}
