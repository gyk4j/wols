package net.altkey12.wols.view.component;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Card extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	protected JComponent parent;
	protected JPanel content;
	
	public Card(Cards parent, JPanel content) {
		super();
		this.parent = parent;
		this.content = content;
//		this.setName(this.getClass().getName());
		
//		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
//		this.setLayout(layout);
		this.add(content);
		
//		this.addComponentListener(CardComponentListener.getInstance());
	}

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}

	public JPanel getContent() {
		return content;
	}

	public void setContent(JPanel content) {
		this.content = content;
	}
}
