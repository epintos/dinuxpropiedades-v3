package ar.edu.itba.it.paw.web.publications;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.PublicationRepo;
import ar.edu.itba.it.paw.domain.publications.Search;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.base.SearchPanel;

public class ListResultsPage extends BasePage {

	@SpringBean
	private PublicationRepo publicationRepo;
	private Search search;
	
	public ListResultsPage(Search search) {
		this.search = search;
	}
	
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new SearchPanel("searchPanel"));
		
		
		List<IColumn<Publication>> columns = new ArrayList<IColumn<Publication>>();
		

		columns.add(new PropertyColumn(new StringResourceModel("status", null), "status"));
		columns.add(new PropertyColumn(new StringResourceModel("propertyType", null), "propertyType"));
		columns.add(new PropertyColumn(new StringResourceModel("operationType", null), "operationType"));
		columns.add(new PropertyColumn(new StringResourceModel("price", null), "price", "price"));
		columns.add(new PropertyColumn(new StringResourceModel("pricePerSquareMeter", null), "pricePerSquareMeter"));
		columns.add(new PropertyColumn(new StringResourceModel("currency", null), "currency"));
		columns.add(new PropertyColumn(new StringResourceModel("neighbourhood", null), "neighbourhood"));
		columns.add(new PropertyColumn(new StringResourceModel("street", null), "street"));
		columns.add(new PropertyColumn(new StringResourceModel("numbering", null), "numbering"));
		columns.add(new AbstractColumn<Publication>(new StringResourceModel("moreInfo", null)) {
			public void populateItem(Item<ICellPopulator<Publication>> cell,
					String wicketId, IModel<Publication> pub) {
				cell.add(new LinkPanel(wicketId, pub, "moreInfo"));
				
			}
		});
		
		add(new AjaxFallbackDefaultDataTable<Publication>("results", columns, new PublicationProvider(publicationRepo, search), 10));
	}

}
