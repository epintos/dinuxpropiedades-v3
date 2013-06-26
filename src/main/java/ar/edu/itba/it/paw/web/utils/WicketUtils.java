package ar.edu.itba.it.paw.web.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.model.IModel;

import ar.edu.itba.it.paw.domain.EntityModel;

public class WicketUtils {

	@SuppressWarnings("unchecked")
	public static <T> Iterator<IModel<T>> getIteratorFor(List<T> list) {
		List<IModel<T>> result = new ArrayList<IModel<T>>();
		for(T elem: list) {
			result.add(new EntityModel<T>((Class<T>)elem.getClass(), elem));
		}
		return result.iterator();
	}
	
}
