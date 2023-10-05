package gyk4j.wols.view.component.termsandconditions;

import java.net.URL;

import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.view.component.HtmlPage;

public class TermsAndConditionsPage extends HtmlPage {

	private static final long serialVersionUID = 1L;

	public TermsAndConditionsPage(String title) {
		super(title);
		URL url = ResourceLoader.getFileFromResource("termsandconditions.html");
		loadHtml(url);
	}

}
