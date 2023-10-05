package gyk4j.wols.view.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class OutlineButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public static int BORDER_THICKNESS = 1;
	public static int BORDER_MARGIN_VERTICAL = 4;
	public static int BORDER_MARGIN_HORIZONTAL = 4;

	public OutlineButton(String text, Color color, boolean drawBorder) {
		super(text);
		
		setContentAreaFilled(false);
        setFocusPainted(false);
		
		if(color != null) {
			setForeground(color);
		}
		
		Border border;
		if (drawBorder) {
			Border margin = BorderFactory.createEmptyBorder(
					BORDER_MARGIN_VERTICAL, BORDER_MARGIN_HORIZONTAL,
					BORDER_MARGIN_VERTICAL, BORDER_MARGIN_HORIZONTAL);
			Border outline = BorderFactory.createLineBorder(color, BORDER_THICKNESS);

			border = BorderFactory.createCompoundBorder(outline, margin);
			
		}
		else {
			border = BorderFactory.createEmptyBorder();
		}
		setBorder(border);
		
		setOpaque(false);
		setFont(Css.FONT_14PT.deriveFont(Font.BOLD));
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
