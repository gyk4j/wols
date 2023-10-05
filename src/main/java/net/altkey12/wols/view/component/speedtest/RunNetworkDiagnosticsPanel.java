package net.altkey12.wols.view.component.speedtest;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.Border;

import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.RoundedButton;
import net.altkey12.wols.view.component.TransparentPanel;
import net.altkey12.wols.view.component.main.MainPanel;

public class RunNetworkDiagnosticsPanel extends TransparentPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected static final Color RUN_NETWORK_DIAGNOSTICS_BUTTON_BACKGROUND = Css.TOP_START;
	protected static final Color RUN_NETWORK_DIAGNOSTICS_BUTTON_FOREGROUND = Color.WHITE;
	protected static final Font RUN_NETWORK_DIAGNOSTICS_BUTTON_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	
	protected static final int BORDER_THICKNESS = 12;
	protected static final Border BORDER = BorderFactory.createEmptyBorder(
			BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS);
	
	protected final JButton btnRunNetworkDiagnostics = new RoundedButton(
			"Run Network Diagnostics", 
			RUN_NETWORK_DIAGNOSTICS_BUTTON_FOREGROUND, 
			RUN_NETWORK_DIAGNOSTICS_BUTTON_BACKGROUND);
	

	public RunNetworkDiagnosticsPanel() {
		setLayout(new GridLayout(1,1));
		setBorder(BORDER);
		add(btnRunNetworkDiagnostics);
		
//		Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
//		btnRunNetworkDiagnostics.setBorder(border);
		btnRunNetworkDiagnostics.setFont(RUN_NETWORK_DIAGNOSTICS_BUTTON_FONT);
		btnRunNetworkDiagnostics.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		btnRunNetworkDiagnostics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComponent page = MainPanel.getInstance().getActiveTabPage();
				if(page instanceof SpeedTestPage) {
					((SpeedTestPage)page).next();
				}
			}
			
		});
	}

}
