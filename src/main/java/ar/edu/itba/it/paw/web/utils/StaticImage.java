package ar.edu.itba.it.paw.web.utils;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

public class StaticImage extends WebComponent {

	public StaticImage(String id, IModel<String> model) {
		super(id, model);
		add(new AttributeModifier("src", model));
		String imageUrl = (String) model.getObject();
		setVisible(!(imageUrl == null || imageUrl.equals("")));
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		checkComponentTag(tag, "img");
		tag.put("src", getDefaultModelObjectAsString());
	}

}
