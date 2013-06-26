package ar.edu.itba.it.paw.web.users;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.http.WebRequest;

import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.utils.PlaceholderBehavior;

public class LogInPanel extends Panel {

	private transient String username;
	private transient String password;
	private transient boolean rememberUsername;
	private transient boolean rememberLogin;
	private static String keepMeLoggedKey = "KeepMeLogged";
	private static String rememberMeKey = "RememberMePlz";

	public LogInPanel(String id) {
		super(id);
		add(new Link<Void>("register") {
			@Override
			public void onClick() {
				setResponsePage(new RegisterPage());
			}
		});

		add(new Link<Void>("viewAgencies") {
			@Override
			public void onClick() {
				setResponsePage(new Agencies());
			}
		});

		Form<LogInPanel> form = new Form<LogInPanel>("logInForm",
				new CompoundPropertyModel<LogInPanel>(this)) {

			@Override
			protected void onSubmit() {
				WicketSession session = WicketSession.get();

				if (session.signIn(username, password)) {
					if (rememberUsername) {
						session.setRememberMe();
					} else {
						session.forgetUsername();
					}

					if (rememberLogin) {
						session.setKeepMeLogged();
					}
					if (!continueToOriginalDestination()) {
						setResponsePage(getApplication().getHomePage());
					}
				} else {
					error(getString("invalidCredentials"));
				}
			}
		};

		rememberUsername = analyzeRememberMe();
		
		form.add(new CheckBox("rememberUsername"));
		form.add(new CheckBox("rememberLogin"));
		form.add(new TextField<String>("username").setRequired(true).add(new PlaceholderBehavior(getString("user"))));
		form.add(new PasswordTextField("password").add(new PlaceholderBehavior(getString("password"))));
		form.add(new Button("login", new ResourceModel("login")));
		addFeedbackPanel(form);
		add(form);
	}

	private <T> void addFeedbackPanel(Form<T> form) {
		IFeedbackMessageFilter filter = new ContainerFeedbackMessageFilter(form);
		add(new FeedbackPanel("errorPanel", filter));
	}

	private boolean analyzeRememberMe() {
		List<Cookie> cookies = ((WebRequest) getRequestCycle().getRequest())
				.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(rememberMeKey)) {
				username = cookie.getValue();
				return !username.isEmpty();
			}
		}
		return false;
	}

}
