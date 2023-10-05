package net.altkey12.wols.view.component.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.altkey12.wols.beans.setup.VerificationResult;
import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.model.setup.SetupVerificationModel;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.FlatButton;
import net.altkey12.wols.view.component.Highlighter;
import net.altkey12.wols.view.component.TitledPage;
import net.altkey12.wols.view.component.TransparentLabel;
import net.altkey12.wols.view.component.TransparentPanel;
import net.altkey12.wols.view.component.app.MainFrame;

public class Verification extends TitledPage {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Verification.class);

	public static final int OTP_COUNTDOWN = 60;

	private static final long serialVersionUID = 1L;
	
	JLabel lblInformation = new TransparentLabel("A verification SMS has been sent to your mobile at ");
	JLabel lblMobilePhone = new TransparentLabel();
	JLabel lblInstruction = new TransparentLabel("Enter the OTP in the SMS below to continue.");
	JTextField txtPin;
	JLabel lblCountdown = new JLabel("0");
	
	FlatButton btnNext = new FlatButton("Next", Color.WHITE, Css.BUTTON_NEXT);
	
	private int timeCountdown;
	
	private Timer timer;
	
	final SetupVerificationModel model;
	
	public Verification(String title, Document mobileNumber) {
		super(title);
		
		model = new SetupVerificationModel(mobileNumber);
		
		TransparentPanel instructionRow = new TransparentPanel();
		instructionRow.setLayout(new BoxLayout(instructionRow, BoxLayout.PAGE_AXIS));
		Box informationBox	= Box.createHorizontalBox();
		informationBox.add(lblInformation);
		informationBox.add(lblMobilePhone);
		informationBox.add(Box.createHorizontalGlue());
		instructionRow.add(informationBox);
		
		Box instructionBox	= Box.createHorizontalBox();
		instructionBox.add(lblInstruction);
		instructionBox.add(Box.createHorizontalGlue());
		instructionRow.add(instructionBox);
		
		TransparentPanel formRow = new TransparentPanel();
		formRow.setLayout(new BoxLayout(formRow, BoxLayout.LINE_AXIS));
		
		try {
			MaskFormatter mask = new MaskFormatter("######");
			mask.setPlaceholderCharacter('.');
			txtPin = new JFormattedTextField(mask);
			txtPin.setDocument(model.getPin());
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		txtPin.setPreferredSize(new Dimension(100, 32));
		txtPin.setMinimumSize(new Dimension(100, 32));
		txtPin.setMaximumSize(new Dimension(100, 32));
		txtPin.setHorizontalAlignment(SwingConstants.CENTER);
//		txtPin.setBackground(Color.PINK);
		txtPin.setFont(Css.FONT_PIN);
		
		lblCountdown.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountdown.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//		lblCountdown.setBackground(Color.CYAN);
//		lblCountdown.setOpaque(true);
		lblCountdown.setPreferredSize(new Dimension(50, 32));
		lblCountdown.setMaximumSize(new Dimension(50, 32));
		lblCountdown.setFont(Css.FONT_COUNTDOWN);
		
		formRow.add(txtPin);
		formRow.add(Box.createRigidArea(new Dimension(16, 0)));
		formRow.add(lblCountdown);
		formRow.add(Box.createHorizontalGlue());
		
		TransparentPanel actionsRow = new TransparentPanel();
//		actionsRow.setBackground(Css.PAGE_BACKGROUND);
		actionsRow.setLayout(new BoxLayout(actionsRow, BoxLayout.LINE_AXIS));
		actionsRow.add(btnNext);
		actionsRow.add(Box.createHorizontalGlue());
//		actionsRow.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		
		
		btnNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
//				if(timer != null)
//					stopTimer();
//				else
//					startTimer();
				
				VerificationResult vr = model.checkVerification();
				
				if(vr.isOK()) {
					Highlighter.unhighlight(txtPin);
					new VerificationWorker().execute();
				}
				else {
					Highlighter.highlight(txtPin, vr.isPin());
				}
				
			}
			
		});
		
		this.setTimeCountdown(OTP_COUNTDOWN);		
		
		this.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				lblMobilePhone.setText(model.getMobilePhone());
				startTimer();
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				stopTimer();
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
//				System.out.println("ancestorMoved");
				
			}
			
		});
		
		add(Box.createRigidArea(new Dimension(600, 16)));
		add(instructionRow);
		add(Box.createRigidArea(new Dimension(600, 16)));
		add(formRow);
		add(Box.createRigidArea(new Dimension(600, 16)));
		add(actionsRow);
		add(Box.createVerticalGlue());
	}
	
	public SetupVerificationModel getModel() {
		return model;
	}
	
	class CountdownTask extends TimerTask {

		@Override
		public void run() {
			countDown();
			lblCountdown.setText(Integer.toString(getTimeCountdown()));
			
//			switch (getTimeCountdown()) {
//			case 20:
//				SetupTopStepper.getInstance().setStep(1);
//				break;
//			case 10:
//				SetupTopStepper.getInstance().setStep(2);
//				break;
//			case 0:
//				SetupTopStepper.getInstance().setStep(0);
//				break;
//			default:
//				break;
//			}
		}
		
	}
	
	protected void startTimer() {
		if (timer == null) {
			timer = new Timer();
			setTimeCountdown(OTP_COUNTDOWN);
			txtPin.setEditable(true);
			timer.scheduleAtFixedRate(new CountdownTask(), 0, 1000);
		}
		txtPin.requestFocusInWindow();
	}
	
	protected void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		btnNext.requestFocusInWindow();
	}

	public int getTimeCountdown() {
		return timeCountdown;
	}

	public void setTimeCountdown(int timeCountdown) {
		this.timeCountdown = timeCountdown;
	}
	
	private void countDown() {
		int t = getTimeCountdown();
		t--;
		setTimeCountdown(t);
		if(t <= 0) {
			txtPin.setEditable(false);
//			txtPin.setEnabled(false);
			stopTimer();
		}
	}
	
	class VerificationWorker extends SwingWorker<String, Void> {

		VerificationWorker(){
			MainFrame.setWaitCursor(true);
			btnNext.setEnabled(false);
		}
		
		@Override
		protected String doInBackground() throws Exception {
			Controller controller = Controller.getInstance();
			return controller.verifyPin(model.createRequest());
		}
		
		@Override
		protected void done() {
			try {
				String status = get();
				
				boolean success = false;
				try {
					UUID.fromString(status);
					success = true;
				}
				catch(IllegalArgumentException e) {
					success = false;
				}
				
				if(!success) {
					// Failed OTP validation
					LOGGER.error(status);
					JOptionPane.showMessageDialog(
							MainFrame.getInstance(), 
							status, 
							"Validation Error", 
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					LOGGER.info(status);
					JOptionPane.showMessageDialog(
							MainFrame.getInstance(), 
							"Connection/saved network (" + status + ") successfully added", 
							"Configuration saved", 
							JOptionPane.INFORMATION_MESSAGE);
					Controller.getInstance().resetRegistrationForms();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			
			MainFrame.setWaitCursor(false);
			btnNext.setEnabled(true);
			
			SetupPage.getWizard().showCard(SetupStage.CONFIGURATION.toString());
		}
		
	}
}
