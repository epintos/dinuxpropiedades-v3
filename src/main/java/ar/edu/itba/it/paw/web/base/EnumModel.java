package ar.edu.itba.it.paw.web.base;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

public class EnumModel<T extends Enum<?>> extends LoadableDetachableModel<List<? extends T>> {

	public static <T extends Enum<?>> EnumModel<T> create(Class<T> clazz) {
		return new EnumModel<T>(clazz);
	}
	
	private Class<T> clazz;
	
	private EnumModel(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	protected List<? extends T> load() {
		return Arrays.asList(clazz.getEnumConstants());
	}
}
