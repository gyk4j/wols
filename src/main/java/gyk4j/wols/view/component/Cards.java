package gyk4j.wols.view.component;

import java.awt.CardLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cards extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Cards.class);
	
	protected CardLayout layout = new CardLayout();
	protected List<String> names = new ArrayList<String>();
	
	protected int card = 0;
	
	public Cards() {
		super();
		setOpaque(false);
		setLayout(layout);
	}

	public void first() {
		setCard(0);
		layout.first(this);
	}
	
	public void last() {
		setCard(this.getComponentCount()-1);
		layout.last(this);
	}
	
	public void previous() {
		if((card - 1) >= 0) {
			setCard(getCard()-1);
			layout.previous(this);
		}
	}
	
	public void next() {
		if((card + 1) < getComponentCount()) {
			setCard(getCard()+1);
			layout.next(this);
		}
	}
	
	public void showCard(int position) {
		if(position >= 0 && position < getNames().size()) {
			setCard(position);
			layout.show(this, getNames().get(position));
		}
	}
	
	public int showCard(String name) {
		int found = -1;
		for(int i=0; i < getNames().size(); i++) {
			
			if(getNames().get(i) == name) {
				found = i;
				setCard(i);
				layout.show(this, name);
			}
		}
		return found;
	}
	
	public Component getCard(int index) {
		return getComponent(index);
	}
	
	public Component getCard(String name) {
		for(int i=0; i < getNames().size(); i++) {
			if(getNames().get(i) == name) {
				return getComponent(i);
			}
		}
		return null;
	}

	public int getCard() {
		return card;
	}

	public void setCard(int card) {
		this.card = card;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	@Override
	public void add(Component comp, Object constraints) {
//		System.out.println("Overriding add: " + constraints);
		if (constraints instanceof String) {
			super.add(comp, constraints);
			getNames().add((String) constraints);
		}
		else {
			LOGGER.warn("Non-string constraint for card layout: {}", constraints.toString());
		}
	}
}
