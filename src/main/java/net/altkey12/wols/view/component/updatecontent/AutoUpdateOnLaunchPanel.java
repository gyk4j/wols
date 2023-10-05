package net.altkey12.wols.view.component.updatecontent;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.model.updatecontent.AutoUpdateOnLaunchModel;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.TransparentLabel;
import net.altkey12.wols.view.component.TransparentPanel;

public class AutoUpdateOnLaunchPanel extends TransparentPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected AutoUpdateOnLaunchModel autoUpdateOnLaunchModel = new AutoUpdateOnLaunchModel();
	
	protected final static int BORDER_MARGIN = 8;
	
	protected final JLabel lblAutoUpdateOnLaunch = new TransparentLabel("Auto update on launch");
	protected final JToggleButton btnAutoUpdateOnLaunch = new JToggleButton();

	public AutoUpdateOnLaunchPanel() {
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		lblAutoUpdateOnLaunch.setFont(Css.FONT_14PT);
		
		// Draw line border on top and bottom, with some spacing.
		Border border = BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(2, 0, 2, 0, Color.LIGHT_GRAY), 
				BorderFactory.createEmptyBorder(BORDER_MARGIN, 0, BORDER_MARGIN, 0));
		
		setBorder(border);
		
		btnAutoUpdateOnLaunch.setBorder(BorderFactory.createEmptyBorder(0,2,0,2));
		btnAutoUpdateOnLaunch.setContentAreaFilled(false);
		btnAutoUpdateOnLaunch.setOpaque(false);
		btnAutoUpdateOnLaunch.setFocusable(true);
		btnAutoUpdateOnLaunch.requestFocusInWindow();
		btnAutoUpdateOnLaunch.setToolTipText("Click to toggle");
		btnAutoUpdateOnLaunch.setFont(Css.FONT_COUNTDOWN);
		ImageIcon sliderSwitchOn = ResourceLoader.getImageIcon("toggle-on.png");
		ImageIcon sliderSwitchOff = ResourceLoader.getImageIcon("toggle-off.png");
		btnAutoUpdateOnLaunch.setIcon(sliderSwitchOff);
		btnAutoUpdateOnLaunch.setSelectedIcon(sliderSwitchOn);
		btnAutoUpdateOnLaunch.setRolloverEnabled(false);
		btnAutoUpdateOnLaunch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				toggleAutoUpdateOnLaunch();
			}
			
		});
		
		btnAutoUpdateOnLaunch.setModel(autoUpdateOnLaunchModel.getAutoUpdateOnLaunch());
		
		add(lblAutoUpdateOnLaunch);
		add(Box.createHorizontalGlue());
		add(btnAutoUpdateOnLaunch);
		
		refresh();
	}
	
	public void refresh() {
		boolean update = Controller.getInstance().getUpdateOnLaunchSetting();
		autoUpdateOnLaunchModel.getAutoUpdateOnLaunch().setSelected(update);
	}
	
	public void toggleAutoUpdateOnLaunch() {
		boolean update = autoUpdateOnLaunchModel.getAutoUpdateOnLaunch().isSelected();
		Controller.getInstance().setAutoUpdateOnLaunch(update);
	}

}
