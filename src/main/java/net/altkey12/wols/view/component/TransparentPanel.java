package net.altkey12.wols.view.component;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class TransparentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public TransparentPanel() {
		super();
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
//		setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.MAGENTA));
	}

}
