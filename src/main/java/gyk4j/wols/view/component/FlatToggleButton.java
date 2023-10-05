package gyk4j.wols.view.component;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class FlatToggleButton extends JToggleButton {
	
	private static final long serialVersionUID = 1L;
	public static final Border BORDER_ON = BorderFactory.createLineBorder(Css.LINK_TEXT, 4);
	public static final Border BORDER_OFF = BorderFactory.createLineBorder(Css.PAGE_BACKGROUND, 4);

	public FlatToggleButton() {
		super();
		setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(BORDER_OFF);
        setFont(Css.FONT_14PT);
        
		ChangeListener changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				AbstractButton abstractButton = (AbstractButton) changeEvent.getSource();
				ButtonModel buttonModel = abstractButton.getModel();
				boolean selected = buttonModel.isSelected();
				Border border = (selected)? BORDER_ON: BORDER_OFF;
				setBorder(border);
			}
		};
		this.addChangeListener(changeListener);
	}
	
	public FlatToggleButton(Icon icon) {
		this();
		setIcon(icon);
	}
}
