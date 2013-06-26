package ar.edu.itba.it.paw.domain.publications;

import java.io.Serializable;

public class Search implements Serializable {
	private Operations operationType;
	private PropertyType propertyType;
	private Integer priceFrom;
	private Integer priceTo;
	private OrderEnum order;
	private Integer from;
	private Integer resultsPerPage;
	private Currency currency;

	public Search(Operations operationType, PropertyType propertyType,
			Integer priceFrom, Integer priceTo, OrderEnum order, Currency currency) {
		this.operationType = operationType;
		this.priceFrom = priceFrom;
		this.priceTo = priceTo;
		this.order = order;
		this.propertyType = propertyType;
		this.currency = currency;
	}

	public Operations getOperationType() {
		return operationType;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public Integer getPriceFrom() {
		return priceFrom;
	}

	public Integer getPriceTo() {
		return priceTo;
	}

	public OrderEnum getOrder() {
		return order;
	}

	public Integer getFrom() {
		return from;
	}

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}
	
	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	
	public void setFrom(Integer from) {
		this.from = from;
	}
	
	public void setOrder(OrderEnum order) {
		this.order = order;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
