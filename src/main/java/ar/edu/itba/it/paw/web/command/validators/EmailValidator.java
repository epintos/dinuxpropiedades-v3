package ar.edu.itba.it.paw.web.command.validators;

import org.apache.wicket.util.lang.Classes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

public class EmailValidator implements IValidator<String> {

	@SuppressWarnings("unused")
	private String email;

	public EmailValidator(String email) {
		this.email = email;
	}

	public void validate(IValidatable<String> validatable) {
		String email = validatable.getValue();
		if (!email.matches("[a-z0-9.-_]*@[a-z0-9.-_]*")) {
			ValidationError error = new ValidationError();
			error.addMessageKey(resourceKey());
			error.setVariable("email", email);
			validatable.error(error);
		}
	}

	protected String resourceKey() {
		return Classes.simpleName(EmailValidator.class);
	}
}
