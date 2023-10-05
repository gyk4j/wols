package gyk4j.wols.view.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import gyk4j.wols.io.HtmlEncoder;
import gyk4j.wols.view.component.app.MainFrame;

public class LinkLabel extends TransparentLabel {

	private static final long serialVersionUID = 1L;
	
	protected URI href;
	
	public LinkLabel() {
		super();
	}

	public LinkLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	public LinkLabel(Icon image) {
		super(image);
	}

	public LinkLabel(String text, Color color) {
		super(text, color);
	}

	public LinkLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	public LinkLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public LinkLabel(String text) {
		super(text);
	}
	
	@Override
	public void setText(String text) {
		String underlined = text.substring(
				0, 
				Integer.min(48, text.length()));
		
		String html = HtmlEncoder.createLinkText(underlined);
		super.setText(html);
		
		try {
			if (text.startsWith("http://") || 
					text.startsWith("https://") || 
					text.startsWith("mailto:")) {
				setHref(new URI(text));
			} else if (text.startsWith("+")) {
				setHref(new URI("tel:".concat(text.trim().replace(' ', '-'))));
			} else if (text.indexOf('@') != -1) {
				setHref(new URI("mailto:".concat(text)));
			} else {
				setHref(new URI("http://".concat(text)));
			}
		} catch (URISyntaxException ex) {
			ex.printStackTrace();
		}
		
		super.setToolTipText(getHref().toString());
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
//				super.mouseClicked(e);
				e.consume();
				
				LinkLabel l = (LinkLabel) e.getSource();
				l.handle(getHref());
			}			
		});
	}
	
	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}
	
	protected void handle(URI uri) {
		if(!Desktop.isDesktopSupported()) {
			return;
		}
		
		Desktop desktop = Desktop.getDesktop();
		
		try {
			switch (uri.getScheme()) {
			case "http":
			case "https":
				if(desktop.isSupported(Desktop.Action.BROWSE))
					desktop.browse(uri);
				else
					System.err.println("Desktop does not support BROWSE action.");
				break;
			case "mailto":
				if(desktop.isSupported(Desktop.Action.MAIL))
					desktop.mail(uri);
				else 
					System.err.println("Desktop does not support MAIL action.");
				break;
			case "file":
				if(desktop.isSupported(Desktop.Action.OPEN))
					desktop.open(Paths.get(uri).toFile());
				else { // Try edit if open is not supported.
					System.err.println("Desktop does not support OPEN action.");
				
					if(desktop.isSupported(Desktop.Action.EDIT))
						desktop.edit(Paths.get(uri).toFile());
					else
						System.err.println("Desktop does not support EDIT action.");
				}
				break;
			default:
				System.err.println("Unknown URI: " + uri.toString());
				break;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					MainFrame.getInstance(), 
					e.getMessage(), 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
