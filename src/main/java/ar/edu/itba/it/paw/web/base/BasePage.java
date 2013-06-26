package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.publicity.Publicity;
import ar.edu.itba.it.paw.domain.publicity.PublicityRepo;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.users.LogInPanel;
import ar.edu.itba.it.paw.web.users.LoggedPanel;
import ar.edu.itba.it.paw.web.utils.StaticImage;

public class BasePage extends WebPage {

	@SpringBean
	private PublicityRepo publicityRepo;
	
	public BasePage() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		WicketSession session = WicketSession.get();

		Panel login = null;
		if (session.existsUser()) {
			login = new LoggedPanel("headerPanel");

		} else {
			login = new LogInPanel("headerPanel");
		}
		
		add(login);

		add(new Link<Void>("home") {
			@Override
			public void onClick() {
				setResponsePage(getApplication().getHomePage());
			}
		});

		generatePublicity();
	}

	private void generatePublicity() {
		Publicity publicity = publicityRepo.getRandomPublicity();

		if (publicity != null) {
			add(new StaticImage("advert", new Model<String>(publicity.getUrl())));
		}else{
			add(new StaticImage("advert", new Model<String>("")).setVisible(false));
		}
	}
	
}
