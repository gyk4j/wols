package net.altkey12.wols.view.component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TitledPage extends TransparentPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected JLabel lblTitle = new JLabel();

	public TitledPage(String title) {
		super();
		
		if (title != null) {
			this.setBorder(Css.STEPPER_BORDER);
			
			TransparentPanel titleRow = new TransparentPanel();
			titleRow.setLayout(new BoxLayout(titleRow, BoxLayout.LINE_AXIS));
			titleRow.add(lblTitle);
			titleRow.add(Box.createHorizontalGlue());

			lblTitle.setFont(Css.FONT_PAGE);
			lblTitle.setForeground(Css.COLOR_PAGE);
			lblTitle.setHorizontalAlignment(SwingConstants.LEFT);
			// lblTitle.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

			setTitle(title);

			add(titleRow);
		}
	}

	public String getTitle() {
		return this.lblTitle.getText();
	}

	public void setTitle(String title) {
		this.lblTitle.setText(title);
	}

}
