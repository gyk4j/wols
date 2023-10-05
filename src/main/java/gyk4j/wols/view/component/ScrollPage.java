package gyk4j.wols.view.component;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JScrollPane;

import gyk4j.wols.view.component.BoxUtils.LineSpacing;
import gyk4j.wols.view.component.CollapsiblePane.PaneEvent;
import gyk4j.wols.view.component.CollapsiblePane.PaneExpansionListener;
import gyk4j.wols.view.component.contactus.ProviderPanel;

public class ScrollPage extends TransparentPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected TransparentPanel scrollPanel; 
	protected JScrollPane scrollPane;

	public ScrollPage(String title) {
		super();
		
		scrollPanel = new TransparentPanel();
		scrollPanel.setOpaque(true);
		scrollPanel.setBackground(Css.PAGE_BACKGROUND);
		scrollPanel.setBorder(Css.STEPPER_BORDER);
		scrollPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		scrollPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		/*
		scrollPanel.addContainerListener(new ContainerAdapter() {

			@Override
			public void componentAdded(ContainerEvent e) {
				super.componentAdded(e);
				
				resizePanel(true, e.getChild().getPreferredSize());
				
				System.out.println(String.format("Added: %f %f", newSize.getWidth(), newSize.getHeight()));
			}

			@Override
			public void componentRemoved(ContainerEvent e) {
				super.componentRemoved(e);
				
				resizePanel(false, e.getChild().getPreferredSize());
				
				System.out.println(String.format("Removed: %f %f", newSize.getWidth(), newSize.getHeight()));
			}
			
		});
		*/
		scrollPane = new JScrollPane(scrollPanel);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		scrollPane.setPreferredSize(new Dimension(250, 145));
//		scrollPane.setMinimumSize(new Dimension(10, 10));
		scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // speed up the mouse wheel scrolling

		add(scrollPane);
		resizePanelRelative(true, scrollPane.getPreferredSize());
	}
	
	public void addComponent(ProviderPanel c) {
		scrollPanel.add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
		TransparentPanel tp = new TransparentPanel();
//		tp.setPreferredSize(new Dimension(570, 128));
//		tp.setMaximumSize(new Dimension(570, 128));
//		tp.setBorder(Css.DEBUG_BORDER_MAGENTA);
		tp.add(c);
		Box box = BoxUtils.createCenteredBox(tp);
		scrollPanel.add(box);
		scrollPanel.add(BoxUtils.createLineSpacing(LineSpacing.NORMAL));
	}
	
	public void addComponent(Component c) {
		if(c instanceof CollapsiblePane) {
			CollapsiblePane cp = (CollapsiblePane) c;
			cp.addPaneExpansionListener(new PaneExpansionListener() {

				@Override
				public void paneExpand(PaneEvent e) {
					resizePanelRelative(true, cp.getBody().getPreferredSize());
				}

				@Override
				public void paneCollapse(PaneEvent e) {
					resizePanelRelative(false, cp.getBody().getPreferredSize());
				}
				
			});
		}
		
		scrollPanel.add(c);
		resizePanelRelative(true, c.getPreferredSize());
	}
	
	public void resizePanelAbsolute(Dimension newSize) {
//		System.out.println(String.format("Setting to height %d", newSize.height));
//		scrollPanel.setMinimumSize(newSize);
		scrollPanel.setPreferredSize(newSize);
		scrollPanel.setMaximumSize(newSize);
		scrollPanel.invalidate();
		invalidate();
	}
	
	public void resizePanelRelative(boolean expand, Dimension d) {
		Dimension oldSize = scrollPanel.getPreferredSize();		 
		Dimension newSize = new Dimension();
		
		double newWidth = scrollPane.getWidth();
		double newHeight = oldSize.getHeight();
		
		// Expand / Open
		if(expand) {
			newHeight += d.getHeight();
		}
		// Collapse / Close
		else {
			newHeight -= d.getHeight();
		}
		
		newSize.setSize(newWidth, newHeight);

		resizePanelAbsolute(newSize);
	}
}
