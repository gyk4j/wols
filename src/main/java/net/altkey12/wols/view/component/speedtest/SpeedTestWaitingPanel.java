package net.altkey12.wols.view.component.speedtest;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import net.altkey12.wols.view.component.BoxUtils;
import net.altkey12.wols.view.component.TransparentLabel;
import net.altkey12.wols.view.component.TransparentPanel;
import net.altkey12.wols.view.component.BoxUtils.LineSpacing;

public class SpeedTestWaitingPanel extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	JLabel lblPleaseWait = new TransparentLabel("Please wait while we run the diagnostics tests.");
	JProgressBar pgbRunning = new JProgressBar();

	public SpeedTestWaitingPanel() {
		super();
		
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		add(BoxUtils.createLeftBox(lblPleaseWait));
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		add(pgbRunning);
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		pgbRunning.setIndeterminate(true);
	}
}
