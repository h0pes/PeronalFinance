import java.util.Properties;

import javax.swing.JFrame;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilCalendarModel;

/**
 * This class build a JDatePicker to display a calendar on the form of the
 * application.
 * 
 * @author Marco Canavese
 * @version v.1.0 - 19 dic 2015 13:27:51
 *
 */
public class JDatePicker extends JFrame
{
	public JDatePickerImpl datePicker;

	public JDatePicker()
	{
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		UtilCalendarModel model = new UtilCalendarModel();

		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	}

	/**
	 * It returns the JDatePickerImpl object
	 * 
	 * @return the JDatePickerImpl object
	 */
	public JDatePickerImpl getDatePicker()
	{
		return datePicker;
	}
}