package com.rinitec.algerieoffice.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ActivityValidator implements ConstraintValidator<ValidActivity, String> {
	
	private static final String CODE_PATTERN = "^[0-9]{6}$";

	@Override
	public void initialize(ValidActivity constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(final String code, ConstraintValidatorContext context) {
		final Matcher matcher = Pattern.compile(CODE_PATTERN).matcher(code);
        return matcher.matches();
	}

}
