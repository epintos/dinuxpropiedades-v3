package ar.edu.itba.it.paw.domain.photos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ar.edu.itba.it.paw.domain.PersistentEntity;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.users.User;

@Entity
public class Photo extends PersistentEntity {

	@Column(nullable = false)
	private byte[] file;

	@OneToOne(optional = true)
	private User user;

	@ManyToOne(optional = true)
	private Publication publication;

	public Photo() {
	}

	public Photo(byte[] file, User user) {
		this.file = file;
		this.user = user;
	}

	public Photo(byte[] file, Publication publication) {
		this.file = file;
		this.publication = publication;
	}

	public byte[] getFile() {
		return file;
	}

	public Publication getPublication() {
		return publication;
	}

	public User getUser() {
		return user;
	}

}
