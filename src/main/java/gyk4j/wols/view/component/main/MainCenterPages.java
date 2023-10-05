package gyk4j.wols.view.component.main;

import javax.swing.JPanel;

import gyk4j.wols.view.component.Card;
import gyk4j.wols.view.component.Cards;

public class MainCenterPages extends Cards {
	
	private static final long serialVersionUID = 1L;
	
	protected Card[] pages;
	/*= { 
			new HotspotsPage(this), 
			new SpeedTestPage(this), 
			new SetupPage(this), 
			new NewsPage(this),
			new UpdateContentPage(this), 
			new FaqsPage(this), 
			new ContactUsPage(this), 
			new TermsAndConditionsPage(this),
			new PrivacyPolicyPage(this),
	};*/

	public MainCenterPages() {
		super();
		
		try {
			pages = new Card[MainPanel.BUTTONS.length];

			for (int i = 0; i < MainPanel.BUTTONS.length; i++) {
//				Class<? extends JPanel> clazz = MainPanel.BUTTONS[i].getPage();
//				Constructor<? extends JPanel> constructor = clazz.getDeclaredConstructor(String.class);
//				String text = MainPanel.BUTTONS[i].getText();
//				JPanel p = constructor.newInstance(text);
				JPanel p = MainPanel.BUTTONS[i].getPage();
				pages[i] = new Card(this, p);
				add(pages[i], MainPanel.BUTTONS[i].getText()); // pages[i].getName()
			}
		} 
//		catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} 
		catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public Card[] getPages() {
		return pages;
	}

	public void setPages(Card[] pages) {
		this.pages = pages;
	}
}
