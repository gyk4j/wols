package gyk4j.wols.view.component;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TransparentLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	public TransparentLabel() {
		super();
		initialize();
	}

	public TransparentLabel(String text) {
		super(text);
		initialize();
	}
	
	public TransparentLabel(String text, Color color) {
		super(text);
		initialize();
		setForeground(color);
	}

	public TransparentLabel(Icon image) {
		super(image);
		initialize();
	}

	public TransparentLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		initialize();
	}

	public TransparentLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		initialize();
	}

	public TransparentLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		initialize();
	}

	protected void initialize() {
		this.setFont(Css.FONT_14PT);
		this.setOpaque(false);
		this.setHorizontalAlignment(SwingConstants.LEFT);
//		this.setBorder(Css.DEBUG_BORDER_MAGENTA);
	}
}
