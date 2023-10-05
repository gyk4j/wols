package gyk4j.wols.view.component.speedtest;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.BoxUtils.LineSpacing;

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
