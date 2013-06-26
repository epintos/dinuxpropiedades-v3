package ar.edu.itba.it.paw.domain;

@SuppressWarnings("serial")
public class BadInformationException extends RuntimeException {
	public BadInformationException() {
	}

	public BadInformationException(String msg) {
		super(msg);
	}

}
