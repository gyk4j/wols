package gyk4j.wols.view.component.news;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gyk4j.wols.beans.updatecontent.News;
import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.main.MainPanel;

public class Jumbotron extends TransparentPanel {
	private static final long serialVersionUID = 1L;
	
	protected static final int THUMBNAIL_WIDTH = 480;
	protected static final int THUMBNAIL_HEIGHT = 240;
	
	News news;
	
	JLabel icon = new JLabel();
	JLabel lblTitle = new TransparentLabel();
	JLabel lblDate = new TransparentLabel();
	JLabel readMore = new TransparentLabel("Read more >");
	
	MouseAdapter mouseClickHandler = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			JComponent page = MainPanel.getInstance().getActiveTabPage();
			if(page instanceof NewsPage) {
				NewsPage.getArticlePage().setNews(news);
				((NewsPage) page).getViews().next();
			}
		}
		
	};

	public Jumbotron(News news) {
		super();
		this.news = news;
		
		Box box = Box.createVerticalBox();
//		box.setBorder(Css.STEPPER_BORDER);
		
		// Icon
		JPanel p = new JPanel();		
		p.setOpaque(true);
		p.setBackground(Css.NEWS_GRAY_DARK);
		
		icon.setBackground(Css.NEWS_GRAY_DARK);
		icon.setOpaque(true);
		Image placeholder = ResourceLoader.getImage(news.getImage(), ResourceLoader.NEWS_PLACEHOLDER_THUMBNAIL);
		int width = (int) (((double) THUMBNAIL_HEIGHT/(double) placeholder.getHeight(null)) * (double) placeholder.getWidth(null));
		
		if(width == 0)
			width = THUMBNAIL_WIDTH;
		
		Image newImage = placeholder.getScaledInstance(width, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
		icon.setIcon(new ImageIcon(newImage));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		p.add(icon);
		
		box.add(p);
	
		// Text
		JPanel text = new JPanel();
		text.setLayout(new GridLayout(2,2));
		text.setBackground(Css.NEWS_GRAY_LIGHT);
		text.setOpaque(true);
		
		String html = news.getTitle();
		lblTitle.setText(html);
		lblTitle.setBorder(Css.NEWS_BORDER);
		lblTitle.setFont(Css.FONT_14PT);
		lblTitle.setForeground(Css.COLOR_PAGE);
		lblTitle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTitle.addMouseListener(mouseClickHandler);
		text.add(lblTitle);
		
		text.add(new JLabel());
		
		lblDate.setText(news.getDate().format(FrontPage.FORMATTER));
		lblDate.setBorder(Css.NEWS_BORDER);
		lblDate.setFont(Css.FONT_10PT);
		text.add(lblDate);
		
		readMore.setFont(readMore.getFont().deriveFont(Font.BOLD));
		readMore.setHorizontalAlignment(SwingConstants.RIGHT);
		readMore.setBorder(Css.NEWS_BORDER);
		readMore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		readMore.addMouseListener(mouseClickHandler);
		text.add(readMore);
		
		box.add(text);
		
		add(box);
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}
}
