package net.altkey12.wols.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.altkey12.wols.io.ResourceLoader;
import net.altkey12.wols.net.DiagnosticsMapKeyValue;

public class CollapsiblePane extends TransparentPanel {

	private static final long serialVersionUID = 1L;

	public static final Border border = BorderFactory.createCompoundBorder(
			BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY),
			BorderFactory.createEmptyBorder(12, 12, 12, 12));

	protected Component header;
	protected Component body;
	protected Component separator;

	protected List<PaneExpansionListener> listeners = new ArrayList<>();

	public CollapsiblePane(String title) {
		this(title, null);
	}

	public CollapsiblePane(String title, Component body) {
		super();
		setLayout(new BorderLayout());
		
		setName(title);
		setHeader(new CollapsiblePaneHeader(title));
		setBody((body == null) ? new CollapsiblePaneBody() : body);
		setSeparator(new CollapsiblePaneSeparator());

		add(getHeader(), BorderLayout.NORTH);
		add(getBody(), BorderLayout.CENTER);
		add(getSeparator(), BorderLayout.SOUTH);
	}

	public Component getHeader() {
		return header;
	}

	public void setHeader(Component header) {
		this.header = header;
	}

	public Component getBody() {
		return body;
	}

	public void setBody(Component body) {
		this.body = body;
	}

	public void setBody(List<DiagnosticsMapKeyValue> data) {
		remove(getBody());
		setBody(new CollapsiblePaneBody(data));
		add(getBody());
	}

	public Component getSeparator() {
		return separator;
	}

	public void setSeparator(Component separator) {
		this.separator = separator;
	}

	public boolean getState() {
		return body.isVisible();
	}

	public void setState(boolean state) {
		body.setVisible(state);
	}

	public boolean toggle(Object source) {
		boolean oldState = getState();
		boolean state = !oldState;

		PaneEventType type = (state) ? PaneEventType.EXPAND : PaneEventType.COLLAPSE;
		PaneEvent e = new PaneEvent(source, type, this);

		if (type == PaneEventType.COLLAPSE) {
			processEvent(e);
		}

		setState(state);

		if (type == PaneEventType.EXPAND) {
			processEvent(e);
		}
		
		invalidate();

		return state;
	}

	public void addPaneExpansionListener(PaneExpansionListener l) {
		listeners.add(l);
	}

	public void processEvent(PaneEvent e) {
		listeners.forEach(l -> {
			if (e.getType() == PaneEventType.EXPAND) {
				l.paneExpand(e);
			} else if (e.getType() == PaneEventType.COLLAPSE) {
				l.paneCollapse(e);
			}
		});
	}

	/**
	 * CollapsiblePaneHeader
	 * 
	 * @author USER
	 *
	 */

	class CollapsiblePaneHeader extends TransparentPanel {
		private static final long serialVersionUID = 1L;

		public final ImageIcon ICON_EXPAND = ResourceLoader.getImageIcon("pane_open.png");
		public final ImageIcon ICON_COLLAPSE = ResourceLoader.getImageIcon("pane_close.png");

		JLabel lblTitle;
		JToggleButton btnToggle;

		public CollapsiblePaneHeader() {
			super();

			lblTitle = new TransparentLabel();
			lblTitle.setFont(Css.FONT_LINE);
			lblTitle.setForeground(Css.COLOR_PANE_HEADER);
			lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

			btnToggle = new JToggleButton();
			btnToggle.setIcon(ICON_EXPAND);
			btnToggle.setSelectedIcon(ICON_COLLAPSE);
			btnToggle.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					toggle();
				}

			});
			btnToggle.setOpaque(false);
			btnToggle.setBorderPainted(false);
			btnToggle.setFocusPainted(false);
			btnToggle.setContentAreaFilled(false);
			btnToggle.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));

			this.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					toggle();
				}

			});

			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			add(lblTitle);
			add(Box.createHorizontalGlue());
			add(btnToggle);
		}

		public CollapsiblePaneHeader(String title) {
			this();
			setName(title);
			lblTitle.setText(title);
		}

		public void toggle() {
			CollapsiblePane pane = (CollapsiblePane) getParent();
			pane.toggle(this);
			btnToggle.setSelected(pane.getState());
		}

		public String getTitle() {
			return this.lblTitle.getText();
		}
	}

	/**
	 * CollapsiblePaneBody
	 * 
	 * @author USER
	 *
	 */

	class CollapsiblePaneBody extends TransparentPanel {

		private static final long serialVersionUID = 1L;

		public CollapsiblePaneBody() {
			this(createPlaceHolderData());
		}

		public CollapsiblePaneBody(List<DiagnosticsMapKeyValue> props) {
			super();
			/*
			int x = 0, y = 0;
			
			setBorder(Css.COLLAPSIBLE_PANE_BORDER);
			
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			
			ListIterator<DiagnosticsMapKeyValue> iterator = props.listIterator();
			while(iterator.hasNext()) {
				DiagnosticsMapKeyValue kvp = iterator.next();
				String key = kvp.getKey();
				String value = kvp.getValue();
				
				GridBagConstraints c = new GridBagConstraints();
				c.ipadx = Css.COLLAPSIBLE_PAD_X;
				c.ipady = Css.COLLAPSIBLE_PAD_Y;
				
				if (key != null && key.length() > 0) {
					c.gridx = x;
					c.gridy = y;
					c.gridwidth = 1;
					c.gridheight = 1;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.anchor = GridBagConstraints.FIRST_LINE_START;
					c.weightx = 0.5;
					
					add(new TransparentLabel(key + " : "), c);
					x++;
				}

				value = (value == null) ? "": value;
				c.gridx = x;
				c.gridy = y;
				c.gridwidth = 1;
				c.gridheight = 1;
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				c.weightx = 0.5;
				add(new TransparentLabel(value), c);
				x++;
				
				if (key == null || key.length() == 0) {
					c.gridx = x;
					c.gridy = y;
					c.gridwidth = 1;
					c.gridheight = 1;
					c.fill = GridBagConstraints.HORIZONTAL;
					c.anchor = GridBagConstraints.FIRST_LINE_START;
					c.weightx = 0.5;
					add(new TransparentLabel(""), c);
					x++;
				}
				
				y++;
				x = 0;
			}
			*/
			
			JPanel p = new TransparentPanel();
			GridLayout g = new GridLayout(0, 2);
			g.setHgap(4);
			g.setVgap(4);
			p.setLayout(g);

			props.forEach((kvp) -> {
				String key = kvp.getKey();
				String value = kvp.getValue();
				
				if (key != null && key.length() > 0) {
					p.add(new TransparentLabel(key + " : "));
				}

				value = (value == null) ? "": value;
				p.add(new TransparentLabel(value));
				
				if (key == null || key.length() == 0) {
					p.add(new TransparentLabel(""));
				}
			});

			setBorder(BorderFactory.createEmptyBorder(8, 32, 8, 32));
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
			add(p);
			add(Box.createHorizontalGlue());

		}
	}

	private static List<DiagnosticsMapKeyValue> createPlaceHolderData() {
		List<DiagnosticsMapKeyValue> data = new ArrayList<DiagnosticsMapKeyValue>();

//		for (int i = 1; i <= 10; i++) {
//			data.put("Key " + i, "Value " + i);
//		}

		return data;
	}

	/**
	 * CollapsiblePaneSeparator
	 * 
	 * @author USER
	 *
	 */
	class CollapsiblePaneSeparator extends TransparentPanel {

		private static final long serialVersionUID = 1L;

		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

		public CollapsiblePaneSeparator() {
			super();
			setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
//			setBorder(BorderFactory.createEmptyBorder());
//			separator.setMaximumSize(new Dimension(800, 2));
			add(separator);
		}
	}

	public enum PaneEventType {
		EXPAND, COLLAPSE
	}

	public static class PaneEvent extends EventObject {

		private static final long serialVersionUID = 1L;

		protected PaneEventType type;
		protected CollapsiblePane pane;

		public PaneEvent(Object source, PaneEventType type, CollapsiblePane pane) {
			super(source);
			this.type = type;
			this.pane = pane;
		}

		public PaneEventType getType() {
			return type;
		}

		public void setType(PaneEventType type) {
			this.type = type;
		}

		public CollapsiblePane getPane() {
			return pane;
		}

		public void setPane(CollapsiblePane pane) {
			this.pane = pane;
		}

	}

	public interface PaneExpansionListener {
		public void paneExpand(PaneEvent e);

		public void paneCollapse(PaneEvent e);
	}

	public class PaneAdapter implements PaneExpansionListener {

		@Override
		public void paneExpand(PaneEvent e) {
			System.out.println(e);
		}

		@Override
		public void paneCollapse(PaneEvent e) {
			System.out.println(e);
		}

	}

	/*
	 * private static void createAndShowGUI() { //Create and set up the window.
	 * JFrame frame = new JFrame("CollapsiblePaneDemo");
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * 
	 * Dimension size = new Dimension(400, 600); frame.setPreferredSize(size);
	 * frame.setMinimumSize(size); frame.setMaximumSize(size);
	 * 
	 * //Create and set up the content pane. // JComponent newContentPane = new
	 * CollapsiblePane(); // // newContentPane.setOpaque(true); //content panes must
	 * be opaque // frame.setContentPane(newContentPane);
	 * 
	 * JPanel p = new JPanel(); p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
	 * p.setOpaque(true); p.setBackground(Color.white);
	 * p.setForeground(Color.black); // frame.setContentPane(p);
	 * 
	 * p.add(new CollapsiblePane("Test Information")); p.add(new
	 * CollapsiblePane("Network Information")); p.add(new
	 * CollapsiblePane("Device Information")); p.add(new
	 * CollapsiblePane("Ping Test")); p.add(new CollapsiblePane("Diagnostics"));
	 * p.add(Box.createVerticalGlue());
	 * 
	 * JScrollPane s = new JScrollPane(); s.setViewportView(p);
	 * s.getVerticalScrollBar().setUnitIncrement(16);
	 * s.getHorizontalScrollBar().setUnitIncrement(16); // frame.setLayout(new
	 * BoxLayout(frame, BoxLayout.PAGE_AXIS)); frame.add(s);
	 * 
	 * //Display the window. frame.pack(); frame.setVisible(true);
	 * frame.setLocationRelativeTo(null); }
	 * 
	 * public static void main(String[] args) {
	 * javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() {
	 * try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
	 * catch (ClassNotFoundException | InstantiationException |
	 * IllegalAccessException | UnsupportedLookAndFeelException e) {
	 * e.printStackTrace(); } finally { createAndShowGUI(); } } }); }
	 */
}
