package gyk4j.wols.view.component.privacypolicy;

import java.net.URL;

import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.view.component.HtmlPage;

public class PrivacyPolicyPage extends HtmlPage {

	private static final long serialVersionUID = 1L;

	public PrivacyPolicyPage(String title) {
		super(title);
		URL url = ResourceLoader.getFileFromResource("privacy.html");
		loadHtml(url);
	}

}
