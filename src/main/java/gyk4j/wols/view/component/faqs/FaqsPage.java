package gyk4j.wols.view.component.faqs;

import gyk4j.wols.view.component.Card;
import gyk4j.wols.view.component.Cards;
import gyk4j.wols.view.component.TitledPage;

public class FaqsPage extends TitledPage {

	private static final long serialVersionUID = 1L;
	
	public static enum Page {
		SECTION,
		QNA
	}
	
	private static final Cards cards = new Cards();
	private static FaqsSectionsPage section;
	private static FaqsQnAPage question;
	
	public FaqsPage(String title) {
		super(null);
		
		section = new FaqsSectionsPage(Page.SECTION.toString());
		question = new FaqsQnAPage(Page.QNA.toString());
		
		cards.add(new Card(cards, section), Page.SECTION.toString());
		cards.add(new Card(cards, question), Page.QNA.toString());
		
		add(cards);
	}
	
	public static Cards getCards() {
		return cards;
	}

	public static FaqsSectionsPage getSection() {
		return section;
	}

	public static FaqsQnAPage getQuestion() {
		return question;
	}
}
