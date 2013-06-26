package ar.edu.itba.it.paw.web.errorPages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class InternalErrorPage extends WebPage {

	private String error500 = "img/500Error.gif";

	public InternalErrorPage() {
		super();

	}
	
	protected void onInitialize() {
		super.onInitialize();
		add(new Link<Void>("home") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});
//		add(new Image("errorImg", new ContextRelativeResource(error500)));
		
	};
	
	public String getError500() {
		return error500;
	}
	
}
