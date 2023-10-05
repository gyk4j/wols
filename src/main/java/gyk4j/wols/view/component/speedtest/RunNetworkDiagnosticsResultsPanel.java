package gyk4j.wols.view.component.speedtest;

import javax.swing.Box;
import javax.swing.JPanel;

import gyk4j.wols.beans.speedtest.DiagnosticsResults;
import gyk4j.wols.net.DiagnosticsMap;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.CollapsiblePane;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.BoxUtils.LineSpacing;

public class RunNetworkDiagnosticsResultsPanel extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	JPanel[] panes = new JPanel[] { 
			new CollapsiblePane("Diagnostics Information"),
			new CollapsiblePane("Device Information"),
			new CollapsiblePane("Wifi Information"),
			new CollapsiblePane("Network Information"),
			new CollapsiblePane("Wifi Scan Results"),
			new CollapsiblePane("Speed Tests"),
			new CollapsiblePane("General Ping Tests"),
			new CollapsiblePane("AMS Ping Tests"),
			new CollapsiblePane("Exceptions"),			
	};
	
	protected DiagnosticsResults results;
	
	public RunNetworkDiagnosticsResultsPanel() {
		super();
		
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		for(int i=0; i < panes.length; i++) {
			add(panes[i]);
			add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
		}
        add(Box.createVerticalGlue());
	}
	public JPanel[] getPanes() {
		return panes;
	}
	public void setPanes(JPanel[] panes) {
		this.panes = panes;
	}

	public DiagnosticsResults getResults() {
		return results;
	}
	public void setResults(DiagnosticsResults results) {
		this.results = results;
	}

	public void refresh(DiagnosticsResults results) {		
		DiagnosticsMap d;
		
		d = DiagnosticsMap.from(results.getDiagnosticsInformation());
		((CollapsiblePane) panes[0]).setBody(d);
		
		d = DiagnosticsMap.from(results.getDeviceInformation());
		((CollapsiblePane) panes[1]).setBody(d);
		
		d = DiagnosticsMap.from(results.getWifiInformation());
		((CollapsiblePane) panes[2]).setBody(d);
		
		d = DiagnosticsMap.from(results.getNetworkInformation());
		((CollapsiblePane) panes[3]).setBody(d);
		
		d = DiagnosticsMap.from(results.getWifiScanResults());
		((CollapsiblePane) panes[4]).setBody(d);
		
		d = DiagnosticsMap.from(results.getSpeedTests());
		((CollapsiblePane) panes[5]).setBody(d);
		
		d = DiagnosticsMap.from(results.getGeneralPingTests());
		((CollapsiblePane) panes[6]).setBody(d);
		
		d = DiagnosticsMap.from(results.getAmsPingTests());
		((CollapsiblePane) panes[7]).setBody(d);
		
		d = DiagnosticsMap.from(results.getExceptions());
		((CollapsiblePane) panes[8]).setBody(d);
	}
}
