package ar.edu.itba.it.paw.web.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.photos.Photo;
import ar.edu.itba.it.paw.domain.photos.PhotoRepo;

@Component
public class PhotoConverter implements Converter<String, Photo> {

	
	private PhotoRepo repo;

	@Autowired
	public PhotoConverter(PhotoRepo repo) {
		this.repo = repo;
	}
	
	public Photo convert(String arg0) {
		return repo.getPhoto(Integer.valueOf(arg0));
	}
}
