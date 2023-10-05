package net.altkey12.wols.io;

import java.awt.Color;

import net.altkey12.wols.view.component.Css;

public class HtmlEncoder {
	public static String stripHtml(String html) {
		String text = html.replaceAll("<.*?>", "");
		return text;
	}
	
	public static String createText(String s) {
		String text = String.format("<html>%s</html>", s);
		return text;
	}
	
	public static String createLinkText(String s) {
		String colorHex = createColor(Css.LINK_TEXT);
		String text = String.format(
				"<html><u><font color=%s>%s</font></u></html>", 
				colorHex, s);
		return text;
	}
	
	public static String createColor(Color c) {
		return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	public static String createColorText(String s, int pt, Color c) {
		String colorHex = createColor(c);
		
		String html = String.format("<font color=%s size=%d>%s</font>", 
				colorHex, pt, StringUtils.encodeHtml(s));
//		System.out.println("HtmlEncoder.createColorText: " + html);
		return html;
	}
}
