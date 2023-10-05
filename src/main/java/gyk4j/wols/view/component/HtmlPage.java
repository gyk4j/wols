package gyk4j.wols.view.component;

import java.net.URL;

public class HtmlPage extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	AntialiasingHtmlView webView = new AntialiasingHtmlView();

	public HtmlPage() {
		super();
	}
	
	public HtmlPage(String title) {
		super();
		add(webView);
	}
	
	public void loadHtml(URL url) {
		webView.loadHtml(url);
	}
	
	public void loadHtml(String html) {
		webView.loadHtml(html);
	}

	public AntialiasingHtmlView getWebView() {
		return webView;
	}
}
