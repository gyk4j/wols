package gyk4j.wols.view.component.news;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.plaf.LayerUI;

import gyk4j.wols.beans.updatecontent.News;
import gyk4j.wols.controller.Controller;
import gyk4j.wols.view.component.BoxUtils;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TransparentPanel;
import gyk4j.wols.view.component.BoxUtils.LineSpacing;

public class FrontPage extends TransparentPanel {

	private static final long serialVersionUID = 1L;

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");
	
	LocalDateTime lastViewed;

	Jumbotron jumbotron;
	ArticlePanel articlePanel;

	public FrontPage() {
		super();

		setBorder(Css.STEPPER_BORDER);		
		refresh();
	}
	
	public void refresh() {
		lastViewed = Controller.getInstance().loadNewsLastViewed();
		
		this.removeAll();
		
		News[] news = Controller.getInstance().getNews();

		// add jumotron
		jumbotron = new Jumbotron(news[0]);
		LayerUI<JComponent> layerUI = new NewBadgeLayerUI(lastViewed);
		JLayer<JComponent> jumbotronNew = new JLayer<JComponent>(jumbotron, layerUI);
		
		if(news[0].getDate().isAfter(lastViewed.toLocalDate())) {
			add(jumbotronNew);
		}
		else {
			add(jumbotron);
		}

		add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));

		// add elements
		articlePanel = new ArticlePanel(
				Arrays.copyOfRange(news, 1, news.length), 
				(NewBadgeLayerUI) layerUI);
		add(articlePanel);
		repaint();
		
		Controller.getInstance().saveNewsLastViewed();
	}

	public Jumbotron getJumbotron() {
		return jumbotron;
	}

	public void setJumbotron(Jumbotron jumbotron) {
		this.jumbotron = jumbotron;
	}

	public ArticlePanel getArticlePanel() {
		return articlePanel;
	}

	public void setArticlePanel(ArticlePanel articlePanel) {
		this.articlePanel = articlePanel;
	}
}
