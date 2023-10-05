package net.altkey12.wols.view.component.hotspots;

import javax.swing.JPanel;
import javax.swing.Timer;

import net.altkey12.wols.view.component.Card;
import net.altkey12.wols.view.component.Cards;

public class HotspotsPage extends Cards {

	private static final long serialVersionUID = 1L;
	
	protected final JPanel[] cards = {
			new LoadingMap(),
			new HotspotsMap()
	};
	
	Timer timer;

	public HotspotsPage(String title) {
		super();
		
		for(JPanel page: cards) {
			Card card = new Card(this, page);
			add(card);
		}
	}

	public JPanel[] getCards() {
		return cards;
	}

}
