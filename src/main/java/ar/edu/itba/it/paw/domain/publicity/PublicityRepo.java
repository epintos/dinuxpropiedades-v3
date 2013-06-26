package ar.edu.itba.it.paw.domain.publicity;

/**
 * Publicity Repository.
 * 
 */
public interface PublicityRepo {

	/**
	 * Returns a random publicity according to it's frequence.
	 * @return Publicity
	 */
	public Publicity getRandomPublicity();

}
