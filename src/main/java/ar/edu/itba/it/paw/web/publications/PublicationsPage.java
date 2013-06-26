package ar.edu.itba.it.paw.web.publications;

import java.util.Iterator;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.PublicationRepo;
import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.base.SearchPanel;
import ar.edu.itba.it.paw.web.utils.WicketUtils;

import com.google.code.jqwicket.ui.accordion.AccordionWebMarkupContainer;

public class PublicationsPage extends BasePage {
	@SpringBean
	private PublicationRepo publications;

	private EntityModel<User> user;

	public PublicationsPage(User user, final boolean filterSold) {
		if (user == null)
			throw new NullPointerException();
		this.user = new EntityModel<User>(User.class, user.getId());
		add(new SearchPanel("searchPanel"));
		AccordionWebMarkupContainer awmc = new AccordionWebMarkupContainer(
				"accordion");

		RefreshingView<Publication> rv = new RefreshingView<Publication>(
				"section") {

			@Override
			protected Iterator<IModel<Publication>> getItemModels() {
				return WicketUtils.getIteratorFor(publications.getAll(user(),
						filterSold));
			}

			@Override
			protected void populateItem(Item<Publication> item) {
				item.setRenderBodyOnly(true);
				item.add(new Label("label", item.getModelObject().getStreet()
						+ " " + item.getModelObject().getNumbering()));
				item.add(new PublicationsItemPanel("myPublicationsItemPanel",
						item.getModelObject(), user()));
			}

		};
		awmc.add(rv);

		add(awmc);
		Label empty = new Label("empty", Application.get()
				.getResourceSettings().getLocalizer().getString("empty", null));
		add(empty);
		if (publications.getAllSize(user(), filterSold) != 0)
			empty.setVisible(false);
	}

	

	private User user() {
		return user.getObject();
	}
}
