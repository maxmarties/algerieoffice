package com.rinitec.algerieoffice.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	public static final String EMAIL_PATTERN = "^[_a-z0-9-\\+]+(\\.[_a-z0-9-]+)*@" + "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";
	
	@Override
	public void initialize(ValidEmail constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(final String email, ConstraintValidatorContext context) {
		final Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return matcher.matches() && email.length() <= 100;
	}

}
