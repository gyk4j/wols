package gyk4j.wols.view.component.main;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class MainRight extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	JComponent parent;

	public MainRight(JComponent parent) {
		super();
		setParent(parent);
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(new JButton("Info A"));
		this.add(new JButton("Info B"));
		this.add(new JButton("Info C"));
		this.add(new JButton("Info D"));
		this.add(new JButton("Info E"));
	}

	public JComponent getParent() {
		return parent;
	}

	public void setParent(JComponent parent) {
		this.parent = parent;
	}

}
