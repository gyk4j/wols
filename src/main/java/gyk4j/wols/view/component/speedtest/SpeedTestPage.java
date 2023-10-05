package gyk4j.wols.view.component.speedtest;

import javax.swing.JPanel;

import gyk4j.wols.view.component.Card;
import gyk4j.wols.view.component.Cards;

public class SpeedTestPage extends Cards {

	private static final long serialVersionUID = 1L;
	
	protected final JPanel[] cards = {
			new SpeedTestFirst(""),
			new RunNetworkDiagnosticsPage("Run Network Diagnostics")
	};

	public SpeedTestPage() {
		super();
	}
	
	public SpeedTestPage(String title) {
		this();
		
		for(JPanel page: cards) {
			Card card = new Card(this, page);
			add(card);
		}
	}

}
