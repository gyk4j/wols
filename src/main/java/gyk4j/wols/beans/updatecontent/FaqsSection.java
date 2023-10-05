package gyk4j.wols.beans.updatecontent;

public class FaqsSection {

	private String name;
	private QnA[] questions;
	
	public FaqsSection(String name, QnA[] questions) {
		this.name = name;
		this.questions = questions;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QnA[] getQuestions() {
		return questions;
	}

	public void setQuestions(QnA[] questions) {
		this.questions = questions;
	}

}
