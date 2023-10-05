package gyk4j.wols.view.component.news;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import gyk4j.wols.beans.updatecontent.News;
import gyk4j.wols.io.HtmlEncoder;
import gyk4j.wols.io.ResourceLoader;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.OutlineButton;
import gyk4j.wols.view.component.TransparentLabel;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.BoxUtils.LineSpacing;
import gyk4j.wols.view.component.main.MainPanel;

public class ArticlePage extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	protected static final int THUMBNAIL_WIDTH = 480;
	protected static final int THUMBNAIL_HEIGHT = 240;
	
	protected News news;
	
	JButton btnBack = new OutlineButton("< Back", Color.BLACK, false);
	Color background = Css.NEWS_GRAY_LIGHT;
	
	JLabel icon = new JLabel();
	JLabel lblTitle = new TransparentLabel();
	JLabel lblDate = new TransparentLabel();
	JLabel lblContent = new JLabel();

	public ArticlePage() {
		super();
		
		setBorder(Css.STEPPER_BORDER);
		setBackground(background);
		setOpaque(true);
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComponent page = MainPanel.getInstance().getActiveTabPage();
				if(page instanceof NewsPage) {
					((NewsPage) page).getViews().previous();
				}
			}
			
		});
		
		add(BoxUtils.createLeftBox(btnBack));
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		
		// Icon
		JPanel p = new JPanel();
		p.setOpaque(true);
		p.setBackground(Css.NEWS_GRAY_DARK);
		
		icon.setBackground(Css.NEWS_GRAY_DARK);
		icon.setOpaque(true);
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		
		p.add(icon);
		
		add(p);
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		
		// Title Link
		lblTitle.setFont(Css.FONT_14PT);
		lblTitle.setForeground(Css.COLOR_PAGE);
		
		add(BoxUtils.createLeftBox(lblTitle));
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		
		// Date
		lblDate.setFont(Css.FONT_12PT);
		
		add(BoxUtils.createLeftBox(lblDate));
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		
		// Content
		lblContent.setHorizontalAlignment(SwingConstants.LEFT);
		lblContent.setFont(Css.FONT_14PT);
		JPanel c = new JPanel();
		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		c.setOpaque(true);
		c.setBackground(Css.PAGE_BACKGROUND);
		c.add(BoxUtils.createLeftBox(lblContent));
		add(c);
		add(BoxUtils.createLineSpacing(LineSpacing.THIN));
		
		add(new JSeparator());
		add(Box.createGlue());
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
		updateNews();
	}
	
	private void updateNews() {
		lblTitle.setText(HtmlEncoder.createText(news.getTitle()));
		lblDate.setText(news.getDate().format(FrontPage.FORMATTER));
		lblContent.setText(HtmlEncoder.createText(news.getContent()));
		Image placeholder = ResourceLoader.getImage(news.getImage(), ResourceLoader.NEWS_PLACEHOLDER_THUMBNAIL);
		int width = (int) (((double) THUMBNAIL_HEIGHT/(double) placeholder.getHeight(null)) * (double) placeholder.getWidth(null));
		
		if(width == 0)
			width = THUMBNAIL_WIDTH;
		
		Image newImage = placeholder.getScaledInstance(width, THUMBNAIL_HEIGHT, Image.SCALE_DEFAULT);
		icon.setIcon(new ImageIcon(newImage));
	}

}
