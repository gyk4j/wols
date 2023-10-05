package net.altkey12.wols.view.component;

import javax.swing.JPanel;

public class TabPage {
	String text;
	JPanel page;
//	Class<? extends JPanel> page;
	
	public TabPage(String text, JPanel page) {
		super();
		this.text = text;
		this.page = page;
	}
	
//	public TabPage(String text, Class<? extends JPanel> page) {
//		super();
//		this.text = text;
//		this.page = page;
//	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
//	public Class<? extends JPanel> getPage() {
//		return page;
//	}
//	public void setPage(Class<? extends JPanel> page) {
//		this.page = page;
//	}

	public JPanel getPage() {
		return page;
	}

	public void setPage(JPanel page) {
		this.page = page;
	}
}
