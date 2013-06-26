package ar.edu.itba.it.paw.web.base;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.MinimumValidator;

import ar.edu.itba.it.paw.domain.publications.Currency;
import ar.edu.itba.it.paw.domain.publications.Operations;
import ar.edu.itba.it.paw.domain.publications.OrderEnum;
import ar.edu.itba.it.paw.domain.publications.PropertyType;
import ar.edu.itba.it.paw.domain.publications.Search;
import ar.edu.itba.it.paw.web.publications.ListResultsPage;
import ar.edu.itba.it.paw.web.utils.PlaceholderBehavior;

public class SearchPanel extends Panel {

	private Integer priceFrom;
	private Integer priceTo;
	private Operations operationType;
	private PropertyType propertyType;
	private Search search;
	private Currency currency;

	public SearchPanel(String id) {
		super(id);
		Form<SearchPanel> form = new Form<SearchPanel>("searchForm") {

			@Override
			protected void onSubmit() {
				search = new Search(operationType, propertyType, priceFrom,
						priceTo, OrderEnum.ASC, currency);
				setResponsePage(new ListResultsPage(search));

			}
		};

		setDefaultModel(new CompoundPropertyModel<SearchPanel>(this));
		
		DropDownChoice<Operations> operationDrop = new DropDownChoice<Operations>(
				"operationType", EnumModel.create(Operations.class));
		form.add(operationDrop);
		
		DropDownChoice<Currency> currencyDrop = new DropDownChoice<Currency>(
				"currency", EnumModel.create(Currency.class));
		form.add(currencyDrop);
		
		DropDownChoice<PropertyType> propertyTypeDrop = new DropDownChoice<PropertyType>(
				"propertyType", EnumModel.create(PropertyType.class));
		form.add(propertyTypeDrop);

		TextField<Integer> priceFrom = new TextField<Integer>("priceFrom");
		priceFrom.add(new PlaceholderBehavior(getString("from")));
		form.add(priceFrom.add(new MinimumValidator<Integer>(0)));
		form.add(new ComponentFeedbackPanel("priceFromError", priceFrom));

		TextField<Integer> priceTo = new TextField<Integer>("priceTo");
		form.add(priceTo.add(new MinimumValidator<Integer>(0)));
		priceTo.add(new PlaceholderBehavior(getString("to")));
		form.add(new ComponentFeedbackPanel("priceToError", priceTo));

		form.add(new Button("search", new ResourceModel("search")));
		add(form);
	}

}
