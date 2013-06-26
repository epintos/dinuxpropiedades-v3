package ar.edu.itba.it.paw.domain.publications;

import ar.edu.itba.it.paw.domain.BadInformationException;
import ar.edu.itba.it.paw.domain.InformationMissingException;
import ar.edu.itba.it.paw.domain.ValidatorImpl;
import ar.edu.itba.it.paw.domain.ValidatorInterface;

public class Comment {

	private String comment;
	private String email;
	private String phone;
	private String name;
	private ValidatorInterface v = new ValidatorImpl();

	public Comment(String comment, String email, String phone, String name) {
		if (email.isEmpty() || phone.isEmpty() || name.isEmpty())
			throw new InformationMissingException();
		this.setComment(comment);
		this.setEmail(email);
		this.setPhone(phone);
		this.setName(name);
	}

	private void setComment(String comment) {

		int maxCommentLenght = 300;
		if(comment == null){
			comment="";
		}
		if (v.validateLenght(comment, maxCommentLenght))
			this.comment = comment;
		else
			throw new BadInformationException();
	}

	private void setEmail(String email) {
		if (v.validateEmail(email))
			this.email = email;
		else
			throw new BadInformationException();
	}

	private void setPhone(String phone) {
		this.phone = phone;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getName() {
		return name;
	}

}
