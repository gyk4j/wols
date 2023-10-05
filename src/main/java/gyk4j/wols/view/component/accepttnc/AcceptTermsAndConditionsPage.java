package gyk4j.wols.view.component.accepttnc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.AcceptDialogPage;

public class AcceptTermsAndConditionsPage extends AcceptDialogPage implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String HEADER = "Terms And Conditions";
	private static final String BODY = "termsandconditions.html";
	private static final String FOOTER = "Accept & Continue";
	

	public AcceptTermsAndConditionsPage() {
		super(HEADER, BODY, FOOTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Controller controller = Controller.getInstance();
		controller.saveTermsAndConditionsAgree();
		controller.nextScene();
	}
}
