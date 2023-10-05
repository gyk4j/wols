package net.altkey12.wols.view.component.termsandconditions;

import java.net.URL;

import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.HtmlPage;

public class TermsAndConditionsPage extends HtmlPage {

	private static final long serialVersionUID = 1L;

	public TermsAndConditionsPage(String title) {
		super(title);
		URL url = ResourceLoader.getFileFromResource("termsandconditions.html");
		loadHtml(url);
	}

}
