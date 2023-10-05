package gyk4j.wols.view.component.setup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import gyk4j.wols.io.HtmlEncoder;
import gyk4j.wols.view.component.Css;
import gyk4j.wols.view.component.TransparentPanel;

public class SetupTopStepper extends TransparentPanel {

	private static final long serialVersionUID = 1L;
	
	class Step {
		final String name;
		final char digit;
		final Color color;
		
		public Step(String name, char digit, Color color) {
			this.name = name;
			this.digit = digit;
			this.color = color;
		}
		
		public String getName() {
			return name;
		}
		
		public char getDigit() {
			return digit;
		}

		public Color getColor() {
			return color;
		}
	}
	
	private final Step[] STEPS = {
		new Step("Registration", (char) 0x278A, Css.STEPPER_STEP[0]), 
		new Step("Verification", (char) 0x278B, Css.STEPPER_STEP[1]), 
		new Step("Configuration", (char) 0x278C, Css.STEPPER_STEP[2]),	
	};
	
	private List<JComponent> children = new ArrayList<>();
	
	private int step = 0;
	
	protected static SetupTopStepper instance;
	
	public static SetupTopStepper getInstance() {
		if(instance == null) {
			instance = new SetupTopStepper();
		}
		return instance;
	}
	
	private SetupTopStepper() {
		super();
//		this.setBackground(Css.PAGE_BACKGROUND);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(Css.STEPPER_BORDER);
		
		for(int i=0; i < STEPS.length; i++) {
			JComponent step = createStep(i+1, STEPS[i].getName());
			add(step);
			getChildren().add(step);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		List<JComponent> children = getChildren();
		
//		for(int i=0; i < children.size(); i++) {
//			drawOutline(g, i, children.get(i));
//		}
		
		for(int i=1; i < children.size(); i++) {
			drawLine(g, i, children.get(i));
		}
	}
	
	/*
	private void drawOutline(Graphics g, int index, JComponent a) {
		Color defaultColor = g.getColor(); // Backup the current default color.
		
		final int radius = 32;
		
		int x = a.getX() + (a.getWidth() / 2) - 16;
		int y =	a.getY() + (a.getHeight() / 2) - 22;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(x, y, radius, radius);
		
		g.setColor(defaultColor); // Restore the default color
	}
	*/
	
	private void drawLine(Graphics g, int index, JComponent a) {
		final int height = 12; // line thickness
		final int spacing = 12;
		
		int x = a.getX() - (a.getWidth()/2) + spacing;
		int y = a.getY() + (a.getHeight()/2) - (height);
		int width = (int) ((0.9f) * a.getWidth());
		
		Color defaultColor = g.getColor(); // Backup the current default color.
		
		Color stepperColor;
		
		if(index <= getStep()) {
			stepperColor = STEPS[index-1].getColor();
		}
		else {
			stepperColor = Css.PROGRESSBAR_INACTIVE_FOREGROUND;
		}
		
		g.setColor(stepperColor);
		g.fillRect(x, y, width, height);
		
		g.setColor(defaultColor); // Restore the default color
	}
	
	
	
	private JLabel createStep(int n, String text) {
		JLabel l = new JLabel();
		
		formatStep(l, n, text);
		
		l.setHorizontalAlignment(SwingConstants.CENTER);
//		l.setBorder(BorderFactory.createLineBorder(Color.RED));
		l.setMinimumSize(new Dimension(220, 60));
		l.setPreferredSize(new Dimension(220, 60));
		return l;
	}
	
	private JLabel formatStep(JLabel l, int n, String text) {
		Color color = ((n-1) <= getStep()) ?
				STEPS[n-1].getColor(): Css.PROGRESSBAR_INACTIVE_FOREGROUND;
		
		String html = createHtml(color, n, text);
		l.setText(html);
		l.setFont(Css.FONT_14PT);
		return l;
	}
	
	private String createHtml(Color color, int n, String text) {		
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		
		// Digit
		char digit = STEPS[n-1].getDigit();
		sb.append("<center>");
		sb.append(HtmlEncoder.createColorText(Character.toString(digit), 48, color));
		sb.append("<br>");
		
		// Text
		sb.append(String.format("<font color=\"%s\">", HtmlEncoder.createColor(color)));
		String formatted = ((n - 1) == getStep()) ? "<b>" + text + "</b>" : text;
		sb.append(formatted);
		sb.append("</font>");
		
		sb.append("</center>");		
		
		sb.append("</html>");
		
		String html = sb.toString();
		return html;
	}

	/*
	public SetupTopStepper() {
		this.setBackground(StyleSheet.PAGE_BACKGROUND);

		this.setLayout(new GridLayout(1, 5));
		int y = 0;
		for (int x = 0; x < 5; x++) {
			JComponent c;

			int currentStep = Math.floorDiv(x, 2);
			if(x % 2 == 0) {
				c = createDot(currentStep);
			}
			else {
				c = createLine(currentStep);
			}

			add(c);
			children.add(c);
		}

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		System.out.println();
	}
	
	private JPanel createDot(int currentStep) {
		JPanel p = new JPanel();
		p.setBackground(StyleSheet.PAGE_BACKGROUND);
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		
		JLabel n = createStepNumber(new JLabel(), currentStep, false);
		JLabel c = createStepCaption(new JLabel(), currentStep, false);
		
		p.add(n);
		p.add(c);
		
		p.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		p.setSize(new Dimension(50, 50));
		return p;
	}
	
	private JPanel createLine(int currentStep) {
		JPanel p = new JPanel();
		p.setBackground(StyleSheet.PAGE_BACKGROUND);
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		
		JLabel l = createLine(new JLabel(), currentStep, false);
//		JLabel e = createEmpty(new JLabel());
		
		p.add(l);
//		p.add(e);
		p.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		p.setSize(new Dimension(50, 50));
		return p;
	}
	
	private JLabel createStepNumber(JLabel l, int stepNumber, boolean selected) {
		int col = (selected)? 1: 0;
		String text = Character.toString(CIRCLED_DIGITS[stepNumber][col]);
		l.setText(text);
		Color color = (selected)? StyleSheet.PROGRESSBAR_ACTIVE_FOREGROUND: StyleSheet.PROGRESSBAR_INACTIVE_FOREGROUND;
		l.setForeground(color);
		l.setFont(StyleSheet.FONT_STEPPER.deriveFont(48.0f));
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setBorder(BorderFactory.createLineBorder(Color.red));
		return l;
	}
	
	private JLabel createStepCaption(JLabel l, int stepNumber, boolean selected) {
		String text = STEPS[stepNumber];
		l.setText(text);
		int style = (selected)? Font.BOLD: Font.PLAIN;
		l.setFont(l.getFont().deriveFont(style));
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setBorder(BorderFactory.createLineBorder(Color.red));
		return l;
	}
	
	private JLabel createLine(JLabel l, int stepNumber, boolean selected) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 5; i++) {
			sb.append((char) 0x2584);
		}
		l.setText(sb.toString());
		Color color = (selected)? 
				StyleSheet.PROGRESSBAR_ACTIVE_FOREGROUND : 
			StyleSheet.PROGRESSBAR_INACTIVE_FOREGROUND;
		l.setForeground(color);
		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setVerticalAlignment(SwingConstants.CENTER);
		l.setBorder(BorderFactory.createLineBorder(Color.red));
		return l;
	}
	
	private JLabel createEmpty(JLabel l) {
		l.setText("");
		l.setBorder(BorderFactory.createLineBorder(Color.red));
		return l;
	}
	*/
	public void next() {
		if(getStep() < STEPS.length-2) {
			setStep(getStep()+1);
		}
	}
	
	public void previous() {
		if(getStep() > 0) {
			setStep(getStep()-1);
		}
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
		this.redraw();
	}

	public List<JComponent> getChildren() {
		return children;
	}

	public void setChildren(List<JComponent> children) {
		this.children = children;
	}
	
	private void redraw() {
		for(int i=0; i < getChildren().size(); i++) {
			JLabel l = (JLabel) getChildren().get(i);
			formatStep(l, i+1, STEPS[i].getName());
		}
		this.repaint();
	}
	
}
