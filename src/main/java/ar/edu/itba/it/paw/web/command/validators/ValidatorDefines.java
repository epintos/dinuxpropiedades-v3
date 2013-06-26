package ar.edu.itba.it.paw.web.command.validators;


public interface ValidatorDefines {

	static final String INFORMATION_MISSING_MSG = "emptyField";
	static final String VERY_LONG_STRING = "longString";
	static final String PASSWORD_NOT_MATCH = "passwordsNotMatch";
	static final String BAD_FORMAT = "badFormat";	
	static final String VERY_BIG_NUMBER = "bigNumber";
	static final String NEGATIVE_NUMBER = "negativeNumber";
	static final Integer MAX_REGISTER_FIELD_LENGTH = 50;
	static final Integer MAX_APARTMENT_LENGTH = 5;
	static final Integer MAX_DESCRIPTION_LENGTH = 512;
}
