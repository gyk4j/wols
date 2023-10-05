package net.altkey12.wols.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.altkey12.wols.io.ResourceLoader;

public class AcceptDialogPage extends GradientBackgroundPage implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	private static final Color BUTTON_COLOR = new Color(0, 121, 139);
	private static final Font BUTTON_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
	private static final Border BORDER = BorderFactory.createEmptyBorder(8, 16, 8, 16);
	
	JLabel header = new TransparentLabel("Terms and Conditions", Color.WHITE);
	JPanel body = new HtmlPage(null);
	JButton footer = new FlatButton("Accept and Continue", Color.WHITE, BUTTON_COLOR);
	
	public AcceptDialogPage() {
		this("Read Me", "privacy.rtf", "OK");
	}
	
	public AcceptDialogPage(String title, String resource, String button) {
		super();
		header.setText(title);
		header.setFont(TITLE_FONT);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		URL url = ResourceLoader.getFileFromResource(resource);
		((HtmlPage) body).loadHtml(url);
		footer.setText(button);
		footer.setFont(BUTTON_FONT);
		footer.addActionListener(this);
		
		JPanel rowTop = new TransparentPanel();
		rowTop.setLayout(new BoxLayout(rowTop, BoxLayout.LINE_AXIS));
		rowTop.setBorder(BORDER);
		rowTop.add(Box.createHorizontalGlue());
		rowTop.add(header);
		rowTop.add(Box.createHorizontalGlue());
		
		JPanel rowBottom = new TransparentPanel();
		rowBottom.setLayout(new BoxLayout(rowBottom, BoxLayout.LINE_AXIS));
		rowBottom.setBorder(BORDER);
		rowBottom.add(Box.createHorizontalGlue());
		rowBottom.add(footer);
		rowBottom.add(Box.createHorizontalGlue());
		
		/*
		JPanel rowTop = BoxUtils.createFilledBox(header);
		rowTop.setBorder(BORDER);
		add(rowTop);
		add(BoxUtils.createFilledBox(body));
		
		JPanel rowBottom = BoxUtils.createFilledBox(footer);
		rowBottom.setBorder(BORDER);
		add(rowBottom);
		*/
		
		setLayout(new BorderLayout());
		add(rowTop, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
		add(rowBottom, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(this,
			    "Eggs are not supposed to be green.");
	}
	
}
