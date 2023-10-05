package net.altkey12.wols.view.component.privacypolicy;

import java.net.URL;

import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.HtmlPage;

public class PrivacyPolicyPage extends HtmlPage {

	private static final long serialVersionUID = 1L;

	public PrivacyPolicyPage(String title) {
		super(title);
		URL url = ResourceLoader.getFileFromResource("privacy.html");
		loadHtml(url);
	}

}
