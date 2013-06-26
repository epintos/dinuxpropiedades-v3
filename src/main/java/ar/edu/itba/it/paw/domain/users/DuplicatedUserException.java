package ar.edu.itba.it.paw.domain.users;


public class DuplicatedUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private User user;

	public DuplicatedUserException(User subject) {
		this.user = subject;
	}

	public User getSubject() {
		return user;
	}

}
