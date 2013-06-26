package ar.edu.itba.it.paw.domain.publications;

import java.util.List;

import ar.edu.itba.it.paw.domain.users.User;

/**
 * Publication Repository.
 * 
 */
public interface PublicationRepo {

	/**
	 * Returns all the publications.
	 * 
	 * @return List of Publication
	 */
	public List<Publication> getAll();

	/**
	 * Returns all the publications of an user.
	 * 
	 * @param user
	 * @param filterFinished
	 *            If true then the FINISHED publications are not returned. If
	 *            false, then all the publications are returned.
	 * @return
	 */
	public List<Publication> getAll(User user, boolean filterFinished);
	
	public Integer getAllSize(User user, boolean filterFinished);

	/**
	 * Returns a Publication
	 * 
	 * @param id
	 * @return
	 */
	public Publication get(int id);

	/**
	 * Returns all the publications according to an advanced search.
	 * 
	 * @param search
	 *            Containing operationType, propertyType, price range.
	 * @return
	 */
	public List<Publication> getAll(Search search);

	/**
	 * Returns the total publications size available according to an advanced
	 * search.
	 * 
	 * @param search
	 * @return
	 */
	public Integer getAllSize(Search search);

	/**
	 * Adds a publication
	 * @param publication
	 */
	public void add(Publication publication);

}
