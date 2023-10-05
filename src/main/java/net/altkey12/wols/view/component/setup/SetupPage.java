package net.altkey12.wols.view.component.setup;

import java.awt.BorderLayout;

import net.altkey12.wols.view.component.Card;
import net.altkey12.wols.view.component.Cards;
import net.altkey12.wols.view.component.TitledPage;

public class SetupPage extends TitledPage {

	private static final long serialVersionUID = 1L;
	
	private SetupTop stepper;
	private static Cards wizard;
	private Registration registration;
	private Verification verification;
	private Configuration configuration;

	public SetupPage(String title) {
		super(title);
		this.setLayout(new BorderLayout());
		
		stepper = new SetupTop();
		add(stepper, BorderLayout.NORTH);
		
		wizard = new SetupWizard();
		
		registration = new Registration("Setup");
		wizard.add(new Card(wizard, registration), SetupStage.REGISTRATION.toString());
		
		verification = new Verification("One-time Password", registration.getModel().getMobileNumber());
		wizard.add(new Card(wizard, verification), SetupStage.VERIFICATION.toString());
		
		configuration = new Configuration("Configuration");
		wizard.add(new Card(wizard, configuration), SetupStage.CONFIGURATION.toString());
		
		add(wizard, BorderLayout.CENTER);
	}

	public static Cards getWizard() {
		return wizard;
	}
	
	public void reset() {
		registration.getModel().reset();
		verification.getModel().reset();
		configuration.getModel().reset();
	}
}
