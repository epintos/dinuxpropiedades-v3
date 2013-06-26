package ar.edu.itba.it.paw.domain.photos;


/**
 * Photo Repository
 *
 */
public interface PhotoRepo {
	
	/**
	 * Retuns a Photo
	 * @param id
	 * @return
	 */
	public Photo getPhoto(int id);
	
}
