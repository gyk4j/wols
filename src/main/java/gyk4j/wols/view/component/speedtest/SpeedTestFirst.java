package gyk4j.wols.view.component.speedtest;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TitledPage;

public class SpeedTestFirst extends TitledPage {
	
	private static final long serialVersionUID = 1L;
	
	protected final JPanel pnlSpeedTest = new SpeedTestPanel();
	protected final JPanel pnlRunNetworkDiagnostics = new RunNetworkDiagnosticsPanel();
	
	public SpeedTestFirst(String title) {
		super(null);
		
		setLayout(new BorderLayout());
		
		add(pnlSpeedTest, BorderLayout.CENTER);
		add(pnlRunNetworkDiagnostics, BorderLayout.SOUTH);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Css.TOP_START);
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}

}
