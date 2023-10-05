package gyk4j.wols.view.component.news;

import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.JLayer;

import gyk4j.wols.beans.updatecontent.News;
import gyk4j.wols.view.component.TransparentPanel;

public class ArticlePanel extends TransparentPanel {
	
	private static final long serialVersionUID = 1L;
	
	public static final int COLUMN_NUMBER = 2;
	
	Article[] articles;

	public ArticlePanel(News[] news, NewBadgeLayerUI layerUI) {
//		int rows = 0;
//		GridLayout g = new GridLayout(rows, COLUMN_NUMBER);
//		g.setHgap(8);
//		g.setVgap(8);
//		setLayout(g);
		setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		// Skip news[0] for Jumbotron
		articles = new Article[news.length];
		for (int i = 0; i < news.length; i++) {
			articles[i] = new Article(news[i]);
			JLayer<JComponent> articleNew = new JLayer<JComponent>(articles[i], layerUI);
			if(news[i].getDate().isAfter(layerUI.getLastViewed().toLocalDate())) {
				add(articleNew);
			}
			else {
				add(articles[i]);
			}
		}
	}
}
