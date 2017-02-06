
/**
 * Provide format utilities for Date and Calendar
 * 
 * @version: v.1.0 - 19 dic 2015 12:03:52 
 * @author:  Marco Canavese
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter
{

	private String datePattern = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	@Override
	public Object stringToValue(String text) throws ParseException
	{
		return dateFormatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException
	{
		if (value != null)
		{
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}

		return "";
	}

}