package ar.edu.itba.it.paw.web.publications;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.StatusChange;

public class StatusChangeHistoryProvider extends SortableDataProvider<StatusChange> implements ISortableDataProvider<StatusChange> {

	private EntityModel<Publication> publication;
	
	public StatusChangeHistoryProvider(Publication publication) {
		this.publication = new EntityModel<Publication>(Publication.class, publication);
	}

	public Iterator<? extends StatusChange> iterator(int first, int count) {
		return publication.getObject().getStatusLog().iterator();
	}

	public int size() {
		return publication.getObject().getStatusLog().size();
	}

	public IModel<StatusChange> model(StatusChange object) {
		return new Model<StatusChange>(object);
	}
	
	
	
}
