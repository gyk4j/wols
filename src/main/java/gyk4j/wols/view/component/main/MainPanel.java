package gyk4j.wols.view.component.main;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.view.component.Card;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TabPage;
import gyk4j.wols.view.component.contactus.ContactUsPage;
import gyk4j.wols.view.component.faqs.FaqsPage;
import gyk4j.wols.view.component.hotspots.HotspotsPage;
import gyk4j.wols.view.component.news.NewsPage;
import gyk4j.wols.view.component.privacypolicy.PrivacyPolicyPage;
import gyk4j.wols.view.component.setup.SetupPage;
import gyk4j.wols.view.component.speedtest.SpeedTestPage;
import gyk4j.wols.view.component.termsandconditions.TermsAndConditionsPage;
import gyk4j.wols.view.component.updatecontent.UpdateContentPage;

public class MainPanel extends JPanel {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainPanel.class);

	private static final long serialVersionUID = 1L;
	
	public static final String INITIAL_PAGE = "Setup";
	
	public static final TabPage[] BUTTONS = {
			new TabPage("Hotspots", new HotspotsPage("Hotspots")), 
			new TabPage("Speed Test", new SpeedTestPage("Speed Test")),
			new TabPage("Setup", new SetupPage("Setup")), 
			new TabPage("News", new NewsPage("News")),
			new TabPage("Update Content", new UpdateContentPage("Update Content")), 
			new TabPage("FAQs", new FaqsPage("FAQs")),
			new TabPage("Contact Us", new ContactUsPage("Contact Us")),
			new TabPage("Terms and Conditions", new TermsAndConditionsPage("Terms and Conditions")),
			new TabPage("Privacy Policy", new PrivacyPolicyPage("Privacy Policy")),
//			new TabPage("Hotspots", HotspotsPage.class), 
//			new TabPage("Speed Test", SpeedTestPage.class),
//			new TabPage("Setup", SetupPage.class), 
//			new TabPage("News", NewsPage.class),
//			new TabPage("Update Content", UpdateContentPage.class), 
//			new TabPage("FAQs", FaqsPage.class),
//			new TabPage("Contact Us", ContactUsPage.class),
//			new TabPage("Terms and Conditions", TermsAndConditionsPage.class),
//			new TabPage("Privacy Policy", PrivacyPolicyPage.class),
	};
	
	/*public static final String[] BUTTONS = {
			"Hotspots", 
			"Speed Test",
			"Setup", 
			"News",
			"Update Content", 
			"FAQs",
			"Contact Us",
			"Terms and Conditions",
			"Privacy Policy",
	};*/
	
	private static MainPanel instance = null;
	
	protected MainTop top = new MainTop(this);
	protected MainLeft left = new MainLeft(this);
	protected MainCenter center = new MainCenter(this);

	public static MainPanel getInstance() {
		if(instance == null) {
			instance = new MainPanel();
		}
		return instance;
	}
	
	private MainPanel() {
		super();
		this.setBackground(Css.PAGE_BACKGROUND);
		this.setLayout(getLayoutManager());
		
		this.add(getTop(), BorderLayout.PAGE_START);
		this.add(getLeft(), BorderLayout.LINE_START);
		this.add(getCenter(), BorderLayout.CENTER);
		
		this.showPage(INITIAL_PAGE);
	}
	
	private LayoutManager getLayoutManager() {
//		BoxLayout lm = new BoxLayout(this, BoxLayout.LINE_AXIS);
		BorderLayout lm = new BorderLayout();
		return lm;
	}

	public MainTop getTop() {
		return top;
	}

	public void setTop(MainTop top) {
		this.top = top;
	}

	public MainLeft getLeft() {
		return left;
	}

	public void setLeft(MainLeft left) {
		this.left = left;
	}

	public MainCenter getCenter() {
		return center;
	}

	public void setCenter(MainCenter center) {
		this.center = center;
	}
	
	public void showPage(int position) {
		this.getLeft().getMainLeftButtons().setActive(position);
		this.getCenter().getPages().showCard(position);
	}
	
	public void showPage(String text) {
		this.getLeft().getMainLeftButtons().setActive(text);
		this.getCenter().getPages().showCard(text);
	}
	
	public JPanel getPage(String className) {
		LOGGER.trace("Looking for (118) {}", className);
		JPanel found = null;
		Card[] cards = this.getCenter().getPages().getPages();
		for(Card card: cards) {
			JPanel p = card.getContent();
			LOGGER.trace("MainPanel.getPage (123) : {}", p.getClass().getSimpleName());
			if(p.getClass().getSimpleName().equals(className)) {
				LOGGER.trace("Found! {}", className);
				found = p;
				break;
			}
		}
		return found;
	}
	
	public JComponent getActiveTabPage() {
		int cardIndex = this.getCenter().getPages().getCard();
		Card[] cards = this.getCenter().getPages().getPages();
		return cards[cardIndex].getContent();
	}
}
