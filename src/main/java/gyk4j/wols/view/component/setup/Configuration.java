package gyk4j.wols.view.component.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.model.setup.SetupConfigurationModel;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.FlatButton;
import gyk4j.wols.view.component.TitledPage;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.app.MainFrame;

public class Configuration extends TitledPage {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
	
	final SetupConfigurationModel model = new SetupConfigurationModel();
	
	JLabel lblInfo = new TransparentLabel();

	FlatButton btnProceed = new FlatButton("Proceed", Color.WHITE, Css.BUTTON_CONFIGURATION_PROCEED);
	
	public Configuration(String title) {
		super(title);
		
		TransparentPanel informationRow = new TransparentPanel();
		informationRow.setLayout(new BoxLayout(informationRow, BoxLayout.LINE_AXIS));
		informationRow.add(lblInfo);
		informationRow.add(Box.createHorizontalGlue());
		
		TransparentPanel actionsRow = new TransparentPanel();
		actionsRow.setLayout(new BoxLayout(actionsRow, BoxLayout.LINE_AXIS));
		actionsRow.add(btnProceed);
		actionsRow.add(Box.createHorizontalGlue());
		
		add(Box.createRigidArea(new Dimension(0, 16)));
		add(informationRow);
		add(Box.createRigidArea(new Dimension(0, 16)));
		add(actionsRow);
		
		btnProceed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SetupPage.getWizard().showCard(SetupStage.REGISTRATION.toString());
			}
			
		});
		
		this.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				LOGGER.debug("ancestorAdded: {}", event.paramString());
//				ConfigurationResult cr = model.checkConfiguration();
//				if(cr.isOK()) {
				LOGGER.trace("Executing ConfigurationWorker");
				new ConfigurationWorker().execute();
//				}
//				else {
//					LOGGER.trace("Skipped ConfigurationWorker");
//				}
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}
		});
	}
	
	public SetupConfigurationModel getModel() {
		return model;
	}
	
	class ConfigurationWorker extends SwingWorker<String, Void> {
		
		ConfigurationWorker() {
			MainFrame.setWaitCursor(true);
			btnProceed.setEnabled(false);
		}

		@Override
		protected String doInBackground() throws Exception {
			return Controller.getInstance().checkConfigurationResult();
		}
		
		@Override
		protected void done() {
			String message = null;
			try {
				String newConnectionUuid = get();
				if(newConnectionUuid != null && 
						newConnectionUuid.length() > 0 && 
						UUID.fromString(newConnectionUuid) != null) {
					String ssid = Controller.getInstance().getSSID();
					message = "Congratulations, we have configured your device to connect to " + ssid + ".";
				}
				else {
					message = "Problem accessing your network preferences. Please try again later.";
				}				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			MainFrame.setWaitCursor(false);
			btnProceed.setEnabled(true);
			lblInfo.setText(message);
		}
		
	}
}
