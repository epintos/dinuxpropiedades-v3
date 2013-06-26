package ar.edu.itba.it.paw.web.publications;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.MinimumValidator;
import org.apache.wicket.validation.validator.StringValidator;

import ar.edu.itba.it.paw.domain.EntityModel;
import ar.edu.itba.it.paw.domain.publications.Currency;
import ar.edu.itba.it.paw.domain.publications.Operations;
import ar.edu.itba.it.paw.domain.publications.PropertyType;
import ar.edu.itba.it.paw.domain.publications.Publication;
import ar.edu.itba.it.paw.domain.publications.PublicationRepo;
import ar.edu.itba.it.paw.domain.publications.Services;
import ar.edu.itba.it.paw.web.WicketSession;
import ar.edu.itba.it.paw.web.base.EnumModel;
import ar.edu.itba.it.paw.web.base.SecuredPage;
import ar.edu.itba.it.paw.web.command.validators.ValidatorDefines;
import ar.edu.itba.it.paw.web.utils.PlaceholderBehavior;

public class UpdatePublicationPage extends SecuredPage {

	private EntityModel<Publication> publicationModel;

	private PropertyType propertyType;
	private Operations operationType;
	private String street;
	private Integer numbering;
	private Integer floor;
	private String apartment;
	private String neighbourhood;
	private Integer price;
	private Currency currency;
	private Integer age;
	private Integer coveredSurface;
	private Integer uncoveredSurface;
	private ArrayList<Services> services;
	private Integer roomsQty;
	private String description;

	@SpringBean
	private PublicationRepo publicationRepo;

	public UpdatePublicationPage() {
		this.services = new ArrayList<Services>();
		this.description = "";
	}

	public UpdatePublicationPage(Publication publication) {
		if (publication == null)
			throw new NullPointerException();
		this.publicationModel = new EntityModel<Publication>(Publication.class,
				publication);
		this.operationType = publication.getOperationType();
		this.street = publication.getStreet();
		this.numbering = publication.getNumbering();
		this.floor = publication.getFloor();
		this.apartment = publication.getApartment();
		this.neighbourhood = publication.getNeighbourhood();
		this.price = publication.getPrice();
		this.age = publication.getAge();
		this.coveredSurface = publication.getCoveredSurface();
		this.uncoveredSurface = publication.getUncoveredSurface();
		this.services = new ArrayList<Services>(publication.getServices());
		this.roomsQty = publication.getRoomsQty();
		this.description = publication.getDescription();
		this.currency = publication.getCurrency();
	}


	@Override
	protected void onInitialize() {
		super.onInitialize();
		setDefaultModel(new CompoundPropertyModel<UpdatePublicationPage>(this));
		buildForm();
	}

	private void buildForm() {

		Form<UpdatePublicationPage> form = new Form<UpdatePublicationPage>(
				"updatePublication") {

			@Override
			protected void onSubmit() {
				if (publicationModel == null) {
					WicketSession session = WicketSession.get();
					if (description == null)
						description = "";
					publicationModel = new EntityModel<Publication>(
							Publication.class, new Publication(
									session.getUser(), neighbourhood,
									coveredSurface, uncoveredSurface, roomsQty,
									description, age, street, numbering, floor,
									apartment, price, currency, operationType,
									propertyType, services));
					publicationRepo.add(publicationModel.getObject());
				} else {
					updateValues();
				}
				setResponsePage(new PublicationDetailPage(publication()));
			}
		};

		addFormComponents(form);

		add(form);

	}

	private void addFormComponents(Form<UpdatePublicationPage> form) {

		buildDropDowns(form);
		buildTextFields(form);

		// this dropdown can't be placed in #buildDropDowns(form) because it is
		// then needed together with floor and apartment textfields for the ajax
		// behaviour
		DropDownChoice<PropertyType> propertyTypeDrop = new DropDownChoice<PropertyType>(
				"propertyType", EnumModel.create(PropertyType.class));
		propertyTypeDrop.setRequired(true);
		form.add(new ComponentFeedbackPanel("propertyTypeError",
				propertyTypeDrop));
		form.add(propertyTypeDrop);

		final TextField<Integer> floor = new TextField<Integer>("floor");
		floor.setOutputMarkupId(true).add(new PlaceholderBehavior(getString("floor")));;
		floor.setRequired(true);
		floor.setEnabled(propertyType != null ? propertyType
				.equals(PropertyType.APARTMENT) : false);
		form.add(floor);
		form.add(new ComponentFeedbackPanel("floorError", floor));

		final TextField<Integer> apartment = new TextField<Integer>("apartment");
		form.add(apartment.add(StringValidator
				.maximumLength(ValidatorDefines.MAX_APARTMENT_LENGTH)));
		apartment.setOutputMarkupId(true).add(new PlaceholderBehavior(getString("apartment")));;
		apartment.setRequired(true);
		apartment.setEnabled(propertyType != null ? propertyType
				.equals(PropertyType.APARTMENT) : false);
		form.add(new ComponentFeedbackPanel("apartmentError", apartment));

		propertyTypeDrop.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				floor.setEnabled(propertyType.equals(PropertyType.APARTMENT));
				apartment.setEnabled(propertyType
						.equals(PropertyType.APARTMENT));
				target.add(floor);
				target.add(apartment);
			}
		});

		form.add(new Button("saveChanges", new ResourceModel("saveChanges")));
	}

	private <T> void buildTextFields(Form<T> form) {
		TextField<Integer> street = new TextField<Integer>("street");
		form.add(street
				.setRequired(true).add(new PlaceholderBehavior(getString("street")))
				.add(StringValidator
						.maximumLength(ValidatorDefines.MAX_REGISTER_FIELD_LENGTH)));
		form.add(new ComponentFeedbackPanel("streetError", street));

		TextField<Integer> numbering = new TextField<Integer>("numbering");
		form.add(numbering.setRequired(true).add(new PlaceholderBehavior(getString("numbering"))));
		form.add(new ComponentFeedbackPanel("numberingError", numbering));

		TextField<Integer> neighbourhood = new TextField<Integer>(
				"neighbourhood");
		form.add(neighbourhood
				.setRequired(true).add(new PlaceholderBehavior(getString("neighbourhood")))
				.add(StringValidator
						.maximumLength(ValidatorDefines.MAX_REGISTER_FIELD_LENGTH)));
		form.add(new ComponentFeedbackPanel("neighbourhoodError", neighbourhood));

		TextField<Integer> price = new TextField<Integer>("price");
		form.add(price.setRequired(true).add(new MinimumValidator<Integer>(0)));
		form.add(new ComponentFeedbackPanel("priceError", price));

		TextField<Integer> roomsQty = new TextField<Integer>("roomsQty");
		form.add(roomsQty.setRequired(true).add(
				new MinimumValidator<Integer>(0)));
		form.add(new ComponentFeedbackPanel("roomsQtyError", roomsQty));

		TextField<Integer> age = new TextField<Integer>("age",
				new PropertyModel<Integer>(this, "age"));
		form.add(age.setRequired(true).add(new MinimumValidator<Integer>(0)));
		form.add(new ComponentFeedbackPanel("ageError", age));

		TextField<Integer> uncoveredSurface = new TextField<Integer>(
				"uncoveredSurface");
		form.add(uncoveredSurface.setRequired(true).add(
				new MinimumValidator<Integer>(0)));
		form.add(new ComponentFeedbackPanel("uncoveredSurfaceError",
				uncoveredSurface));

		TextField<Integer> coveredSurface = new TextField<Integer>(
				"coveredSurface");
		form.add(coveredSurface.setRequired(true).add(
				new MinimumValidator<Integer>(0)));
		form.add(new ComponentFeedbackPanel("coveredSurfaceError",
				coveredSurface));

		form.add(new CheckBoxMultipleChoice<Services>("services", EnumModel
				.create(Services.class)));

		TextArea<String> description = new TextArea<String>("description");
		form.add(description.add(StringValidator
				.maximumLength(ValidatorDefines.MAX_DESCRIPTION_LENGTH)));
		form.add(new ComponentFeedbackPanel("descriptionError", description));
	}

	private <T> void buildDropDowns(Form<T> form) {
		DropDownChoice<Operations> operationDrop = new DropDownChoice<Operations>(
				"operationType", EnumModel.create(Operations.class));

		operationDrop.setRequired(true);
		form.add(new ComponentFeedbackPanel("operationTypeError", operationDrop));
		form.add(operationDrop);

		DropDownChoice<Currency> currencyDrop = new DropDownChoice<Currency>(
				"currency", EnumModel.create(Currency.class));

		currencyDrop.setRequired(true);
		form.add(new ComponentFeedbackPanel("currencyError", currencyDrop));
		form.add(currencyDrop);
	}

	private void updateValues() {
		Publication pub = publication();
		pub.setAge(age);
		pub.setApartment(apartment);
		pub.setCoveredSurface(coveredSurface);
		pub.setDescription(description);
		pub.setFloor(floor);
		pub.setNeighbourhood(neighbourhood);
		pub.setNumbering(numbering);
		pub.setOperationType(operationType);
		pub.setPrice(price);
		pub.setPropertyType(propertyType);
		pub.setRoomsQty(roomsQty);
		pub.setServices(services);
		pub.setStreet(street);
		pub.setUncoveredSurface(uncoveredSurface);
		pub.setCurrency(currency);
		publicationModel = new EntityModel<Publication>(Publication.class, pub);
	}

	private Publication publication() {
		return publicationModel.getObject();
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		if (publicationModel != null)
			publicationModel.detach();
	}
}
