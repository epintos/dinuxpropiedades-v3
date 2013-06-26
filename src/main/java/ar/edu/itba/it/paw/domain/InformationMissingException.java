package ar.edu.itba.it.paw.domain;

@SuppressWarnings("serial")
public class InformationMissingException extends RuntimeException{

	public InformationMissingException() {
		super();
	}

	public InformationMissingException(String msg) {
		super(msg);
	}

}
