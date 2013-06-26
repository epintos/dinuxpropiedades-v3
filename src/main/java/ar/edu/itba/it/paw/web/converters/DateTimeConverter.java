package ar.edu.itba.it.paw.web.converters;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTimeConverter implements IConverter<DateTime> {

	private final static org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat
			.shortDate().withLocale(new Locale("es", "AR"));

	public DateTime convertToObject(String value, Locale locale) {
		return DateTime.parse(value);
	}

	public String convertToString(DateTime value, Locale locale) {
		return DateTimeFormat.forPattern("dd/MM/yyyy - H:m:s").print(value);
	}

}
