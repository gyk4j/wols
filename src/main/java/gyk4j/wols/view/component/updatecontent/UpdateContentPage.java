package gyk4j.wols.view.component.updatecontent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.FlatButton;
import gyk4j.wols.view.component.TitledPage;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.faqs.FaqsPage;
import gyk4j.wols.view.component.news.NewsPage;

public class UpdateContentPage extends TitledPage {
	
	private static final long serialVersionUID = 1L;
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
	public static final int LINE_SPACING = 16;
	public static final int LINE_SPACING_BIGGER = 24;
	
	protected final JLabel lblInfo = new TransparentLabel("A copy of the hotspot list and FAQs are stored on your device for offline use.");
	protected final JLabel lblInstruction = new TransparentLabel("To find out where the latest hotpots are, please update the content regularly.");
	protected final JLabel lblStatus = new TransparentLabel("No New Content");
	protected final JButton btnUpdateNow = new FlatButton("Update Now!", Color.WHITE, Color.LIGHT_GRAY);
	protected final JLabel lblLastUpdated = new TransparentLabel();
	
	protected final JPanel pnlAutoUpdateOnLaunch = new AutoUpdateOnLaunchPanel();

	public UpdateContentPage(String title) {
		super(null);
		setBorder(Css.STEPPER_BORDER);
		
		lblInfo.setFont(Css.FONT_14PT);
		lblInstruction.setFont(Css.FONT_14PT);
		lblStatus.setFont(Css.FONT_14PT);
		btnUpdateNow.setFont(Css.FONT_UPDATE_NOW);
//		btnUpdateNow.setEnabled(false);
		btnUpdateNow.setBackground(Css.TOP_END);
		btnUpdateNow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateNow();
			}
			
		});
		lblLastUpdated.setFont(Css.FONT_14PT);
		
		add(BoxUtils.createLeftBox(lblInfo));
		add(Box.createVerticalStrut(LINE_SPACING));
		add(BoxUtils.createLeftBox(lblInstruction));
		add(Box.createVerticalStrut(LINE_SPACING_BIGGER));
		add(BoxUtils.createLeftBox(lblStatus));
		add(Box.createVerticalStrut(LINE_SPACING_BIGGER));
		add(BoxUtils.createLeftBox(btnUpdateNow));
		add(Box.createVerticalStrut(LINE_SPACING));
		add(BoxUtils.createLeftBox(lblLastUpdated));
		add(Box.createVerticalStrut(LINE_SPACING_BIGGER));
		add(pnlAutoUpdateOnLaunch);
		
		loadData();
	}
	
	protected void loadData() {
		LocalDate d = Controller.getInstance().getDate();
		String date = (d != null)? FORMATTER.format(d): "never";
		
		String v = Controller.getInstance().getVersion();
		String version = (v != null)? v : "unknown";
		
		String caption = String.format("Last Updated: %s (%s)", date, version);
		lblLastUpdated.setText(caption);
	}
	
	protected void updateNow() {
		boolean newUpdate = Controller.getInstance().updateNow();
		
		if(newUpdate) {
			// Update the GUI if pressing the "Update now" button.
			NewsPage.getFrontPage().refresh();
			FaqsPage.getSection().refresh();
			
			loadData();
			lblStatus.setText("New content updated");
		}
		else {
			lblStatus.setText("No New Content");
		}
	}
}
