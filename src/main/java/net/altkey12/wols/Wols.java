// WOLS : Registers for a new Wireless@SG SSA account
//
// Java equivalent of the Wireless@SG app available at:
//    https://www2.imda.gov.sg/programme-listing/wireless-at-sg/Wireless-at-SG-for-Consumers
//

package net.altkey12.wols;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import net.altkey12.wols.controller.AbstractController;
import net.altkey12.wols.controller.CliController;
import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.view.CliView;
import net.altkey12.wols.view.GuiView;
import net.altkey12.wols.view.IUserInterface;

public final class Wols {
	
	public static final String VERSION = "1.0.0.0000";
	public static final String AUTHOR = "gyk4j";
	
	public static final String SSID_TEST = "test-Wireless@SGx";
	public static final String SSID_PROD = "Wireless@SGx";
	// TODO: Need to update SSID to either TEST or PROD
	public static final String SSID = SSID_TEST;
	
	private static Wols INSTANCE;

	private AbstractController controller;
	private IUserInterface ui;
	
	public static Wols getInstance(String[] args) {
		if(INSTANCE == null) {
			
			IUserInterface ui = (args.length == 0)? 
					new GuiView(): new CliView();
					
			AbstractController controller = (args.length == 0)? 
					Controller.getInstance(): CliController.getInstance();
					
			INSTANCE = new Wols(ui, controller, args);
		}
		return INSTANCE;
	}
	
	public static Wols getInstance() {
		return INSTANCE;
	}
	
	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		Wols app = Wols.getInstance(args);
		app.getUi().start();
	}

	private Wols(IUserInterface ui, AbstractController controller, String[] args) {
		super();
		this.controller = controller;
		this.controller.setArguments(args);
		this.ui = ui;
	}

	public AbstractController getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public IUserInterface getUi() {
		return ui;
	}

	public void setUi(IUserInterface ui) {
		this.ui = ui;
	}	
}
