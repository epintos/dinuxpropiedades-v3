package ar.edu.itba.it.paw.web.publications;

import java.util.Iterator;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.Room;
import ar.edu.itba.it.paw.domain.publications.RoomsEnum;
import ar.edu.itba.it.paw.web.base.EnumModel;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.utils.PlaceholderBehavior;
import ar.edu.itba.it.paw.web.utils.WicketUtils;

public class UpdateRoomsPage extends SecuredPage {

	private EntityModel<Publication> publication;

	private transient RoomsEnum roomType;
	private transient Integer width;
	private transient Integer length;

	public UpdateRoomsPage(Publication publication) {
		if (publication == null)
			throw new NullPointerException();
		this.publication = new EntityModel<Publication>(Publication.class,
				publication.getId());
		buildAddRoomForm();
		buildDeleteRooms();
		buildLinks();
	}

	private void buildLinks() {
		Link<Void> toPublication = new Link<Void>("toPublication") {
			public void onClick() {
				setResponsePage(new PublicationDetailPage(publication()));

			};
		};
		add(toPublication);

	}

	void buildAddRoomForm() {
		Form<UpdateRoomsPage> form = new Form<UpdateRoomsPage>("addRoomForm") {

			@Override
			protected void onSubmit() {
				publication().addRoom(new Room(roomType, width, length));
				setResponsePage(new PublicationDetailPage(publication()));
			}
		};

		DropDownChoice<RoomsEnum> roomsDrop = new DropDownChoice<RoomsEnum>(
				"roomType", new PropertyModel<RoomsEnum>(this, "roomType"),
				EnumModel.create(RoomsEnum.class));
		form.add(roomsDrop.setRequired(true));
		form.add(new ComponentFeedbackPanel("roomTypeError", roomsDrop));

		TextField<Integer> length = new TextField<Integer>("length",
				new PropertyModel<Integer>(this, "length"));
		form.add(length.setRequired(true).add(new PlaceholderBehavior(getString("length"))));
		form.add(new ComponentFeedbackPanel("lengthError", length));
		TextField<Integer> width = new TextField<Integer>("width",
				new PropertyModel<Integer>(this, "width"));
		form.add(width.setRequired(true).add(new PlaceholderBehavior(getString("width"))));
		form.add(new ComponentFeedbackPanel("widthError", width));
		form.add(new Button("add", new ResourceModel("add")));

		add(form);

	}

	private void buildDeleteRooms() {
		RefreshingView<Room> rv = new RefreshingView<Room>("room") {

			@Override
			protected Iterator<IModel<Room>> getItemModels() {
				return WicketUtils.getIteratorFor(publication().getRooms());
			}

			@Override
			protected void populateItem(Item<Room> item) {
				item.add(new Label("name", new PropertyModel<RoomsEnum>(item
						.getModel(), "type")));
				item.add(new Label("width", new PropertyModel<Room>(item
						.getModel(), "width")));
				item.add(new Label("length", new PropertyModel<Room>(item
						.getModel(), "length")));
				item.add(new Link<Room>("delete", item.getModel()) {
					@Override
					public void onClick() {
						publication().removeRoom(getModelObject());
					}
				});
			}

		};
		add(rv);
		Label empty = new Label("emptyRooms", Application.get()
				.getResourceSettings().getLocalizer()
				.getString("emptyRooms", this));
		add(empty);
		if (publication().getRooms().size() != 0) {
			empty.setVisible(false);
		}

	}

	private Publication publication() {
		return publication.getObject();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		publication.detach();
	}
}
