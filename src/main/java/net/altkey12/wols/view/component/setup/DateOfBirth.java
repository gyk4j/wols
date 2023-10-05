package net.altkey12.wols.view.component.setup;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDateTime;

import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import net.altkey12.wols.model.setup.SetupRegistrationModel;
import net.altkey12.wols.view.component.Css;
import net.altkey12.wols.view.component.TransparentPanel;

public class DateOfBirth extends TransparentPanel {

	private static final long serialVersionUID = 1L;

	// JSpinner spnDob = new JSpinner(); //DateOfBirth();
	JSpinner spnDay = new JSpinner();
	JLabel lblSeparator1 = new JLabel(" / ");
	JSpinner spnMonth = new JSpinner();
	JLabel lblSeparator2 = new JLabel(" / ");
	JSpinner spnYear = new JSpinner();
	
	final SetupRegistrationModel model;

	public DateOfBirth(SetupRegistrationModel model) {
		super();
		this.model = model;
		
		BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		this.setLayout(layout);
		
		FocusAdapter focusAdapter = new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);
				Object o = e.getSource();
				
				if(o instanceof JFormattedTextField) {
					JFormattedTextField txt = (JFormattedTextField) o;
					SwingUtilities.invokeLater(new Runnable() {
			            @Override
			            public void run() {
			            	txt.selectAll();
			            }
			        });
				}
			}
			
		};

//		spnDay = createSpinner(1, 31, 9);
		spnDay.setModel(model.getDobDay());
		spnDay.setEditor(new JSpinner.NumberEditor(spnDay, "##"));
		getTextField(spnDay).setColumns(2);
		getTextField(spnDay).addFocusListener(focusAdapter);
		getTextField(spnDay).setFont(Css.FONT_14PT);
		
		lblSeparator1.setFont(Css.FONT_14PT);

//		spnMonth = createSpinner(1, 12, 8);
		spnMonth.setModel(model.getDobMonth());
		spnMonth.setEditor(new JSpinner.NumberEditor(spnMonth, "##"));
		getTextField(spnMonth).setColumns(2);
		getTextField(spnMonth).addFocusListener(focusAdapter);
		getTextField(spnMonth).setFont(Css.FONT_14PT);
		
		lblSeparator2.setFont(Css.FONT_14PT);
		
//		spnYear = createSpinner(1900, LocalDateTime.now().getYear(), 1965);
		spnYear.setModel(model.getDobYear());
		spnYear.setEditor(new JSpinner.NumberEditor(spnYear, "####"));
		getTextField(spnYear).setColumns(4);
		getTextField(spnYear).addFocusListener(focusAdapter);
		getTextField(spnYear).setFont(Css.FONT_14PT);

		spnDay.setToolTipText("Day of month e.g. between 1 and 31");
		spnMonth.setToolTipText("Month e.g. between 1 and 12");
		spnYear.setToolTipText("Year e.g. between " + 
				((SpinnerNumberModel) model.getDobYear()).getMinimum() + " and " + 
				((SpinnerNumberModel) model.getDobYear()).getMaximum());

		spnDay.setInputVerifier(new InputVerifier() {

			@Override
			public boolean verify(JComponent input) {
				boolean verified = false;

				JTextField textbox = (JTextField) input;
				String text = textbox.getText();

				try {
					int value = Integer.parseInt(text);
					if (value >= 1 && value <= 31) {
						verified = true;
					}
					System.out.println(value);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				return verified;
			}

		});

		add(spnDay);
		add(lblSeparator1);
		add(spnMonth);
		add(lblSeparator2);
		add(spnYear);
		
		// Date of Birth
		
//		Calendar calendar = Calendar.getInstance();
//		Date initDate = calendar.getTime();
//		calendar.add(Calendar.YEAR, -100);
//		Date earliestDate = calendar.getTime();
//
//		calendar.add(Calendar.YEAR, 100);
//		Date latestDate = calendar.getTime();
//
//		SpinnerDateModel model = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
//		spnDob.setModel(model);
		
		/*
		 * private JDatePickerImpl createDatePicker() { Properties p = new Properties();
		 * p.put("text.today", "Today"); p.put("text.month", "Month");
		 * p.put("text.year", "Year");
		 * 
		 * DateLabelFormatter formatter = new DateLabelFormatter();
		 * 
		 * UtilDateModel model = new UtilDateModel(); int year =
		 * Calendar.getInstance().get(Calendar.YEAR) - 42; model.setDate(year, 0, 1);
		 * JDatePanelImpl datePanel = new JDatePanelImpl(model, p); JDatePickerImpl
		 * datePicker = new JDatePickerImpl(datePanel, formatter); return datePicker; }
		 */
		 
	}

	protected JSpinner createSpinner(int start, int end, int value) {
		int min = 1900;
		int max = LocalDateTime.now().getYear();
		int initial = min + ((max - min) / 2);
		SpinnerNumberModel model = new SpinnerNumberModel(initial, min, max, 1);
		JSpinner spinner = new JSpinner(model);
		spinner.setValue(value);
		return spinner;
	}
	
	public JFormattedTextField getTextField(JSpinner spinner) {
	    JComponent editor = spinner.getEditor();
	    if (editor instanceof JSpinner.DefaultEditor) {
	        return ((JSpinner.DefaultEditor)editor).getTextField();
	    } else {
	        System.err.println("Unexpected editor type: "
	                           + spinner.getEditor().getClass()
	                           + " isn't a descendant of DefaultEditor");
	        return null;
	    }
	}

	public Component getDay() {
		return spnDay;
	}

	public Component getMonth() {
		return spnMonth;
	}

	public Component getYear() {
		return spnYear;
	}
	
	class JTextFieldLimit extends PlainDocument {

		private static final long serialVersionUID = 1L;
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

}
