package net.altkey12.wols.view.component.main;

import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JPanel;

import net.altkey12.wols.io.ResourceLoader;

public class MainLeftButtons extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private MainLeftButton[] buttons;	

	public MainLeftButtons() {
		this.setLayout(new GridLayout(MainPanel.BUTTONS.length, 1));
		setButtons(new MainLeftButton[MainPanel.BUTTONS.length]);
		for(int i = 0; i < MainPanel.BUTTONS.length; i++) {
			String text = MainPanel.BUTTONS[i].getText();
			String iconFileName = getIconName(text);
			Icon icon = ResourceLoader.getImageIcon(iconFileName);
			getButtons()[i] = new MainLeftButton(this, text, icon);
			this.add(getButtons()[i]);
		}
	}
	
	private String getIconName(String s) {
		return s.trim().toLowerCase().replace(' ', '-').concat(".png");
//		return "wifi.png";
	}
	
	public MainLeftButton[] getButtons() {
		return this.buttons;
	}
	
	public void setButtons(MainLeftButton[] buttons) {
		this.buttons = buttons;
	}
	
	public void setActive(int position) {
		for (int i = 0; i < getButtons().length; i++) {
			getButtons()[i].setActive(i==position);
		}
	}
	
	public void setActive(String text) {
		for(MainLeftButton b: getButtons()) {
			if(b.getText() == text) {
				b.setActive(true);
			}
			else {
				b.setActive(false);
			}
		}
	}

}
