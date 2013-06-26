package ar.edu.itba.it.paw.web.users;

import java.util.Iterator;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.domain.users.UserRepo;
import ar.edu.itba.it.paw.domain.users.UserType;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.base.SearchPanel;
import ar.edu.itba.it.paw.web.publications.PublicationsPage;
import ar.edu.itba.it.paw.web.utils.ImageResource;
import ar.edu.itba.it.paw.web.utils.WicketUtils;

public class Agencies extends BasePage {

	@SpringBean
	private UserRepo users;

	public Agencies() {
		add(new SearchPanel("searchPanel"));
		RefreshingView<User> rv = new RefreshingView<User>("agency") {
			@Override
			protected Iterator<IModel<User>> getItemModels() {	
				return WicketUtils.getIteratorFor(users.getAll(UserType.AGENCY));
			}

			@Override
			protected void populateItem(Item<User> item) {
				item.add(new Label("name", new PropertyModel<String>(item
						.getModel(), "name")));
				item.add(new Label("publications", new PropertyModel<String>(
						item.getModel(), "count")));
				if (item.getModelObject().getPhoto() == null) {
					item.add(new Image("photo", new ContextRelativeResource(
							"img/agency.jpg")));
				} else {
					item.add(new NonCachingImage("photo", new ImageResource(
							item.getModelObject().getPhoto().getFile())));
				}
				item.add(new Link<User>("view_publications", item.getModel()) {

					@Override
					public void onClick() {
						setResponsePage(new PublicationsPage(getModelObject(), true));
					}
				});
			}
		};
		add(rv);

		Label empty = new Label("empty", Application.get()
				.getResourceSettings().getLocalizer().getString("empty", null));
		add(empty);
		if (users.getAllSize(UserType.AGENCY) != 0) {
			empty.setVisible(false);
		}
	}

}
