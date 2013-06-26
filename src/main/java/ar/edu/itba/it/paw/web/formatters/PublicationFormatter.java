package ar.edu.itba.it.paw.web.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.PublicationRepo;

public class PublicationFormatter implements Formatter<Publication> {

	private PublicationRepo repo;

	public PublicationFormatter(PublicationRepo repo) {
		this.repo = repo;
	}

	public String print(Publication arg0, Locale arg1) {
		return String.valueOf(arg0.getId());
	}

	public Publication parse(String arg0, Locale arg1) throws ParseException {
		return repo.get(Integer.valueOf(arg0));
	}

}
