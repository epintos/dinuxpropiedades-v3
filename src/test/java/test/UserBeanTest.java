package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.edu.itba.it.paw.domain.BadInformationException;
import ar.edu.itba.it.paw.domain.InformationMissingException;
import ar.edu.itba.it.paw.domain.publications.Currency;
import ar.edu.itba.it.paw.domain.publications.Operations;
import ar.edu.itba.it.paw.domain.publications.PropertyType;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.Services;
import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.domain.users.UserType;

public class UserBeanTest {

	private String neighbourhood = "test";
	private Integer coversup = 123;
	private Integer uncoversup = 123;
	private Integer roomsQty = 123;
	private String description = "";
	private Integer age = 123;
	private String street = "test";
	private Integer numbering = 123;
	private Integer floor = 1;
	private String apartment = "C";
	private Integer price = 123;
	private Operations operationType = Operations.SALE;
	private PropertyType propertyType = PropertyType.HOUSE;
	private List<Services> services = null;

	@Test
	public void testLongName() {
		// name with more than 50 characters
		String name = "asdfasdfadfadfasdfasdfafasdfasdfasdfasdfsadfasdfasdfsadfdafsafsdfsadfadfasfasfasd";
		String surname = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		try {
			@SuppressWarnings("unused")
			User u = new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (BadInformationException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el nombre del usuario es muy largo");
		}
		Assert.fail();

	}

	@Test
	public void testLongSurnameName() {
		// surname with more than 50 characters
		String surname = "asdfasdfadfadfasdfasdfafasdfasdfasdfasdfsadfasdfasdfsadfdafsafsdfsadfadfasfasfasd";
		String name = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (BadInformationException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el apellido del usuario es muy largo");
		}
		Assert.fail();

	}

	@Test
	public void testLongEmail() {
		// email with more than 50 characters
		String surname = "test";
		String name = "test";
		String email = "testasdfasfasdfasdfasdfasdfasdfasdfdasfadsfasdfasdfasdfsadfasdfdsafsadfsadfdsafdsaf@test.com";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (BadInformationException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el mail del usuario es muy largo");
		}
		Assert.fail();

	}

	@Test
	public void testLongPassword() {
		// password with more than 50 characters
		String surname = "test";
		String name = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "testasdfasdfasdfasfasdfasdfadsfasfasfasdfdsafadsfdfsafasdfasfasfasfasfsadfasdfasdfasdfadsfads";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (BadInformationException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el password del usuario es muy largo");
		}
		Assert.fail();

	}

	@Test
	public void testLongUsername() {
		// username with more than 50 characters
		String surname = "test";
		String name = "test";
		String email = "test@test.com";
		String username = "testasdfasdfsadfasdfasdfadsfsadfasdfdasfadsfasdfasdfasdfasdfasdfasdfasdfasdfsdfasdfasdfasdfsdafads";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (BadInformationException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque username es muy largo");
		}
		Assert.fail();

	}

	@Test
	public void testEmptyName() {
		String name = "";
		String surname = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (InformationMissingException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el nombre del usuario es vacio");
		}
		Assert.fail();

	}

	@Test
	public void testEmtpySurname() {
		String surname = "";
		String name = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (InformationMissingException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el apellido del usuario es vacio");
		}
		Assert.fail();

	}

	@Test
	public void testEmptyEmail() {
		String surname = "test";
		String name = "test";
		String email = "";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (InformationMissingException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el mail del usuario es vacio");
		}
		Assert.fail();

	}

	@Test
	public void testEmptyPassword() {
		String surname = "test";
		String name = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (InformationMissingException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el password del usuario es vacio");
		}
		Assert.fail();

	}

	@Test
	public void testEmptyUsername() {
		String surname = "test";
		String name = "test";
		String email = "test@test.com";
		String username = "";
		String password = "test";
		String phone = "4-123-4567";

		try {
			new User(name, surname, email, username, password, phone,
					UserType.USER);
		} catch (InformationMissingException e) {
			return;
		} catch (Exception e) {
			Assert.fail("Deberia fallar porque el nombre de usuario es vacio");
		}
		Assert.fail();

	}

	@Test
	public void testIsMyPublication() {
		String surname = "test";
		String name = "test";
		String email = "test@test.com";
		String username = "test";
		String password = "test";
		String phone = "4-123-4567";

		User user = new User(name, surname, email, username, password, phone, UserType.USER);
		Publication publication = new Publication(user, neighbourhood, coversup, uncoversup, roomsQty, description, age, street, numbering, floor, apartment, price, Currency.DOLAR, operationType, propertyType, services);
		user.addPublication(publication);
		
		if(!user.isMyPublication(publication)) {
			Assert.fail();
		}
		
		Publication publication2 = new Publication(user, "something", coversup, uncoversup, roomsQty, description, age, street, numbering, floor, apartment, price, Currency.DOLAR, operationType, propertyType, services);
		
		
		if(user.isMyPublication(publication2)) {
			Assert.fail();
		}
		
		user.addPublication(publication2);
		
		if(!user.isMyPublication(publication2)) {
			Assert.fail();
		}
		
		if(!user.isMyPublication(publication)) {
			Assert.fail();
		}

	}
}
