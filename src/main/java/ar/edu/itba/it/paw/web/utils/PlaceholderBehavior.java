package ar.edu.itba.it.paw.web.utils;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

public class PlaceholderBehavior extends Behavior {

	private final String placeholder;

	public PlaceholderBehavior(String placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("placeholder", this.placeholder);
	}

}
