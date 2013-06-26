package ar.edu.itba.it.paw.web.publications;

import org.apache.wicket.markup.html.link.Link;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.Status;
import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.web.WicketSession;

public class PublicationsItemPanel extends PublicationListItemPanel {
	
	private EntityModel<User> user;

	public PublicationsItemPanel(String id, Publication publication, User user) {
		super(id, publication);
		if (user == null)
			throw new NullPointerException();
		this.user = new EntityModel<User>(User.class, user.getId());
		buidEditLinks();
		buildStatusLinks();
	}

	private User user() {
		return user.getObject();
	}

	private void buidEditLinks() {
		WicketSession ws = WicketSession.get();
		Link<Void> editInformation = new Link<Void>("editInformation") {
			@Override
			public void onClick() {
				setResponsePage(new UpdatePublicationPage(publication()));
			}
		};
		add(editInformation);
		add(new Link<Void>("editPhotos") {
			@Override
			public void onClick() {
				setResponsePage(new UpdatePhotoPage(publication()));
			}
		});
		add(new Link<Void>("editRooms") {
			@Override
			public void onClick() {
				setResponsePage(new UpdateRoomsPage(publication()));
			}
		});

		User localUser = ws.getUser();
		
		if (!ws.existsUser() || ws.existsUser() && !localUser.equals(user())) {
			editInformation.setVisibilityAllowed(false);
		}

	}

	public void buildStatusLinks() {
		Link<Void> link1 = new Link<Void>("activate") {
			@Override
			public void onClick() {
				publication().modifyStatus(Status.ACTIVE);
			}
		};
		link1.setVisible(!publication().getStatus().equals(Status.ACTIVE));

		Link<Void> link2 = new Link<Void>("cancel") {
			@Override
			public void onClick() {
				publication().modifyStatus(Status.CANCELED);
			}
		};
		link2.setVisible(!publication().getStatus().equals(Status.CANCELED));

		Link<Void> link3 = new Link<Void>("reserve") {
			@Override
			public void onClick() {
				publication().modifyStatus(Status.RESERVED);
			}
		};
		link3.setVisible(!publication().getStatus().equals(Status.RESERVED));

		Link<Void> link4 = new Link<Void>("sell") {
			@Override
			public void onClick() {
				publication().modifyStatus(Status.SOLD);
			}
		};
		link4.setVisible(!publication().getStatus().equals(Status.SOLD));

		add(link1);
		add(link2);
		add(link3);
		add(link4);
	}
}
