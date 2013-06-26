package ar.edu.itba.it.paw.web.publications;

import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.photos.Photo;
import ar.edu.itba.it.paw.domain.publications.Comment;
import ar.edu.itba.it.paw.domain.publications.PropertyType;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.Room;
import ar.edu.itba.it.paw.domain.publications.RoomsEnum;
import ar.edu.itba.it.paw.domain.publications.Services;
import ar.edu.itba.it.paw.domain.publications.Status;
import ar.edu.itba.it.paw.domain.users.UserType;
import ar.edu.itba.it.paw.services.interfaces.EmailSender;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.command.validators.EmailValidator;
import ar.edu.itba.it.paw.web.users.PublicationAgencyPanel;
import ar.edu.itba.it.paw.web.users.UserInfoPage;
import ar.edu.itba.it.paw.web.utils.ImageResource;
import ar.edu.itba.it.paw.web.utils.PlaceholderBehavior;
import ar.edu.itba.it.paw.web.utils.StaticImage;

import com.google.code.jqwicket.ui.jcarousel.JCarouselWebMarkupContainer;

public class PublicationDetailPage extends BasePage {

	private EntityModel<Publication> publication;

	private transient String name;
	private transient String phone;
	private transient String email;
	private transient String comment;
	private WicketSession ws = WicketSession.get();
	private Form<PublicationDetailPage> commentForm;

	@SpringBean
	private EmailSender emailSender;

	public PublicationDetailPage(Publication publication) {
		if (publication == null)
			throw new NullPointerException();
		this.publication = new EntityModel<Publication>(Publication.class,
				publication.getId());
		setDefaultModel(new CompoundPropertyModel<Publication>(this.publication));

		add(new WebMarkupContainer("banner").setVisible(publication()
				.getStatus().equals(Status.RESERVED)));

		buildBasicInformation();
		buildMap();
		buildRooms();
		buildServices();
		buildLinks();
		buildCommentForm();

		if (ws.existsUser()
				&& ws.getUser().getId() == publication().getUser().getId()) {
			commentForm.setVisible(false);
		} else if (ws.existsUser()
				&& ws.getUser().getId() != publication().getUser().getId()) {
			name = ws.getUser().getName();
			email = ws.getUser().getEmail();
			phone = ws.getUser().getPhone();
		}

		buildPhotos();

	}

	private void buildCommentForm() {
		commentForm = new Form<PublicationDetailPage>("commentForm") {
			@Override
			protected void onSubmit() {
				Publication pub = publication();
				Comment c = new Comment(comment, email, phone, name);
				pub.addComment(c);
				emailSender.sendEmail(c, pub);
				comment = null;
				email = null;
				phone = null;
				name = null;
				setResponsePage(new UserInfoPage(publication()));
			}
		};
		TextField<String> name = new TextField<String>("name",
				new PropertyModel<String>(this, "name"));
		commentForm.add(name.setRequired(true).setVisible(!ws.existsUser()));
		commentForm.add(new ComponentFeedbackPanel("nameError", name));
		TextField<String> email = new TextField<String>("email",
				new PropertyModel<String>(this, "email"));
		commentForm.add(email.setRequired(true)
				.add(new PlaceholderBehavior(getString("emailExample")))
				.setVisible(!ws.existsUser()));
		email.add(new EmailValidator(this.email));
		commentForm.add(new ComponentFeedbackPanel("emailError", email));
		TextField<String> phone = new TextField<String>("phone",
				new PropertyModel<String>(this, "phone"));
		commentForm.add(phone.setRequired(true).setVisible(!ws.existsUser()));
		commentForm.add(new ComponentFeedbackPanel("phoneError", phone));
		commentForm.add(new TextArea<String>("comment",
				new PropertyModel<String>(this, "comment")));
		commentForm.add(new Button("send", new ResourceModel("send")));
		add(commentForm);

	}

	private void buildLinks() {
		Link<Void> publicatorPublications = new Link<Void>(
				"publicatorPublications") {
			@Override
			public void onClick() {
				setResponsePage(new PublicationsPage(publication().getUser(),
						true));

			}
		};
		Link<Void> editRoomsLink = new Link<Void>("editRooms") {
			@Override
			public void onClick() {
				setResponsePage(new UpdateRoomsPage(publication()));

			}
		};
		Link<Void> editPhotosLink = new Link<Void>("editPhotos") {
			@Override
			public void onClick() {
				setResponsePage(new UpdatePhotoPage(publication()));

			}

		};

		Link<Void> logHistory = new Link<Void>("logHistory") {
			@Override
			public void onClick() {
				setResponsePage(new ViewStatusChangeHistory(publication()));

			}
		};
		add(logHistory);
		add(editRoomsLink);
		add(editPhotosLink);
		add(publicatorPublications);
		if (ws.existsUser() && ws.getUser() == publication().getUser()) {
			publicatorPublications.setVisible(false);
		}
		if (!ws.existsUser() || ws.existsUser()
				&& publication().getUser() != ws.getUser()) {
			editRoomsLink.setVisible(false);
			editPhotosLink.setVisible(false);
			logHistory.setVisible(false);
		}

	}

	private void buildBasicInformation() {
		add(new Label("description"));
		add(new Label("operationType"));
		add(new Label("propertyType"));
		add(new Label("status"));
		add(new Label("street"));
		add(new Label("numbering"));
		add(new Label("neighbourhood"));
		add(new Label("coveredSurface"));
		add(new Label("uncoveredSurface"));
		add(new Label("age"));
		add(new Label("roomsQty"));
		add(new Label("price"));
		add(new Label("pricePerSquareMeter"));
		add(new Label("currency"));
		add(new Label("currency2", new PropertyModel<EntityModel<Publication>>(
				new EntityModel<Publication>(Publication.class, publication()),
				"currency")));
		Label floor = new Label("floor");
		add(floor);
		add(new Label("apartment"));
		floor.setVisible(publication().getPropertyType().equals(
				PropertyType.APARTMENT));
		PublicationAgencyPanel agencyPanel = new PublicationAgencyPanel(
				"publicationAgencyPanel", publication().getUser());
		if (!publication().getUser().getUserType().equals(UserType.AGENCY)) {
			agencyPanel.setVisible(false);
		}
		add(agencyPanel);
		add(new Label("visitCount"));

		if (publication().getMainPhoto() == null) {
			add(new Image("mainPhoto", new ContextRelativeResource(
					"img/defaultImg.jpg")));
		} else {
			add(new NonCachingImage("mainPhoto", new ImageResource(
					publication().getMainPhoto().getFile())));
		}

	}

	@Override
	protected void onBeforeRender() {
		super.onBeforeRender();
		publication().increaseVisitCount();
	}

	private void buildMap() {
		ExternalLink externalLink = new ExternalLink("mapLink",
				"http://mapof.it/" + publication().getStreet() + " "
						+ publication().getNumbering() + ", "
						+ publication().getNeighbourhood() + ", Argentina/");
		StaticImage img = new StaticImage("mapImage", new Model<String>(
				"http://maps.google.com/maps/api/staticmap?center="
						+ publication().getStreet() + " "
						+ publication().getNumbering() + ", "
						+ publication().getNeighbourhood()
						+ "&zoom=14&size=300x268&markers="
						+ publication().getStreet() + " "
						+ publication().getNumbering() + ", "
						+ publication().getNeighbourhood() + "&sensor=false"));
		externalLink.add(img);
		add(externalLink);

	}

	private Publication publication() {
		return publication.getObject();
	}

	private void buildRooms() {
		final IModel<List<Room>> roomModel = new LoadableDetachableModel<List<Room>>() {
			@Override
			protected List<Room> load() {
				return publication().getRooms();
			}
		};

		ListView<Room> roomsView = new PropertyListView<Room>("room", roomModel) {
			@Override
			protected void populateItem(ListItem<Room> item) {
				item.add(new Label("name", new PropertyModel<RoomsEnum>(item
						.getModel(), "type")));
				item.add(new Label("width", new PropertyModel<Room>(item
						.getModel(), "width")));
				item.add(new Label("length", new PropertyModel<Room>(item
						.getModel(), "length")));
			}
		};
		add(roomsView);

		Label emptyRooms = new Label("emptyRooms", Application.get()
				.getResourceSettings().getLocalizer()
				.getString("emptyRooms", this));
		add(emptyRooms);
		if (roomModel.getObject().size() == 0) {
			roomsView.setVisible(false);
		} else {
			emptyRooms.setVisible(false);
		}
	}

	private void buildServices() {
		final IModel<List<Services>> serviceModel = new LoadableDetachableModel<List<Services>>() {
			@Override
			protected List<Services> load() {
				return publication().getServices();
			}
		};

		ListView<Services> servicesView = new PropertyListView<Services>(
				"service", serviceModel) {
			@Override
			protected void populateItem(ListItem<Services> item) {
				item.add(new Label("name", item.getModel()));
			}
		};
		add(servicesView);

		Label emptyServices = new Label("emptyServices", Application.get()
				.getResourceSettings().getLocalizer()
				.getString("emptyServices", this));
		add(emptyServices);
		if (serviceModel.getObject().size() == 0) {
			servicesView.setVisible(false);
		} else {
			emptyServices.setVisible(false);
		}
	}

	private void buildPhotos() {
		final IModel<List<Photo>> photosModel = new LoadableDetachableModel<List<Photo>>() {
			@Override
			protected List<Photo> load() {
				return publication().getPhotos();
			}
		};

		ListView<Photo> photosView = new PropertyListView<Photo>("photos",
				photosModel) {
			@Override
			protected void populateItem(final ListItem<Photo> item) {
				NonCachingImage image = new NonCachingImage("photo",
						new ImageResource(item.getModelObject().getFile()));
				item.add(image);

			}
		};
		JCarouselWebMarkupContainer carousel = new JCarouselWebMarkupContainer(
				"myCarousel");

		carousel.add(photosView);
		add(carousel);

		Label empty = new Label("emptyPhotos", Application.get()
				.getResourceSettings().getLocalizer()
				.getString("emptyPhotos", this));
		add(empty);
		if (photosModel.getObject().size() == 0) {
			photosView.setVisible(false);
			carousel.setVisible(false);
		} else
			empty.setVisible(false);
	}

}
