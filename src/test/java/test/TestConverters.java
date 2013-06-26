package test;

import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.web.converters.UserConverter;

public class TestConverters {

	public static void main(String[] args) {

//		UserConversionTest(new UserConverter(new UserService(
//				new InMemoryUserDAOManagerImpl())));
//
//		PropertyConversionTest(new PropertyConverter(new SearchService(
//				new InMemoryPropertyDAOManagerImpl())));
	}


	public static void UserConversionTest(UserConverter conv) {

		User user = null;
		String toConvert = "4";
		user = conv.convert(toConvert);
		if (user != null)
			System.out.println(user);
		else
			System.out.println("Error encounter while converting" + toConvert
					+ " to User");
	}
}
