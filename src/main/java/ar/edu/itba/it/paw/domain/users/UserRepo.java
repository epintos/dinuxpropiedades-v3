package ar.edu.itba.it.paw.domain.users;

import java.util.List;


/**
 * User Repository.
 * 
 */
public interface UserRepo {

	/**
	 * Get an specific User.
	 * 
	 * @param id
	 * @return User
	 */
	public User get(int id);
	
	/**
	 * Get all the users that are userType.
	 * @return List of users
	 */
	public List<User> getAll(UserType userType);
	
	/**
	 * Returns the total amount of users of type "userType".
	 * @param userType
	 * @return
	 */
	public Integer getAllSize(UserType userType);
	
	/**
	 * Get and User by its username
	 * @param username
	 * @return
	 */
	public User get(String username);
	
	/**
	 * Adds a new User.
	 * @param user
	 */
	public void add(User user);
	
	
}
