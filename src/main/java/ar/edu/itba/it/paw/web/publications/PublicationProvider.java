package ar.edu.itba.it.paw.web.publications;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.OrderEnum;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.PublicationRepo;
import ar.edu.itba.it.paw.domain.publications.Search;

public class PublicationProvider extends SortableDataProvider<Publication> implements ISortableDataProvider<Publication> {

	private Search search;
	private PublicationRepo publicationRepo;
	
	public PublicationProvider(PublicationRepo publicationRepo, Search search) {
		this.search = search;
		this.publicationRepo = publicationRepo;
		setSort("price", SortOrder.ASCENDING);
	}
	
	public List<Publication> get() {
		return publicationRepo.getAll(search);
	}
	

	public void detach() {
		return;
	}

	public Iterator<? extends Publication> iterator(int first, int count) {
		search.setFrom(first);
		search.setResultsPerPage(count);
		search.setOrder(getSort().isAscending() ? OrderEnum.ASC:OrderEnum.DES);
		return publicationRepo.getAll(search).iterator();
	}

	public int size() {
		return publicationRepo.getAllSize(search);
	}

	public IModel<Publication> model(Publication object) {
		return new EntityModel<Publication>(Publication.class, object);
	}

}
