package ar.edu.itba.it.paw.domain.publications;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import ar.edu.itba.it.paw.domain.BadInformationException;
import ar.edu.itba.it.paw.domain.PersistentEntity;

@Entity
public class Room extends PersistentEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoomsEnum type;

	@Column(nullable = false)
	private int width;

	@Column(nullable = false)
	private int length;

	protected Room() {
	}

	public Room(RoomsEnum type, int width, int length) {
		setLength(length);
		setWidth(width);
		setType(type);
	}

	public int getLength() {
		return length;
	}

	public RoomsEnum getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	private void setType(RoomsEnum type) {
		this.type = type;
	}

	private void setWidth(int width) {
		if (width < 0) {
			throw new BadInformationException();
		}
		this.width = width;
	}

	private void setLength(int length) {
		if (length < 0)
			throw new BadInformationException();
		this.length = length;
	}

}
