package ar.edu.itba.it.paw.web.publications;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.PropertyType;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.Status;
import ar.edu.itba.it.paw.web.utils.ImageResource;

public class PublicationListItemPanel extends Panel {
	private EntityModel<Publication> publication;

	public PublicationListItemPanel(String id, Publication publication) {
		super(id);
		if (publication == null)
			throw new NullPointerException();
		this.publication = new EntityModel<Publication>(Publication.class,
				publication.getId());
		setDefaultModel(new CompoundPropertyModel<Publication>(this.publication));
		add(new Label("operationType"));
		add(new Label("propertyType"));
		add(new Label("status"));
		add(new Label("street"));
		add(new Label("numbering"));
		add(new Label("neighbourhood"));
		add(new Label("price"));
		add(new Label("pricePerSquareMeter"));
		add(new Label("currency"));
		add(new Label("currency2", new PropertyModel<EntityModel<Publication>>(
				new EntityModel<Publication>(Publication.class, publication),
				"currency")));
		add(new Link<Void>("moreInformation") {
			@Override
			public void onClick() {
				setResponsePage(new PublicationDetailPage(publication()));
			}
		});
		add(new WebMarkupContainer("banner").setVisible(publication()
				.getStatus().equals(Status.RESERVED)));
		Label floor = new Label("floor");
		Label apartment = new Label("apartment");
		add(floor);
		add(apartment);
		floor.setVisible(publication().getPropertyType().equals(
				PropertyType.APARTMENT));

		if (publication().getMainPhoto() == null) {
			add(new Image("mainPhoto", new ContextRelativeResource(
					"img/defaultImg.jpg")));
		} else {
			add(new NonCachingImage("mainPhoto", new ImageResource(
					publication().getMainPhoto().getFile())));
		}
	}

	protected Publication publication() {
		return publication.getObject();
	}
}
