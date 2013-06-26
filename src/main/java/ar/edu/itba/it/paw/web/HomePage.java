package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.base.SearchPanel;

public class HomePage extends BasePage {

	public HomePage() {
		add(new SearchPanel("searchPanel"));
	}
}
