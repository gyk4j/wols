package gyk4j.wols.view.component.setup;

import javax.swing.BoxLayout;
import javax.swing.JSeparator;

import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TransparentPanel;

public class SetupTop extends TransparentPanel {

	private static final long serialVersionUID = 1L;

	private SetupTopStepper stepper;
	private JSeparator separator = new JSeparator();
	
	public SetupTop() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		stepper = SetupTopStepper.getInstance();
		add(stepper);
		separator.setBorder(Css.STEPPER_BORDER);
		
		add(separator);
	}

	public SetupTopStepper getStepper() {
		return stepper;
	}

	public void setStepper(SetupTopStepper stepper) {
		this.stepper = stepper;
	}
}
