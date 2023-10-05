package net.altkey12.wols.beans.updatecontent;

import java.time.LocalDate;

public class News {
	protected String image;
	protected LocalDate date;
	protected String title;
	protected String content;

	public News(String image, LocalDate date, String title, String content) {
		this.image = image;
		this.date = date;
		this.title = title;
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
