package ar.edu.itba.it.paw.web.users;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.publications.PublicationsPage;
import ar.edu.itba.it.paw.web.publications.UpdatePublicationPage;

public class LoggedPanel extends Panel {

	public LoggedPanel(String id) {
		super(id);

		add(new Link<Void>("create") {
			@Override
			public void onClick() {
				setResponsePage(new UpdatePublicationPage());
			}
		});

		add(new Link<Void>("list") {
			@Override
			public void onClick() {
				setResponsePage(new PublicationsPage(WicketSession.get()
						.getUser(), false));
			}
		});

		add(new Link<Void>("viewAgencies") {
			@Override
			public void onClick() {
				setResponsePage(new Agencies());
			}
		});

		add(new Link<Void>("logout") {
			@Override
			public void onClick() {
				WicketSession session = WicketSession.get();
				session.logout();
				setResponsePage(new HomePage());
			}
		});

	}

}
