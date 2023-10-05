package gyk4j.wols.view.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PageAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Clicked!" + e.getActionCommand() + " " + e.getID());
	}

}
