package net.altkey12.wols.model;

import javax.swing.BoundedRangeModel;
import javax.swing.ButtonModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SingleSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.Document;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeSelectionModel;

public class BindFactory {
	
	public static Binding create(JButton component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JToggleButton component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JCheckBox component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JRadioButton component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JMenu component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JMenuItem component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JCheckBoxMenuItem component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JRadioButtonMenuItem component, ButtonModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static <T> Binding create(JComboBox<T> component, ComboBoxModel<T> model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JProgressBar component, BoundedRangeModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JScrollBar component, BoundedRangeModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JSlider component, BoundedRangeModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTabbedPane component, SingleSelectionModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static <T> Binding create(JList<T> component, ListModel<T> model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static <T> Binding create(JList<T> component, ListSelectionModel model) {
		component.setSelectionModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTable component, TableModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTable component, TableColumnModel model) {
		component.setColumnModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTree component, TreeModel model) {
		component.setModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTree component, TreeSelectionModel model) {
		component.setSelectionModel(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JEditorPane component, Document model) {
		component.setDocument(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTextPane component, Document model) {
		component.setDocument(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTextArea component, Document model) {
		component.setDocument(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JTextField component, Document model) {
		component.setDocument(model);
		return new Binding(component, model);
	}
	
	public static Binding create(JPasswordField component, Document model) {
		component.setDocument(model);
		return new Binding(component, model);
	}
}
