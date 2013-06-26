package ar.edu.itba.it.paw.domain;


/**
 * Provides an interface to validate different things.
 * 
 */
public interface ValidatorInterface {

	/**
	 * Validates an email format.
	 * 
	 * @param email
	 * @return true if the email format is correct. false if it is not correct.
	 */
	boolean validateEmail(String email);

	/**
	 * Validates if the length of a data is less than n
	 * 
	 * @param tovalidate
	 *            data
	 * @param n
	 *            max length
	 * @return
	 */
	boolean validateLenght(String tovalidate, int n);

	/**
	 * Validate if a number is between floor and roof
	 * 
	 * @param tocheck
	 * @param floor
	 * @param roof
	 * @return
	 */
	boolean between(int tocheck, int floor, int roof);

}
