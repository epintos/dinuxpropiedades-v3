package ar.edu.itba.it.paw.web.base;

import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.common.AuthenticationErrorPage;

public abstract class SecuredPage extends BasePage {

	public SecuredPage() {
		WicketSession session = getDinuxPropiedadesSession();
		if (!session.isSignedIn()) {
			redirectToInterceptPage(new HomePage());
		}

	}

	protected WicketSession getDinuxPropiedadesSession() {
		return (WicketSession) getSession();
	}

}