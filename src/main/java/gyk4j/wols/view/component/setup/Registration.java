package gyk4j.wols.view.component.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.MaskFormatter;

import gyk4j.wols.beans.setup.RegistrationResult;
import gyk4j.wols.controller.Controller;
import gyk4j.wols.model.setup.SetupRegistrationModel;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.FlatButton;
import gyk4j.wols.view.component.Highlighter;
import gyk4j.wols.view.component.TitledPage;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.app.MainFrame;

public class Registration extends TitledPage {

	private static final long serialVersionUID = 1L;
	
	protected final SetupRegistrationModel model = new SetupRegistrationModel();

	JLabel lblProvider = new TransparentLabel("Your preferred Wireless@SG provider:");
	TransparentPanel pnlProvider = new Providers(model);

	JLabel lblDob = new TransparentLabel("Your Date of Birth:");
	//	JDateChooser dcpDob = new JDateChooser("dd/MM/yyyy", "##/##/####", '_');
	JPanel pnlDob = new DateOfBirth(model);

	JLabel lblMobileNumber = new TransparentLabel("Your Mobile Number:");
	JComboBox<String> cbxCountry = new JComboBox<>();
	JTextField txtMobileNumber = createPhoneField();

	JLabel lblConfirmMobileNumber = new TransparentLabel("Confirm Mobile Number:");
	JTextField txtConfirmCountry = new JTextField();
	JTextField txtConfirmMobileNumber = createPhoneField();

	JLabel lblTermsAndConditions = new TransparentLabel("Terms and Conditions");
	TransparentPanel pnlTermsAndConditions = new TermsAndConditionsAgreement(model);

	FlatButton btnNext = new FlatButton("Next", Color.WHITE, Css.BUTTON_NEXT);

	public Registration(String title) {
		super(title);

		TransparentPanel providerRow = new TransparentPanel();
		GridLayout providerLayout = new GridLayout(1, 2);
		providerLayout.setHgap(8);
		providerLayout.setVgap(8);
		providerRow.setLayout(providerLayout);
		providerRow.add(lblProvider);
		providerRow.add(pnlProvider);

		TransparentPanel otherRows = new TransparentPanel();
		GridLayout layout = new GridLayout(6, 2);
		layout.setHgap(8);
		layout.setVgap(8);
		otherRows.setLayout(layout);

		// Date of Birth
		otherRows.add(lblDob);
		otherRows.add(pnlDob);
//		otherRows.add(dcpDob);
//		JTextFieldDateEditor editor = (JTextFieldDateEditor) dcpDob.getDateEditor();
//		editor.setDocument(model.getDob());

		otherRows.add(lblMobileNumber);
		otherRows.add(new JLabel());
		otherRows.add(cbxCountry);
		cbxCountry.setModel(model.getMobileNumberPrefix());
		cbxCountry.setEnabled(false);
		cbxCountry.setFont(Css.FONT_14PT);
		otherRows.add(txtMobileNumber);
		txtMobileNumber.setDocument(model.getMobileNumber());
		txtMobileNumber.setFont(Css.FONT_14PT);
		txtMobileNumber.setToolTipText("A valid 8-digit mobile phone number e.g. 80000000 - 99999999");
		
		otherRows.add(lblConfirmMobileNumber);
		otherRows.add(new JLabel());
		otherRows.add(txtConfirmCountry);
		txtConfirmCountry.setDocument(model.getConfirmMobileNumberPrefix());
		txtConfirmCountry.setEnabled(false);
		txtConfirmCountry.setFont(Css.FONT_14PT);
		otherRows.add(txtConfirmMobileNumber);
		txtConfirmMobileNumber.setDocument(model.getConfirmMobileNumber());
		txtConfirmMobileNumber.setFont(Css.FONT_14PT);
		txtConfirmMobileNumber.setToolTipText("A valid 8-digit mobile phone number e.g. 80000000 - 99999999");
		
		TransparentPanel termsAndConditionsRow = new TransparentPanel();
		Box tncHeaderBox = Box.createHorizontalBox();
		tncHeaderBox.add(lblTermsAndConditions);
		tncHeaderBox.add(Box.createHorizontalGlue());
		termsAndConditionsRow.add(tncHeaderBox);
		Box agreeBox = Box.createHorizontalBox();
		agreeBox.add(pnlTermsAndConditions);
		agreeBox.add(Box.createHorizontalGlue());
		termsAndConditionsRow.add(agreeBox);

		TransparentPanel actionsRow = new TransparentPanel();
		Box actionsBox = Box.createHorizontalBox();
		actionsBox.add(btnNext);
		actionsBox.add(Box.createHorizontalGlue());
		actionsRow.add(actionsBox);

//		Date min = Date.from(LocalDate.now().minusYears(100).atStartOfDay(ZoneId.systemDefault()).toInstant());
//		Date max = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
//		Date initial = Date.from(LocalDate.now().minusYears(40).atStartOfDay(ZoneId.systemDefault()).toInstant());
//		dcpDob.setSelectableDateRange(min, max);
//		dcpDob.setDate(initial);

		loadCountryCodes();
		cbxCountry.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String country = (String) cbxCountry.getSelectedItem();
				txtConfirmCountry.setText(country);
			}

		});
		txtConfirmCountry.setEditable(false);
		txtConfirmCountry.setText(cbxCountry.getSelectedItem().toString());
		txtConfirmCountry.setFocusable(false);
		

		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				RegistrationResult r = model.checkRegistration();
				
				if(r.isOK()) {
					Highlighter.unhighlight(pnlProvider);
					Highlighter.unhighlight(pnlDob);
					Highlighter.unhighlight(cbxCountry);
					Highlighter.unhighlight(txtMobileNumber);
					Highlighter.unhighlight(txtConfirmMobileNumber);
					Highlighter.unhighlight(pnlTermsAndConditions);
					
					new RegistrationWorker().execute();
				}
				else {
					Highlighter.highlight(pnlProvider, r.isProvider());
					Highlighter.highlight(pnlDob, r.isDobDay());
					Highlighter.highlight(cbxCountry, r.isCountry());
					Highlighter.highlight(txtMobileNumber, r.isMobilePhone());
					Highlighter.highlight(txtConfirmMobileNumber, r.isConfirmPhone());
					Highlighter.highlight(pnlTermsAndConditions, r.isAgree());
				}
			}

		});

		add(providerRow);
		add(otherRows);
		add(termsAndConditionsRow);
		add(Box.createRigidArea(new Dimension(1, 32)));
		add(actionsRow);
	}

	private void loadCountryCodes() {
		String[] codes = Controller.getInstance().loadCountryCodes();
		for(String code: codes) {
			cbxCountry.addItem(code);
		}
		cbxCountry.setSelectedItem("(+65) Singapore");
	}

	private JTextField createPhoneField() {
		JTextField tf = null;

		try {
			MaskFormatter maskFormatter = new MaskFormatter("########");
//			maskFormatter.setPlaceholderCharacter('.');
			tf = new JFormattedTextField(maskFormatter);
		} catch (ParseException e1) {
			tf = new JTextField(8);
		}
		
		tf.setFont(Css.FONT_14PT);
		return tf;
	}

	public SetupRegistrationModel getModel() {
		return model;
	}
	
	class RegistrationWorker extends SwingWorker<String, Void> {

		RegistrationWorker() {
			MainFrame.setWaitCursor(true);
			btnNext.setEnabled(false);
		}
		
		@Override
		protected String doInBackground() throws Exception {
			Controller controller = Controller.getInstance();
			return controller.register(model.createRequest());
		}
		
		@Override
		protected void done() {
			String successCode = null;
			try {
				successCode = get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			MainFrame.setWaitCursor(false);
			btnNext.setEnabled(true);

			if(successCode != null && successCode.length() > 0)
				SetupPage.getWizard().showCard(SetupStage.VERIFICATION.toString());
			else
				JOptionPane.showMessageDialog(
						MainFrame.getInstance(), 
						"Wi-Fi provider's registration service is not available.", 
						"Registration Error", 
						JOptionPane.ERROR_MESSAGE);
		}
	}
}
