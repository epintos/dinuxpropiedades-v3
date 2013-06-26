package ar.edu.itba.it.paw.web;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.domain.users.UserRepo;

public class WicketSession extends WebSession implements UserManager {

	private String username;
	private static String keepMeLoggedKey = "KeepMeLogged";
	private static String rememberMeKey = "RememberMePlz";

	@SpringBean
	private UserRepo userRepo;

	public static WicketSession get() {
		return (WicketSession) Session.get();
	}

	public WicketSession(Request request) {
		super(request);
		Injector.get().inject(this);
	}

	public String getUsername() {
		return username;
	}

	public boolean signIn(String username, String password) {
		User user = userRepo.get(username);
		if (user != null && user.logIn(password)) {
			this.username = username;
			return true;
		}
		return false;
	}

	public boolean isSignedIn() {
		return username != null;
	}

	public void signOut() {
		invalidate();
		clear();
	}

	public boolean existsUser() {
		if(username != null)
			return true;
		List<Cookie> cookies = ((WebRequest) RequestCycle.get().getRequest())
				.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(keepMeLoggedKey)) {
				username = cookie.getValue().split("/")[0];
			}
		}
		return username != null;
	}

	public User getUser() {
		if (existsUser())
			return userRepo.get(username);
		return null;
	}

	public void setUser(User user) {
		username = user.getUsername();

	}

	public void logout() {
		WebResponse wr = (WebResponse) (RequestCycle.get().getResponse());
		Cookie keepMeLogged = new Cookie(keepMeLoggedKey, " ");
		keepMeLogged.setMaxAge(0);
		wr.addCookie(keepMeLogged);
		signOut();
	}

	public void setKeepMeLogged() {
		WebResponse wr = (WebResponse) (RequestCycle.get().getResponse());
		User user = getUser();
		Cookie logged = new Cookie(keepMeLoggedKey, user.getUsername() + "/"
				+ user.getPassword());
		logged.setMaxAge(60 * 60 * 24);
		// logged.setPath(request.getContextPath());
		wr.addCookie(logged);
	}

	public void setRememberMe() {
		WebResponse wr = (WebResponse) (RequestCycle.get().getResponse());
		User user = getUser();
		Cookie rememberMe = new Cookie(rememberMeKey, user.getUsername());
		rememberMe.setMaxAge(60 * 60 * 24);
		// rememberMe.setPath(request.getContextPath());
		wr.addCookie(rememberMe);
	}

	public void forgetUsername() {
		WebResponse wr = (WebResponse) (RequestCycle.get().getResponse());
		Cookie rememberMe = new Cookie(rememberMeKey, "");
		rememberMe.setMaxAge(60 * 60 * 24);
		// rememberMe.setPath(request.getContextPath());
		wr.addCookie(rememberMe);
	}

}
