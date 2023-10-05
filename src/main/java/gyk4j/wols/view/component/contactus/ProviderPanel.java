package gyk4j.wols.view.component.contactus;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.LinkLabel;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;

public class ProviderPanel extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	private static final int MARGIN = 0;
	private static final Insets insetsTop = new Insets(8,24,0,0);
	private static final Insets insetsBottom = new Insets(0,24,8,0);
	private static final Insets insetsLogo = new Insets(8,24,8,24);
	
	TransparentLabel lblName = new TransparentLabel("ABC");
	
	TransparentLabel lblContactNo = new TransparentLabel("Contact No.");
	JLabel lblContactNoValue = new LinkLabel("+65 XXXX XXXX");
	
	TransparentLabel lblEmail = new TransparentLabel("Email");
	JLabel lblEmailValue = new LinkLabel("user@domain.com");
	
	TransparentLabel lblWebsite = new TransparentLabel("Website");
	JLabel lblWebsiteValue = new LinkLabel("www.company.com/personal/products-services/broadband/wireless-sg");
	
	TransparentLabel lblLogo = new TransparentLabel();
	public static final String PLACEHOLDER_LOGO = "tn-m1.png";

	public ProviderPanel() {
		// Some sample placeholder text for testing.
		this("ABC", "+65 XXXX XXXX", "user@domain.com", "www.company.com/personal/products-services/broadband/wireless-sg", PLACEHOLDER_LOGO);
	}
	
	public ProviderPanel(String name, String contactNo, String email, String website, String logo) {
		super();
		
		this.setBorder(BorderFactory.createLineBorder(Css.PROGRESSBAR_INACTIVE_FOREGROUND));
		
		lblName.setText(name);
		lblName.setFont(lblName.getFont().deriveFont(Font.BOLD));
		
		lblContactNoValue.setText(contactNo);
		lblEmailValue.setText(email);
		lblWebsiteValue.setText(website);
//		lblWebsiteValue.setPreferredSize(new Dimension(380, 16));
		
		ImageIcon icon = ResourceLoader.getImageIcon(logo);
		lblLogo.setIcon(icon);
		lblLogo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogo.setVerticalAlignment(SwingConstants.BOTTOM);
		
		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.ipadx = MARGIN;
		c.insets = insetsTop;
		add(lblName, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = MARGIN;
		add(lblContactNo, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = MARGIN;
		add(lblContactNoValue, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = MARGIN;
		add(lblEmail, c);
		
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = MARGIN;
		add(lblEmailValue, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = MARGIN;
		c.insets = insetsBottom;
		add(lblWebsite, c);
		
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.ipadx = MARGIN;
		c.insets = insetsBottom;
		add(lblWebsiteValue, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 4;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		c.insets = insetsLogo;
		c.weightx = 1.0;
		add(lblLogo, c);
	}
}
