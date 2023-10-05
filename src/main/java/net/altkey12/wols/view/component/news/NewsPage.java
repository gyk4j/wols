package net.altkey12.wols.view.component.news;

import javax.swing.JPanel;

import net.altkey12.wols.view.component.Card;
import net.altkey12.wols.view.component.Cards;
import net.altkey12.wols.view.component.ScrollPage;

public class NewsPage extends ScrollPage {
	
	private static final long serialVersionUID = 1L;
	
	protected static final JPanel[] cards = {
			new FrontPage(),
			new ArticlePage()
	};
	
	protected Cards views = new Cards();

	public NewsPage(String title) {
		super(null);
		
		for(JPanel page: cards) {
			Card card = new Card(views, page);
			views.add(card);
		}
		
		addComponent(views);
	}
	
	public static JPanel[] getCards() {
		return cards;
	}
	
	public static FrontPage getFrontPage() {
		FrontPage found = null;
		for(int i=0; i < cards.length; i++) {
			if(cards[i] instanceof FrontPage) {
				found = (FrontPage) cards[i];
				break;
			}
		}
		return found;
	}
	
	public static ArticlePage getArticlePage() {
		ArticlePage found = null;
		for(int i=0; i < cards.length; i++) {
			if(cards[i] instanceof ArticlePage) {
				found = (ArticlePage) cards[i];
				break;
			}
		}
		return found;
	}

	public Cards getViews() {
		return views;
	}
}
