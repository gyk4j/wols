package net.altkey12.wols.view.component;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntialiasingHtmlView extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AntialiasingHtmlView.class);
	
	protected JEditorPane editorPane = new AntialiasingEditorPane();
	protected JScrollPane editorScrollPane = new JScrollPane(editorPane);
	
	public AntialiasingHtmlView() {
		super();
		
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setPreferredSize(new Dimension(250, 145));
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		editorScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		
		add(editorScrollPane);
	}
	
	public void loadHtml(URL helpURL) {
		if (helpURL != null) {
		    try {
		    	if(helpURL.toString().toLowerCase().endsWith("rtf")) {
		    		editorPane.setContentType("text/rtf");
		    	}
		    	else if(helpURL.toString().toLowerCase().endsWith("html")) {
		    		editorPane.setContentType("text/html");
		    	}
		    	else {
		    		editorPane.setContentType("text/plain");
		    	}
		        editorPane.setPage(helpURL);
		    } catch (IOException e) {
		    	LOGGER.error("Attempted to read a bad URL: " + helpURL);
		    }
		} else {
			LOGGER.error("helpURL is null");
		}
	}
	
	public void loadHtml(String html) {
		editorPane.setContentType("text/html");
		editorPane.setText(html);
	}
	
	public void reload() {
		URL page = editorPane.getPage();
		Document doc = editorPane.getDocument();
		doc.putProperty(Document.StreamDescriptionProperty, null);
		loadHtml(page);
	}
	
	public static class AntialiasingEditorPane extends JEditorPane {

		private static final long serialVersionUID = 1L;

		public AntialiasingEditorPane() {
			super();
			initialize();
		}
		
		public AntialiasingEditorPane(String type, String text) {
			super(type, text);
			initialize();
		}
		
		public AntialiasingEditorPane(String url) throws IOException {
			super(url);
			initialize();
		}

		public AntialiasingEditorPane(URL initialPage) throws IOException {
			super(initialPage);
			initialize();
		}

		protected void initialize() {
			setEditable(false);
			putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
			setFont(Css.FONT_14PT);
//			setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			setBorder(Css.HTMLVIEW_BORDER);
			setOpaque(true);
			setBackground(Color.WHITE);
			this.addHyperlinkListener(new HyperlinkListener() {

				@Override
				public void hyperlinkUpdate(HyperlinkEvent e) {
					if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
						URL url = e.getURL();
						
						if("localhost".equals(url.getHost()) || "127.0.0.1".equals(url.getHost())) {
							LOGGER.trace("Ignored {}", url.toString());
						}
						else if(Desktop.isDesktopSupported()) {
							Desktop d = Desktop.getDesktop();
							
							if(d.isSupported(Desktop.Action.BROWSE)) {
								try {
									d.browse(url.toURI());
								} catch (IOException e1) {
									LOGGER.error(e1.getMessage());
								} catch (URISyntaxException e1) {
									LOGGER.error(e1.getMessage());
								}
							}
							else {
								LOGGER.error("Desktop is not supported.");
							}
						}
					}
				}
				
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			super.paintComponent(g2);
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JEditorPane getEditorPane() {
		return editorPane;
	}

	public JScrollPane getEditorScrollPane() {
		return editorScrollPane;
	}
}
