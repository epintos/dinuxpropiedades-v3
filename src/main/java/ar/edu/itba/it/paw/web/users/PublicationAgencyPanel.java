package ar.edu.itba.it.paw.web.users;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.web.utils.ImageResource;

public class PublicationAgencyPanel extends Panel {
	public PublicationAgencyPanel(String id, User user) {
		super(id);
		if (user.getPhoto() == null) {
			add(new Image("mainPhoto", new ContextRelativeResource(
					"img/User.png")));
		} else {
			if(user.getPhoto() == null)
			add(new Image("mainPhoto", new ContextRelativeResource(
					"img/User.png")));
			else {
				add(new NonCachingImage("mainPhoto", new ImageResource(
						user.getPhoto().getFile())));
			}
		}

		add(new Label("name", user.getName()));

	}
}
