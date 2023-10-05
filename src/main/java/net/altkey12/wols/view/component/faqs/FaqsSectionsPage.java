package net.altkey12.wols.view.component.faqs;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.altkey12.wols.beans.updatecontent.FaqsSection;
import net.altkey12.wols.beans.updatecontent.QnA;
import net.altkey12.wols.controller.Controller;
import net.altkey12.wols.view.component.AntialiasingHtmlView;
import net.altkey12.wols.view.component.CollapsiblePane;
import net.altkey12.wols.view.component.ScrollPage;

public class FaqsSectionsPage extends ScrollPage {

	private static final long serialVersionUID = 1L;

	FaqsSection[] sections = Controller.getInstance().getFaqs();
	
	public FaqsSectionsPage(String title) {
		super(title);
		
//		add(getWebView());	
//		getWebView().getEditorPane().addHyperlinkListener(new HyperlinkAdapter());
		
		refresh();
	}
	
	public void refresh() {	
		
		for(int s=0; s < sections.length; s++) {
			FaqsSection section = sections[s];
			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<body>");
			
			for(int q=0; q < section.getQuestions().length; q++) {
				QnA qna = section.getQuestions()[q];
				String question = qna.getQuestion();
				sb.append(String.format("<p><a href=\"http://localhost/%d/%d\">%s</a></p>", s, q, question));
			}
			
			sb.append("</body>");
			sb.append("</html>");
			String html = sb.toString();
			
			JEditorPane webView = new AntialiasingHtmlView.AntialiasingEditorPane();
			webView.setContentType("text/html");
			webView.setText(html);
			webView.setCaretPosition(0);
			webView.addHyperlinkListener(new HyperlinkAdapter());
			webView.setBorder(BorderFactory.createEmptyBorder(0, 32, 0, 32));
			
			String header = section.getName();
			CollapsiblePane pane = new CollapsiblePane(header, webView);
			addComponent(pane);
//			String section = sections[s].getName();
//			sb.append(HtmlEncoder.createColorText(section, 10, Css.TOP_START));
//			sb.append("<ol>");
//			for(int q=0; q < sections[s].getQuestions().length; q++) {
//				sb.append("<li>");
//				sb.append(String.format("<a href=\"http://localhost/%d/%d\">%s</a>", 
//						s, q, sections[s].getQuestions()[q].getQuestion()));
//				sb.append("</li>");
//			}
//			
//			sb.append("</ol>");
		}
		addComponent(Box.createVerticalGlue());
	}
	
	public class HyperlinkAdapter implements HyperlinkListener {
		@Override
		public void hyperlinkUpdate(HyperlinkEvent e) {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				String[] tokens = e.getURL().getPath().split("/");
				int s = Integer.parseInt(tokens[1]);
				int q = Integer.parseInt(tokens[2]);
				
				QnA qna = sections[s].getQuestions()[q];
				FaqsPage.getQuestion().setQuestion(qna.getQuestion());
				FaqsPage.getQuestion().setAnswer(qna.getAnswer());
				FaqsPage.getCards().next();
			}
		}
	}
}
