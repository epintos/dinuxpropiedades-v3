package ar.edu.itba.it.paw.web.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.domain.users.UserRepo;

@Component
public class UserConverter implements Converter<String, User> {

	private UserRepo repo;

	@Autowired
	public UserConverter(UserRepo repo) {
		this.repo = repo;
	}

	public User convert(String source) {
		return repo.get(Integer.valueOf(source));
	}

}
