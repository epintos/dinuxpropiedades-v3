package ar.edu.itba.it.paw.web.converters;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.util.convert.IConverter;

public class EnumConverter<T extends Enum<?>> implements IConverter<T> {

	public static <T extends Enum<?>> EnumConverter<T> create(Class<T> clazz) {
		return new EnumConverter<T>(clazz);
	}
	
	private Class<T> clazz;

	private EnumConverter(Class<T> clazz) {
		this.clazz = clazz;
	}

	public T convertToObject(String value, Locale locale) {
		for (T instance : clazz.getEnumConstants()) {
			if (instance.name().equals(value)) {
				return instance;
			}
		}
		return null;
	}

	public String convertToString(T value, Locale locale) {
		return Application.get().getResourceSettings().getLocalizer()
				.getString(clazz.getSimpleName() + "." + value.name(), null);
	}
}