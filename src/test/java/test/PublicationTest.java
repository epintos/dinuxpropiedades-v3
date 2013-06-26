package test;

import java.util.List;

import junit.framework.Assert;

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

public class PublicationTest {

	private String surname = "test";
	private String name = "test";
	private String email = "test@test.com";
	private String username = "test";
	private String password = "test";
	private String phone = "4-123-4567";
	private User u = new User(name, surname, email, username, password, phone,
			UserType.USER);

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
	public void testNullUser() {
		try {
			new Publication(null, "123", 123, 123, 123, "", 123, "123", 123,
					123, null, 123, Currency.DOLAR, Operations.SALE, PropertyType.APARTMENT,
					null);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullStatus() {
		try {
			Publication p = new Publication(u, "123", 123, 123, 123, "", 123,
					"123", 123, 123,  null, 123, Currency.DOLAR, Operations.SALE,
					PropertyType.APARTMENT, null);
			p.setStatus(null);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testEmptyNeighbourhood() {
		try {
			new Publication(u, "", coversup, uncoversup, roomsQty, description,
					age, street, numbering, floor, apartment, price,  Currency.DOLAR,
					operationType, propertyType, services);
		} catch (InformationMissingException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullNeighbourhood() {
		try {
			new Publication(u, null, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					price,  Currency.DOLAR, operationType, propertyType, services);
		} catch (InformationMissingException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testLongNeighbourhood() {
		try {
			new Publication(
					u,
					"asdfasdfasdfasdfdasfdasfadsfdasfasdfasdfadsfsdafadsfadsfdsfasdfadsfasdfdasfadsfdasfadsfadsfadsfadsfads",
					coversup, uncoversup, roomsQty, description, age, street,
					numbering, floor, apartment, price, Currency.DOLAR, operationType,
					propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativeRoomsQty() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, -3,
					description, age, street, numbering, floor, apartment,
					price,  Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullRoomsQty() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, null,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativeCoveredSurface() {
		try {
			new Publication(u, neighbourhood, -1, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullCoveredSurface() {
		try {
			new Publication(u, neighbourhood, null, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativeUnCoveredSurface() {
		try {
			new Publication(u, neighbourhood, coversup, -1, roomsQty,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullUnCoveredSurface() {
		try {
			new Publication(u, neighbourhood, coversup, null, roomsQty,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullDescription() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					null, age, street, numbering, floor, apartment, price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (Exception e) {
			Assert.fail();
		}
		return;
	}

	@Test
	public void testLongDescription() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < 600; i++) {
			s.append("A");
		}
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					s.toString(), age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativeAge() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, -1, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testMaxAge() {

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, Integer.MAX_VALUE, street, numbering, floor,
					apartment, price, Currency.DOLAR, operationType, propertyType, services);
		} catch (Exception e) {
			Assert.fail();
		}

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, Integer.MAX_VALUE + 1, street, numbering,
					floor, apartment, price, Currency.DOLAR, operationType, propertyType,
					services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullStreet() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, null, numbering, floor, apartment, price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (InformationMissingException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testLongStreetName() {
		String s = "adsfasdfasdfadsfdasfadsfdasfdasfdsafdsafdsafdsafdsafdsafadsfadsfdasfasdfasdfdsafadsfadsfsadfasdfasdfsadfasdfasd";
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, s, numbering, floor, apartment, price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativeNumbering() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, -1, floor, apartment, price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullNumbering() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, null, floor, apartment, price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testBigNumbering() {

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, Integer.MAX_VALUE, floor,
					apartment, price, Currency.DOLAR, operationType, propertyType, services);
		} catch (Exception e) {
			Assert.fail();
		}

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, Integer.MAX_VALUE + 1, floor,
					apartment, price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativeFloor() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, -1, apartment, price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void tesBigFloor() {

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, Integer.MAX_VALUE,
					apartment, price, Currency.DOLAR, operationType, propertyType, services);
		} catch (Exception e) {
			Assert.fail();
		}

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, Integer.MAX_VALUE + 1,
					apartment, price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void tesBigApartment() {

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, "ABCDE", price, Currency.DOLAR,
					operationType, propertyType, services);
		} catch (Exception e) {
			Assert.fail();
		}

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, "ABCDEF",
					price, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNegativePrice() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment, -1, Currency.DOLAR, 
					operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullPrice() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					null, Currency.DOLAR, operationType, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testBigPrice() {

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					Integer.MAX_VALUE, Currency.DOLAR, operationType, propertyType, services);
		} catch (Exception e) {
			Assert.fail();
		}

		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					Integer.MAX_VALUE + 1, Currency.DOLAR, operationType, propertyType,
					services);
		} catch (BadInformationException e) {
			return;
		}
		Assert.fail();
	}

	@Test
	public void testNullOperationType() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, null, propertyType, services);
		} catch (BadInformationException e) {
			return;
		}
		
		Assert.fail();
	}
	
	@Test
	public void testNullPropertyType() {
		try {
			new Publication(u, neighbourhood, coversup, uncoversup, roomsQty,
					description, age, street, numbering, floor, apartment,
					price, Currency.DOLAR, operationType, null, services);
		} catch (BadInformationException e) {
			return;
		}
		
		Assert.fail();
	}
}
