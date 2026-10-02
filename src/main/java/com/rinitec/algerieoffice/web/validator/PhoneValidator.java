package com.rinitec.algerieoffice.web.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
	
	private static final String PHONE_PATTERN = "^[0]{1}[0-9]{8,9}$";

	@Override
	public void initialize(ValidPhone constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(final String phone, ConstraintValidatorContext context) {
		final Matcher matcher = Pattern.compile(PHONE_PATTERN).matcher(phone);
        return matcher.matches();
	}

}
