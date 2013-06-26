package ar.edu.itba.it.paw.web.publications;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.StringResourceModel;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.StatusChange;
import ar.edu.itba.it.paw.web.base.SecuredPage;

public class ViewStatusChangeHistory extends SecuredPage {
	
	private EntityModel<Publication> publicationModel;

	public ViewStatusChangeHistory(Publication publication) {
		super();
		
		this.publicationModel = new EntityModel<Publication>(Publication.class, publication);
		List<IColumn<StatusChange>> columns = new ArrayList<IColumn<StatusChange>>();

		columns.add(new PropertyColumn(new StringResourceModel(
				"previousStatus", null), "previousStatus"));
		columns.add(new PropertyColumn(new StringResourceModel("date", null),
				"changeDate"));
		columns.add(new PropertyColumn(new StringResourceModel("postStatus",
				null), "newStatus"));
		
		add(new DefaultDataTable<StatusChange>("history", columns, new StatusChangeHistoryProvider(publication), 15));
		add(new Link<Void>("back") {
			
			@Override
			public void onClick() {
				setResponsePage(new PublicationDetailPage(publication()));
			}
		});
		

	}
	
	private Publication publication(){
		return publicationModel.getObject();
	}

}
