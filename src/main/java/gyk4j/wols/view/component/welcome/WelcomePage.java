package gyk4j.wols.view.component.welcome;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;

import gyk4j.wols.Wols;
import gyk4j.wols.controller.Controller;
import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.FlatButton;
import gyk4j.wols.view.component.GradientBackgroundPage;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.BoxUtils.LineSpacing;

public class WelcomePage extends GradientBackgroundPage implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private static final Font TEXT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
	private static final Color BUTTON_COLOR = new Color(0, 121, 139);
	
	TransparentLabel lblTitle = new TransparentLabel("WOLS App", Color.WHITE);
	TransparentLabel lblLogo = new TransparentLabel();
	TransparentLabel lblObjective = new TransparentLabel("Get Connected to Singapore's largest free Wi-Fi network", Color.WHITE);
	TransparentLabel lblProvider = new TransparentLabel("(App brought to you by " + Wols.AUTHOR + ")", Color.WHITE);
	FlatButton btnLetsGo = new FlatButton("Let's Go", Color.WHITE, Css.TOP_END);

	public WelcomePage() {
		super();
		
		lblTitle.setFont(TITLE_FONT);
		
		ImageIcon icon = ResourceLoader.getImageIcon(Css.LOGO);
		lblLogo.setIcon(icon);
		
		lblObjective.setFont(TEXT_FONT);
		lblProvider.setFont(TEXT_FONT);
		
		btnLetsGo.setBackground(BUTTON_COLOR);
		btnLetsGo.addActionListener(this);
		
		add(Box.createVerticalGlue());
		add(BoxUtils.createCenteredBox(lblTitle));
		add(BoxUtils.createLineSpacing(LineSpacing.FAT));
		add(BoxUtils.createCenteredBox(lblLogo));
		add(BoxUtils.createLineSpacing(LineSpacing.FAT));
		add(BoxUtils.createCenteredBox(lblObjective));
		add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
		add(BoxUtils.createCenteredBox(lblProvider));
		add(BoxUtils.createLineSpacing(LineSpacing.FAT));
		add(BoxUtils.createCenteredBox(btnLetsGo));
		add(Box.createVerticalGlue());
	}	

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller.getInstance().nextScene();
	}

}
