package gyk4j.wols.view.component.setup;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import gyk4j.wols.io.HtmlEncoder;
import gyk4j.wols.model.setup.SetupRegistrationModel;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.main.MainPanel;

public class TermsAndConditionsAgreement extends TransparentPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	JCheckBox ckbAgree = new JCheckBox("I agree to the");
	JLabel lblTermsAndConditions = new JLabel();
	JLabel lblPeriod = new JLabel(" .");
	
	final SetupRegistrationModel model;

	public TermsAndConditionsAgreement(SetupRegistrationModel model) {
		super();
		this.model = model;
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		ckbAgree.setOpaque(false);
		ckbAgree.setFont(Css.FONT_14PT);
		ckbAgree.setModel(model.getTermsAndConditions());

		lblTermsAndConditions = new JLabel(HtmlEncoder.createLinkText("terms and conditions"));
		lblTermsAndConditions.setFont(Css.FONT_14PT);
		lblTermsAndConditions.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTermsAndConditions.addMouseListener(this);
		
		lblPeriod.setFont(Css.FONT_14PT);
		
		add(ckbAgree);
		add(lblTermsAndConditions);
		add(lblPeriod);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MainPanel.getInstance().showPage("Terms and Conditions");
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
