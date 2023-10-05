package net.altkey12.wols.view.component.speedtest;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.BoxUtils;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.FlatButton;
import net.altkey12.wols.view.component.TransparentLabel;
import net.altkey12.wols.view.component.TransparentPanel;

public class SpeedTestPanel extends TransparentPanel {

	private static final String SPEEDTEST_URI = "https://mqspectrum.speedtestcustom.com/";

	private static final int SPEEDTEST_LOGO_HEIGHT = 28;

	private static final int SPEEDTEST_LOGO_WIDTH = 32;

	private static final String SPEEDTEST_LOGO = "speedtest-logo.png";

	private static final long serialVersionUID = 1L;
	
	protected final Font SPEED_TEST_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 32);
	protected final Font INFO_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	
	protected static final Color INFO_LABEL_BACKGROUND = new Color(76, 122, 179);
	protected static final Color INFO_LABEL_FOREGROUND = Color.WHITE;
	
	protected final JLabel lblSpeedTest = new TransparentLabel("SPEED TEST");
	protected final JLabel lblInfo = new JLabel(
			"<html>"
//			+ "<font size=-1>"
			+ "Your speed test will be launched in your default browser.<br/>"
			+ "<br/>"
			+ "Click on the launch button to begin your test.<br/>"
//			+ "</font>"
			+ "</html>");
	
	protected static final Color LAUNCH_BUTTON_BACKGROUND = new Color(185, 217, 255);
	protected static final Color LAUNCH_BUTTON_FOREGROUND = new Color(112, 177, 248);
	protected static final Color LAUNCH_BUTTON_BORDER = Color.DARK_GRAY;
	protected static final Font LAUNCH_BUTTON_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 12);
	
	protected final JButton btnLaunch = new FlatButton("Launch", LAUNCH_BUTTON_FOREGROUND, LAUNCH_BUTTON_BACKGROUND);

	public SpeedTestPanel() {
		super();
//		this.setBorder(Css.DEBUG_BORDER_YELLOW);
		
		lblSpeedTest.setForeground(Color.WHITE);
		lblSpeedTest.setFont(SPEED_TEST_FONT);
		ImageIcon logo = ResourceLoader.getImageIcon(SPEEDTEST_LOGO);
		Image resized = logo.getImage().getScaledInstance(
				SPEEDTEST_LOGO_WIDTH, 
				SPEEDTEST_LOGO_HEIGHT, 
				Image.SCALE_SMOOTH);
		ImageIcon smaller = new ImageIcon(resized);
		lblSpeedTest.setIconTextGap(8);
		lblSpeedTest.setIcon(smaller);
		
		lblInfo.setForeground(INFO_LABEL_FOREGROUND);
		lblInfo.setBackground(INFO_LABEL_BACKGROUND);
		lblInfo.setFont(INFO_FONT);
		lblInfo.setOpaque(true);
		lblInfo.setMaximumSize(new Dimension(372, 84));
		lblInfo.setPreferredSize(new Dimension(372, 84));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setVerticalAlignment(SwingConstants.CENTER);
		
		btnLaunch.setFont(LAUNCH_BUTTON_FONT);
		btnLaunch.setForeground(Css.TOP_START);
		Border border = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(LAUNCH_BUTTON_BORDER, 2),
				BorderFactory.createEmptyBorder(6, 24, 6, 24)
		);
		btnLaunch.setBorder(border);
		btnLaunch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLaunch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				URI uri;
				try {
					uri = new URI(SPEEDTEST_URI);
					Desktop.getDesktop().browse(uri);
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}				
			}
		});
		
		add(Box.createVerticalGlue());
		add(BoxUtils.createCenteredBox(lblSpeedTest));
		add(Box.createVerticalStrut(64));
		add(BoxUtils.createCenteredBox(lblInfo));
		add(Box.createVerticalStrut(32));
		add(BoxUtils.createCenteredBox(btnLaunch));
		add(Box.createVerticalGlue());
	}

}
