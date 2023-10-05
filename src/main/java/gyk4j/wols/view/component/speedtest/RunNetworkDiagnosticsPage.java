package gyk4j.wols.view.component.speedtest;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import gyk4j.wols.beans.speedtest.DiagnosticsResults;
import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.OutlineButton;
import gyk4j.wols.view.component.ScrollPage;
import gyk4j.wols.view.component.main.MainPanel;

public class RunNetworkDiagnosticsPage extends ScrollPage {

	private static final long serialVersionUID = 1L;
	
	boolean isTestRunning = true;
	
	JButton btnBack = new OutlineButton("< Back", Color.BLACK, true);
	JPanel pnlTestRunning = new SpeedTestWaitingPanel();
	RunNetworkDiagnosticsResultsPanel pnlTestResults = new RunNetworkDiagnosticsResultsPanel();
	
	private DiagnosticsResults r;
	
	public RunNetworkDiagnosticsPage(String title) {
		super(title);
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComponent page = MainPanel.getInstance().getActiveTabPage();
				if(page instanceof SpeedTestPage) {
					((SpeedTestPage) page).previous();
				}
			}
			
		});
		
		addComponent(BoxUtils.createLeftBox(btnBack));
		addComponent(pnlTestRunning);
		addComponent(pnlTestResults);
		addComponent(Box.createVerticalGlue());
		
		updateComponents();
		
		this.addAncestorListener(new AncestorListener() {

			@Override
			public void ancestorAdded(AncestorEvent event) {
				if(r == null) {
					SwingWorker<DiagnosticsResults, Object> worker = new DiagnosticsWorker();
					worker.execute();
					isTestRunning = true;
				}
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {}

			@Override
			public void ancestorMoved(AncestorEvent event) {}
			
		});
	}
	
	protected void updateComponents() {
		pnlTestRunning.setVisible(isTestRunning);
		resizePanelAbsolute(pnlTestResults.getPreferredSize());
		pnlTestResults.setVisible(!isTestRunning);
	}
	
	class DiagnosticsWorker extends SwingWorker<DiagnosticsResults, Object> {
		
		@Override
		protected DiagnosticsResults doInBackground() throws Exception {
			DiagnosticsResults results = Controller.getInstance().runDiagnostics();
			return results;
		}

		@Override
		protected void done() {
			try {
				r = get();
				isTestRunning = false;
				pnlTestResults.refresh(r);
				updateComponents();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
