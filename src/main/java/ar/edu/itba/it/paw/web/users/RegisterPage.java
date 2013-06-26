package ar.edu.itba.it.paw.web.users;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.captcha.CaptchaImageResource;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.StringValidator;

import ar.edu.itba.it.paw.domain.photos.Photo;
import ar.edu.itba.it.paw.domain.users.DuplicatedUserException;
import ar.edu.itba.it.paw.domain.users.User;
import ar.edu.itba.it.paw.domain.users.UserRepo;
import ar.edu.itba.it.paw.domain.users.UserType;
import ar.edu.itba.it.paw.web.HomePage;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.BasePage;
import ar.edu.itba.it.paw.web.base.EnumModel;
import ar.edu.itba.it.paw.web.command.validators.EmailValidator;
import ar.edu.itba.it.paw.web.command.validators.ValidatorDefines;
import ar.edu.itba.it.paw.web.utils.PlaceholderBehavior;

@SuppressWarnings("serial")
public class RegisterPage extends BasePage {

	private transient String name;
	private transient String surname;
	private transient String email;
	private transient String phone;
	private transient String username;
	private transient String password;
	@SuppressWarnings("unused")
	private transient String confPassword;
	private transient String imagePass;
	@SuppressWarnings("unused")
	private transient String code;
	private transient UserType userType;
	private transient List<FileUpload> file;

	private TextField<String> usernameComponent;

	@SpringBean
	private UserRepo userRepo;

	public RegisterPage() {

		Form<RegisterPage> registerForm = new Form<RegisterPage>(
				"registerForm", new CompoundPropertyModel<RegisterPage>(this)) {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				User user = new User(name, surname, email, username, password,
						phone, userType);
				if (userType.equals(UserType.AGENCY) && file != null
						&& file.size() != 0) {
					user.setPhoto(new Photo(file.get(0).getBytes(), user));
				}
				try {
					userRepo.add(user);
				} catch (DuplicatedUserException e) {
					usernameComponent.error(getString("usernameExists"));
					return;
				}
				WicketSession session = WicketSession.get();
				session.signIn(user.getUsername(), user.getPassword());
				setResponsePage(new HomePage());
			}
		};

		DropDownChoice<UserType> userDrop = new DropDownChoice<UserType>(
				"userType", EnumModel.create(UserType.class));

		registerForm.add(userDrop.setRequired(true));
		registerForm.add(new ComponentFeedbackPanel("userTypeError", userDrop));

		TextField<String> name = new TextField<String>("name");
		registerForm
				.add(name
						.setRequired(true)
						.add(StringValidator
								.maximumLength(ValidatorDefines.MAX_REGISTER_FIELD_LENGTH)));
		registerForm.add(new ComponentFeedbackPanel("nameError", name));

		final TextField<String> surname = new TextField<String>("surname");
		surname.setOutputMarkupId(true);
		registerForm
				.add(surname
						.setRequired(true)
						.add(StringValidator
								.maximumLength(ValidatorDefines.MAX_REGISTER_FIELD_LENGTH))
						.setOutputMarkupId(true));
		registerForm.add(new ComponentFeedbackPanel("surnameError", surname));

		TextField<String> email = new TextField<String>("email");
		registerForm
				.add(email
						.setRequired(true).add(new PlaceholderBehavior(getString("emailExample")))
						.add(StringValidator
								.maximumLength(ValidatorDefines.MAX_REGISTER_FIELD_LENGTH)));
		email.add(new EmailValidator(this.email));
		registerForm.add(new ComponentFeedbackPanel("emailError", email));

		TextField<String> phone = new TextField<String>("phone");
		registerForm.add(phone.setRequired(true));
		registerForm.add(new ComponentFeedbackPanel("phoneError", phone));

		usernameComponent = new TextField<String>("username");
		registerForm.add(usernameComponent.setRequired(true));
		registerForm.add(new ComponentFeedbackPanel("usernameError",
				usernameComponent));

		PasswordTextField pass = new PasswordTextField("password");
		registerForm.add(pass.setRequired(true));
		registerForm.add(new ComponentFeedbackPanel("passwordError", pass));

		PasswordTextField passConf = new PasswordTextField("confPassword");
		registerForm.add(passConf.setRequired(true));
		registerForm.add(new EqualInputValidator(pass, passConf));
		registerForm.add(new ComponentFeedbackPanel("passwordConfirmError",
				passConf));

		registerForm.add(new Button("register", new ResourceModel("register")));

		final FileUploadField fileUploadField = new FileUploadField("file");
		registerForm.add(fileUploadField.setOutputMarkupId(true));
		registerForm.setMaxSize(Bytes.kilobytes(10000));
		registerForm.setMultiPart(true);
		userDrop.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				fileUploadField.setEnabled(userType.equals(UserType.AGENCY));
				surname.setEnabled(userType.equals(UserType.USER));
				target.add(fileUploadField);
				target.add(surname);
			}
		});

		buildCaptcha(registerForm);

		add(registerForm);

	}

	private void buildCaptcha(Form<RegisterPage> registerForm) {
		imagePass = randomString(3, 5);
		CaptchaImageResource captchaImageResource = new CaptchaImageResource(
				imagePass, 60, 40);
		registerForm.add(new NonCachingImage("captchaImage", captchaImageResource));
		RequiredTextField<String> code = new RequiredTextField<String>("code",
				new PropertyModel<String>(this, "code"));
		registerForm.add(code.add(new CaptchaValidator()));
		 registerForm.add(new ComponentFeedbackPanel("captchaError", code));
	}

	private int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}

	private String randomString(int min, int max) {
		int num = randomInt(min, max);
		byte b[] = new byte[num];
		for (int i = 0; i < num; i++)
			b[i] = (byte) randomInt('a', 'z');
		return new String(b);
	}

	private class CaptchaValidator implements IValidator<String> {
		public void validate(IValidatable<String> validatable) {
			if (!validatable.getValue().equals(imagePass)) {
				validatable.error(new ValidationError()
						.addMessageKey("incorrectCaptcha"));
			}
		}
	}
}
