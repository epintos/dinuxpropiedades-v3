package ar.edu.itba.it.paw.web.errorPages;

import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.base.SearchPanel;

public class SessionExpiredPage extends BasePage {
	public SessionExpiredPage() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new SearchPanel("searchPanel"));
		add(new Link<Void>("homeLink") {
			@Override
			public void onClick() {
				setResponsePage(HomePage.class);
			}
		});
	}
}
