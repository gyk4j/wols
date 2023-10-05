package net.altkey12.wols.view.component.faqs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.altkey12.wols.view.component.BoxUtils;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.OutlineButton;
import net.altkey12.wols.view.component.TitledPage;
import net.altkey12.wols.view.component.TransparentLabel;
import net.altkey12.wols.view.component.BoxUtils.LineSpacing;

public class FaqsQnAPage extends TitledPage {
	
	private static final long serialVersionUID = 1L;
	
	private String question;
	private String answer;
	
	private JButton btnBack = new OutlineButton("< Back", Color.BLACK, false);
	private JLabel lblQuestion = new TransparentLabel();
	private JLabel lblAnswer = new TransparentLabel();

	public FaqsQnAPage(String title) {
		super(null);
		
		setBorder(Css.STEPPER_BORDER);
		
		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FaqsPage.getCards().previous();
			}
			
		});
		
		lblQuestion.setFont(Css.FONT_14PT.deriveFont(Font.BOLD));
		
		lblAnswer.setFont(Css.FONT_14PT);
		
		add(BoxUtils.createLeftBox(btnBack));
		add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
		add(BoxUtils.createLeftBox(lblQuestion));
		add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
		add(BoxUtils.createLeftBox(lblAnswer));
		add(Box.createVerticalGlue());
		
//		addComponent(BoxUtils.createLeftBox(btnBack));
//		addComponent(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
//		addComponent(BoxUtils.createLeftBox(lblQuestion));
//		addComponent(BoxUtils.createLeftBox(lblAnswer));
//		addComponent(Box.createVerticalGlue());
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
		lblQuestion.setText("<html><p>Q: " + question + "</p></html>");
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
		lblAnswer.setText("<html><p>A: " + answer + "</p></html>");
	}
}
