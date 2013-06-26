package ar.edu.itba.it.paw.web.users;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.domain.users.UserType;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.publications.PublicationDetailPage;
import ar.edu.itba.it.paw.web.utils.ImageResource;

public class UserInfoPage extends BasePage {

	private EntityModel<User> userModel;
	private EntityModel<Publication> publicationModel;

	public UserInfoPage(Publication publication) {
		if (publication == null)
			throw new NullPointerException();
		this.publicationModel = new EntityModel<Publication>(Publication.class, publication);
		this.userModel = new EntityModel<User>(User.class, publication().getUser());
		setDefaultModel(new CompoundPropertyModel<User>(userModel));
		addPhoto();
		addInformation();

		add(new Link<Void>("back") {

			@Override
			public void onClick() {
				setResponsePage(new PublicationDetailPage(publication()));

			}
		});
	}

	private void addInformation() {
		add(new Label("email"));
		add(new Label("phone"));

	}

	private void addPhoto() {
		if (user().getUserType().equals(UserType.AGENCY)) {
			if (user().getPhoto() != null) {
				add(new NonCachingImage("photo", new ImageResource(user()
						.getPhoto().getFile())));
			} else {
				add(new Image("photo", new ContextRelativeResource(
						"img/agency.jpg")));
			}
		} else {
			add(new Image("photo", new ContextRelativeResource("img/User.png")));
		}

	}

	private User user() {
		return userModel.getObject();
	}

	private Publication publication() {
		return publicationModel.getObject();
	}
}
