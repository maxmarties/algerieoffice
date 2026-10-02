package com.rinitec.algerieoffice.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

	@Override
	public void initialize(ValidDate constraintAnnotation) {
	}

	@Override
	public boolean isValid(final String date, ConstraintValidatorContext context) {
		try {
			final DateTime dateTime = DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(date);
			return dateTime.isBeforeNow();
		} catch (UnsupportedOperationException | IllegalArgumentException e) {
			return false;
		}
	}

}
