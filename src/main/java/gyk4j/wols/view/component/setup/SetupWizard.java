package gyk4j.wols.view.component.setup;

import gyk4j.wols.view.component.Cards;

public class SetupWizard extends Cards {

	private static final long serialVersionUID = 1L;

	public SetupWizard() {
		super();
	}

	@Override
	public void showCard(int position) {
		super.showCard(position);
		SetupTopStepper.getInstance().setStep(position);	
	}

	@Override
	public int showCard(String name) {
		int i = super.showCard(name);
		SetupTopStepper.getInstance().setStep(i);
		return i;
	}

}
