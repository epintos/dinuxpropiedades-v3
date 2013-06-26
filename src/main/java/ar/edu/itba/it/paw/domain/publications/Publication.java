package ar.edu.itba.it.paw.domain.publications;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CollectionOfElements;
import org.joda.time.DateTime;

import ar.edu.itba.it.paw.domain.BadInformationException;
import ar.edu.itba.it.paw.domain.InformationMissingException;
import ar.edu.itba.it.paw.domain.PersistentEntity;
import ar.edu.itba.it.paw.domain.ValidatorImpl;
import ar.edu.itba.it.paw.domain.ValidatorInterface;
import ar.edu.itba.it.paw.domain.photos.Photo;
import ar.edu.itba.it.paw.domain.users.User;

@Entity
public class Publication extends PersistentEntity {

	@ManyToOne
	private User user;

	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToMany(mappedBy = "publication", cascade = { CascadeType.ALL,
			CascadeType.REMOVE })
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<Photo> photos;

	@Column(nullable = false)
	private Integer price;

	@Enumerated(EnumType.STRING)
	private Currency currency;

	@Enumerated(EnumType.STRING)
	private PropertyType propertyType;

	@Enumerated(EnumType.STRING)
	private Operations operationType;

	@OneToOne
	private Photo mainPhoto;

	@Column(nullable = false)
	private String neighbourhood;

	@Column(nullable = false)
	private Integer coveredSurface;

	@Column(nullable = false)
	private Integer uncoveredSurface;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Room> rooms;

	@Column(nullable = false)
	private Integer roomsQty;

	private String description;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	private String street;

	@Column(nullable = false)
	private Integer numbering;

	private Integer floor;
	private String apartment = null;

	@CollectionOfElements
	@JoinTable(name = "Services", joinColumns = @JoinColumn(name = "publication_id"))
	@Enumerated(EnumType.STRING)
	private List<Services> services;

	@CollectionOfElements
	@JoinTable(name = "StatusChange", joinColumns = @JoinColumn(name = "publication_id"))
	private List<StatusChange> statusLog;

	private Integer visitCount;

	private transient ValidatorInterface validator = new ValidatorImpl();

	private final static int maxNeighbourhoodLenght = 50;
	private final static int maxDescriptionLenght = 512;
	private final static int maxStreetLenght = 50;
	private final static int maxApartmentLenght = 5;

	protected Publication() {
	}

	public Publication(User user, String neighbourhood, Integer coversup,
			Integer uncoversup, Integer roomsQty, String description,
			Integer age, String street, Integer numbering, Integer floor,
			String apartment, Integer price, Currency currency,
			Operations operationType, PropertyType propertyType,
			List<Services> services) {
		setStatus(Status.ACTIVE);
		setNeighbourhood(neighbourhood);
		setRoomsQty(roomsQty);
		setCoveredSurface(coversup);
		setUncoveredSurface(uncoversup);
		setDescription(description);
		setAge(age);
		setStreet(street);
		setNumbering(numbering);
		setFloor(floor);
		setApartment(apartment);
		setPrice(price);
		setOperationType(operationType);
		setPropertyType(propertyType);
		setServices(services);
		setUser(user);
		setCurrency(currency);
		photos = new ArrayList<Photo>();
		rooms = new ArrayList<Room>();
		visitCount = 0;
	}

	public Photo getMainPhoto() {
		if (this.mainPhoto == null) {
			if (!photos.isEmpty()) {
				setMainPhoto(photos.get(0));
			}
		}
		return this.mainPhoto;
	}

	public void setMainPhoto(Photo mainPhoto) {
		this.mainPhoto = mainPhoto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		if (user == null)
			throw new BadInformationException();
		this.user = user;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void addPhotos(List<Photo> photos) {
		this.photos.addAll(photos);
	}

	public void deletePhotos(List<Photo> photos) {
		this.photos.removeAll(photos);
		if (photos.contains(mainPhoto)) {
			mainPhoto = null;
		}
		if (this.photos.isEmpty()) {
			this.mainPhoto = null;
		}
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		if (status == null)
			throw new BadInformationException();
		this.status = status;
	}

	public void modifyStatus(Status status) {
		statusLog.add(new StatusChange(this.status, new DateTime(), status));
		setStatus(status);
	}

	public Operations getOperationType() {
		return operationType;
	}

	public void setOperationType(Operations operationType) {
		if (operationType == null)
			throw new BadInformationException();
		this.operationType = operationType;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		if (propertyType == null)
			throw new BadInformationException();
		this.propertyType = propertyType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		if (price != null && validator.between(price, 0, Integer.MAX_VALUE))
			this.price = price;
		else
			throw new BadInformationException();
	}

	public Photo getPhoto(Photo p) {
		for (Photo photo : photos) {
			if (photo.getId() == p.getId())
				return photo;
		}
		return null;
	}

	public String getNeighbourhood() {
		return neighbourhood;
	}

	public void setNeighbourhood(String neighbourhood) {
		if (neighbourhood != null && !neighbourhood.equals(""))
			this.neighbourhood = neighbourhood;
		else
			throw new InformationMissingException();

		if (!validator.validateLenght(neighbourhood, maxNeighbourhoodLenght))
			throw new BadInformationException();

	}

	public Integer getCoveredSurface() {
		return coveredSurface;
	}

	public void setCoveredSurface(Integer coveredsurface) {

		if (coveredsurface != null
				&& validator.between(coveredsurface, 1, Integer.MAX_VALUE))
			this.coveredSurface = coveredsurface;
		else
			throw new BadInformationException();

	}

	public Integer getUncoveredSurface() {
		return uncoveredSurface;
	}

	public void setUncoveredSurface(Integer uncoveredsurface) {
		if (uncoveredsurface != null
				&& validator.between(uncoveredsurface, 0, Integer.MAX_VALUE))
			this.uncoveredSurface = uncoveredsurface;
		else
			throw new BadInformationException();

	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description != null
				&& validator.validateLenght(description, maxDescriptionLenght))
			this.description = description;
		else if (description != null)
			throw new BadInformationException();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		if (validator.between(age, 0, Integer.MAX_VALUE))
			this.age = age;
		else
			throw new BadInformationException();
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		if (street != null && !street.equals(""))
			this.street = street;
		else
			throw new InformationMissingException();

		if (!validator.validateLenght(street, maxStreetLenght))
			throw new BadInformationException();
	}

	public Integer getNumbering() {
		return numbering;
	}

	public void setNumbering(Integer numbering) {
		if (numbering != null
				&& validator.between(numbering, 0, Integer.MAX_VALUE))
			this.numbering = numbering;
		else
			throw new BadInformationException();
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		if (floor != null) {
			if (!validator.between(floor, 0, Integer.MAX_VALUE)) {
				throw new BadInformationException();
			} else
				this.floor = floor;
		}
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		if (apartment != null) {
			if (validator.validateLenght(apartment, maxApartmentLenght))
				this.apartment = apartment;
			else
				throw new BadInformationException();
		}
	}

	public boolean isHouse() {
		return floor == null;
	}

	public void addRoom(Room room) {
		this.rooms.add(room);
	}

	public void increaseVisitCount() {
		if (visitCount == null) {
			visitCount = 0;
		}
		visitCount++;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Integer getRoomsQty() {
		return roomsQty;
	}

	public void setRoomsQty(Integer roomsQty) {
		if (roomsQty == null
				|| !validator.between(roomsQty, 1, Integer.MAX_VALUE))
			throw new BadInformationException();
		this.roomsQty = roomsQty;
	}

	public void addComment(Comment comment) {
		return;
	}

	public boolean isMyPhoto(Photo photo) {
		return photos.contains(photo);
	}

	public boolean isMyPhoto(List<Photo> photo) {
		return photos.containsAll(photo);
	}

	public void removeRoom(Room room) {
		rooms.remove(room);
	}

	public void removePhoto(Photo photo) {
		if (photo.equals(mainPhoto))
			mainPhoto = null;
		photos.remove(photo);
	}

	public List<StatusChange> getStatusLog() {
		return statusLog;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getPricePerSquareMeter() {
		Integer totalMeters = getCoveredSurface() + getUncoveredSurface();
		return getPrice() / totalMeters;
	}
}
