package ar.edu.itba.it.paw.domain;

public class ValidatorImpl implements ValidatorInterface {

	public boolean validateEmail(String email) {
		if (email == null)
			return false;
		return email.matches("[a-z0-9.-_]*@[a-z0-9.-_]*");
	}

	public boolean validateLenght(String tovalidate, int n) {
		return !(tovalidate.length() > n);
	}

	public boolean between(int tocheck, int floor, int roof) {
		return (tocheck >= floor) && (tocheck <= roof);

	}

}
