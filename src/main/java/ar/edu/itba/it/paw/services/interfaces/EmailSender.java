package ar.edu.itba.it.paw.services.interfaces;

import ar.edu.itba.it.paw.domain.publications.Comment;
import ar.edu.itba.it.paw.domain.publications.Publication;

/**
 * This interface provides a method to send an email to a given email account
 * with a certain body
 * 
 * 
 * */

public interface EmailSender {

	/**
	 * Sends an email to the publication owner.
	 * 
	 * @param comment
	 * @param publication
	 * 
	 * */
	public void sendEmail(Comment comment, Publication publication);
}
