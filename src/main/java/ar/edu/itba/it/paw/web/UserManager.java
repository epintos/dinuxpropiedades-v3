package ar.edu.itba.it.paw.web;

import ar.edu.itba.it.paw.domain.users.User;

public interface UserManager {

    /**
     * Sets the given User in the Session
     * 
     * @param User the user that is logging in
     * */
    public void setUser(User user);

    /**
     * Checks weather there is a user logged in or not
     * @return true if there a logged user
     * */
    public boolean existsUser();

    /**
     * Returns the logged user
     * @return the logged user
     * */
    public User getUser();

    /**
     * Logs out the logged in user
     * */
    public void logout();

    /**
     * Takes the corresponding actions so that the browser knows the username of the logged user
     * */
    public void setRememberMe();

    
    /**
     * Takes the corresponding actions so that the user is always logged in
     * */
    public void setKeepMeLogged();
    
    /**
     * Turns off the KeepMeLogged function.
     */
    public void forgetUsername();
    
}
