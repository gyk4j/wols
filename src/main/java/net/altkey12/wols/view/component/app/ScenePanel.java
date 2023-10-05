package net.altkey12.wols.view.component.app;

import javax.swing.JPanel;

import net.altkey12.wols.view.component.Card;
import net.altkey12.wols.view.component.Cards;
import net.altkey12.wols.view.component.acceptprivacypolicy.AcceptPrivacyPolicyPage;
import net.altkey12.wols.view.component.accepttnc.AcceptTermsAndConditionsPage;
import net.altkey12.wols.view.component.main.MainPanel;
import net.altkey12.wols.view.component.welcome.WelcomePage;

public class ScenePanel extends Cards {

	private static final long serialVersionUID = 1L;
	
	public static final JPanel[] SCENES = {
			new WelcomePage(),
			new AcceptTermsAndConditionsPage(),
			new AcceptPrivacyPolicyPage(),
			MainPanel.getInstance(),
	};
	
	public Card[] dialogs = new Card[SCENES.length];
	
	public ScenePanel() {
		super();
		
		for(int i=0; i < SCENES.length; i++) {
			dialogs[i] = new Card(this, SCENES[i]);
			String key = SCENES[i].getClass().getSimpleName();
			add(dialogs[i], key);
		}
	}
	
}
