package gyk4j.wols.view.component.acceptprivacypolicy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.AcceptDialogPage;

public class AcceptPrivacyPolicyPage extends AcceptDialogPage implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final String HEADER = "Privacy Policy";
	private static final String BODY = "privacy.html";
	private static final String FOOTER = "Accept & Continue";
	

	public AcceptPrivacyPolicyPage() {
		super(HEADER, BODY, FOOTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Controller controller = Controller.getInstance();
		controller.savePrivacyAgree();
		controller.nextScene();
	}
}
