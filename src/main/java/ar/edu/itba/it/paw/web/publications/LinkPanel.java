package ar.edu.itba.it.paw.web.publications;


import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.it.paw.domain.publications.Publication;

public class LinkPanel extends Panel {
	
	public LinkPanel(String id, final IModel<Publication> publication, String messageKey) {
		super(id);
		add(new Link<Void>("link") {
			@Override
			public void onClick() {
				setResponsePage(new PublicationDetailPage(publication.getObject()));
			}
		}.add(new Label("label", new StringResourceModel(messageKey, null))));
	}

}
