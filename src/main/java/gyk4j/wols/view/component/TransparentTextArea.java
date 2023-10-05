package gyk4j.wols.view.component;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class TransparentTextArea extends JTextArea {
	
	private static final long serialVersionUID = 1L;

	public TransparentTextArea() {
		super();
		initialize();
	}

	public TransparentTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		initialize();
	}

	public TransparentTextArea(Document doc) {
		super(doc);
		initialize();
	}

	public TransparentTextArea(int rows, int columns) {
		super(rows, columns);
		initialize();
	}

	public TransparentTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
		initialize();
	}

	public TransparentTextArea(String text) {
		super(text);
		initialize();
	}

	protected void initialize() {
		this.setOpaque(false);
		this.setEditable(false);
		this.setOpaque(false);
		this.setColumns(45);
		this.setRows(2);
//		this.setBorder(BorderFactory.createEmptyBorder());
		this.setBorder(Css.DEBUG_BORDER_MAGENTA);
		this.setFocusable(false);
		this.setLineWrap(true);
		this.setFont(Css.FONT_14PT);
		this.setWrapStyleWord(true);
//		this.setMaximumSize(new Dimension(800, 50));
	}
}
