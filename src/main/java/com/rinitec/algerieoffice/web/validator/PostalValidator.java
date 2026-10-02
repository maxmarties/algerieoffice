package com.rinitec.algerieoffice.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostalValidator implements ConstraintValidator<ValidPostal, String> {
	
	private static final String POSTAL_PATTERN = "^[0-9]{5}$";

	@Override
	public void initialize(ValidPostal constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(final String postal, ConstraintValidatorContext context) {
		final Matcher matcher = Pattern.compile(POSTAL_PATTERN).matcher(postal);
        return matcher.matches();
	}

}
