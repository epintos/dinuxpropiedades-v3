package ar.edu.itba.it.paw.web.common;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.ContextRelativeResource;

import ar.edu.itba.it.paw.web.base.BasePage;

public class AuthenticationErrorPage extends BasePage {

	private static String error401 = "img/401error.gif";

	public AuthenticationErrorPage() {
		super();

		add(new Image("errorImg", new ContextRelativeResource(error401)));
	}

}
