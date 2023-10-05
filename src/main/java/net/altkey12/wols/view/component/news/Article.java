package net.altkey12.wols.view.component.news;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.altkey12.wols.beans.updatecontent.News;
import net.altkey12.wols.io.HtmlEncoder;
import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.TransparentLabel;
import net.altkey12.wols.view.component.TransparentPanel;
import net.altkey12.wols.view.component.main.MainPanel;

public class Article extends TransparentPanel {
	private static final long serialVersionUID = 1L;
	
	protected static final int THUMBNAIL_WIDTH = 80;
	protected static final int THUMBNAIL_HEIGHT = 80;
	
	protected static final URL url = ResourceLoader.getFileFromResource("news_new_icon.png");
	protected static final Image overlay = Toolkit.getDefaultToolkit().getImage(url);
	
	News news;

	public Article(News news) {
		this.news = news;
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setOpaque(true);
		setBackground(Css.NEWS_GRAY_LIGHT);
//		setBorder(Css.DEBUG_BORDER_GREEN);
		
		JLabel icon = new JLabel();
		icon.setBackground(Css.NEWS_GRAY_DARK);
		icon.setOpaque(true);
//		icon.setBorder(Css.DEBUG_BORDER_GREEN);
//		icon.setHorizontalAlignment(SwingConstants.CENTER);
		Image placeholder = ResourceLoader.getImage(news.getImage(), ResourceLoader.NEWS_PLACEHOLDER_THUMBNAIL);
		Image newImage = placeholder.getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_SMOOTH);
		icon.setIcon(new ImageIcon(newImage));

		add(icon);
		
		JPanel text = new JPanel();
		text.setBackground(Css.NEWS_GRAY_LIGHT);
		text.setOpaque(true);
		text.setBorder(Css.STEPPER_BORDER);
		
		text.setLayout(new BoxLayout(text, BoxLayout.PAGE_AXIS));
		
		String html = HtmlEncoder.createText(news.getTitle());
		JLabel title = new TransparentLabel(html);
		title.setFont(Css.FONT_10PT);
		title.setForeground(Css.COLOR_PAGE);
		title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		title.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				JComponent page = MainPanel.getInstance().getActiveTabPage();
				if(page instanceof NewsPage) {
					NewsPage.getArticlePage().setNews(news);
					((NewsPage) page).getViews().next();
				}
			}
			
		});
		text.add(title);
		text.add(Box.createVerticalGlue());
		JLabel date = new JLabel(news.getDate().format(FrontPage.FORMATTER));
		date.setFont(Css.FONT_10PT);
		text.add(date);
		
		add(text);
		
		add(Box.createGlue());
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

}
