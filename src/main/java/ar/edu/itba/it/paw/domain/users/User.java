package ar.edu.itba.it.paw.domain.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.edu.itba.it.paw.domain.BadInformationException;
import ar.edu.itba.it.paw.domain.InformationMissingException;
import ar.edu.itba.it.paw.domain.PersistentEntity;
import ar.edu.itba.it.paw.domain.ValidatorImpl;
import ar.edu.itba.it.paw.domain.ValidatorInterface;
import ar.edu.itba.it.paw.domain.photos.Photo;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.Status;

@Entity
@Table(name = "Users")
public class User extends PersistentEntity {

	@Enumerated(EnumType.STRING)
	private UserType userType;

	@Column(nullable = false)
	private String name;

	private String surname;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@JoinColumn(name = "user_id")
	private List<Publication> publications;

	@Column(nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String phone;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private Photo photo;

	private transient ValidatorInterface v = new ValidatorImpl();

	private final static int maxUsernameLenght = 50;
	private final static int maxNameLenght = 50;
	private final static int maxSurnameLenght = 50;
	private final static int maxPasswordLenght = 50;
	private final static int maxEmailLenght = 50;

	protected User() {
	}

	public User(String name, String surname, String email, String username,
			String password, String phone, UserType usertype) {

		setName(name);
		// It is important to set the userType before the username
		setUserType(usertype);
		setSurname(surname);
		setEmail(email);
		setUsername(username);
		setPassword(password);
		setPhone(phone);
		setUsername(username);
		publications = new ArrayList<Publication>();

	}

	public void addPublication(Publication publication) {
		this.publications.add(publication);
	}

	public int getCount() {
		int count = 0;
		for (Publication publication : publications) {
			if (!publication.getStatus().equals(Status.SOLD) && !publication.getStatus().equals(Status.CANCELED)) {
				count++;
			}

		}
		return count;
	}

	public String toString() {
		return userType + ": " + name + " " + surname == null ? ""
				: surname;
	}

	public void setEmail(String email) {
		if (email.equals(""))
			throw new InformationMissingException();

		if (v.validateEmail(email))
			this.email = email;
		else
			throw new BadInformationException();

		if (!v.validateLenght(email, maxEmailLenght))
			throw new BadInformationException();
	}

	public void setName(String name) {
		if (!name.equals(""))
			this.name = name;
		else
			throw new InformationMissingException();

		if (!v.validateLenght(name, maxNameLenght))
			throw new BadInformationException();

	}

	public void setPassword(String password) {
		if (!password.equals(""))
			this.password = password;
		else
			throw new InformationMissingException();

		if (!v.validateLenght(password, maxPasswordLenght))
			throw new BadInformationException();

	}

	public void setPhone(String phone) {
		if (phone.equals(""))
			throw new InformationMissingException();
		this.phone = phone;
	}

	public void setSurname(String surname) {
		if (getUserType().equals(UserType.USER)) {
			if (!surname.equals(""))
				this.surname = surname;
			else
				throw new InformationMissingException();

			if (!v.validateLenght(surname, maxSurnameLenght))
				throw new BadInformationException();
		}
	}

	public void setUsername(String username) {
		if (!username.equals(""))
			this.username = username;
		else
			throw new InformationMissingException();

		if (!v.validateLenght(username, maxUsernameLenght))
			throw new BadInformationException();

	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public Photo getPhoto() {
		return photo;
	}

	public UserType getUserType() {
		return userType;
	}

	public boolean logIn(String password) {
		return getPassword().equals(password);
	}

	public boolean isMyPublication(Publication publication) {
		for (Publication p : publications) {
			if (p.equals(publication)) {
				return true;
			}
		}
		return false;

	}

}