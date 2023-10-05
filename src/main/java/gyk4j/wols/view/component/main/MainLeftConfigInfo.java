package gyk4j.wols.view.component.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TransparentLabel;

public class MainLeftConfigInfo extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MainLeftConfigInfo.class);
	
	private JLabel lblConfiguredFor = new TransparentLabel("Configured for");
	private JLabel lblIsp = new TransparentLabel();
	
	public MainLeftConfigInfo() {
		this.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
		this.setLayout(new GridLayout(0, 1));
		this.setBackground(Css.BACKGROUND_UNSELECTED);
		
		Controller controller = Controller.getInstance();
		
		lblConfiguredFor.setFont(Css.FONT_10PT);
		this.add(lblConfiguredFor);
		
		lblIsp.setFont(Css.FONT_ISP);
		lblIsp.setToolTipText("");
		this.add(lblIsp);
		
		new NetworkProfilerWorker().execute();
		
		LocalDateTime configurationDateTime = controller.getConfigurationDateTime();
		String date = (configurationDateTime != null)? 
				configurationDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")):
					"";
		JLabel configurationDate = new JLabel(date);
		configurationDate.setFont(Css.FONT_10PT);
		this.add(configurationDate);
		
		String appIdString = controller.getApplicationId().toString().toUpperCase();
		JLabel appId = new JLabel("Application ID: " + appIdString);
		appId.setFont(Css.FONT_14PT);
//		appId.setMaximumSize(new Dimension(10, 20));
		appId.setPreferredSize(new Dimension(128, 20));
		appId.setToolTipText(appIdString);
		
		this.add(appId);
//		this.add(Box.createRigidArea(new Dimension(0, 10)));
		this.add(new JLabel());
		
		String version = controller.getApplicationVersion();
		JLabel lblVersion = new JLabel("Version: ".concat(version));
		lblVersion.setFont(Css.FONT_14PT);
		this.add(lblVersion);
	}

//	public JLabel getIsp() {
//		return isp;
//	}
//
//	public void setIsp(JLabel isp) {
//		this.isp = isp;
//	}
	
	class NetworkProfilerWorker extends SwingWorker<String, Object> {

		public NetworkProfilerWorker() {
			super();
		}

		@Override
		protected String doInBackground() throws Exception {
			String isp = Controller.getInstance().getConfiguredIsp();
			return isp;
		}
		
		@Override
		protected void done() {
			try {
				String isp = get();
				
				if(isp != null) {
					// Retrieved ISP successfully.
					// Either no connection or connection with a telco.
					lblConfiguredFor.setVisible(true);
					lblIsp.setText(isp);
				}
				else {
					// Error reading despite .nmconnection exists.
					// Allow the user to click and check again.
					lblConfiguredFor.setVisible(false);
					lblIsp.setText("Unconfigured");
				}
				lblIsp.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				lblIsp.setToolTipText(lblIsp.getText());
			} 
			catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
	}
}
