package gyk4j.wols.view.component.setup;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.model.setup.SetupRegistrationModel;
import gyk4j.wols.view.component.RoundToggleButton;
import gyk4j.wols.view.component.TransparentPanel;

import static org.python.lang.global.ISP_CONFIG;

public class Providers extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	public static final String[] PROVIDERS = { "m1", "singtel", "starhub", "simba", };

	List<JToggleButton> providers = new ArrayList<>();
	
	final SetupRegistrationModel model;

	public Providers(SetupRegistrationModel model) {
		super();
		this.model = model;
//		this.setBackground(Css.PAGE_BACKGROUND);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		ButtonGroup group = new ButtonGroup();

		for (String provider : ISP_CONFIG.keySet()) {
			ImageIcon icon = ResourceLoader.getImageIcon("lg-32-" + provider + ".png");
			RoundToggleButton option = new RoundToggleButton(icon);
			
//			option.setIcon(icon);
			// option.setText(provider);
			option.setToolTipText(provider);
			
			ButtonModel bm = model.getProvider(provider);
			bm.setSelected("singtel".equals(provider));
			bm.setEnabled("singtel".equals(provider) || "starhub".equals(provider));
			
			option.setModel(bm);
			
			group.add(option);

			providers.add(option);
			add(option);
		}
		
		add(Box.createHorizontalGlue());

		// providers.get(0).setSelected(true);
	}

	public List<JToggleButton> getProviders() {
		return providers;
	}
}