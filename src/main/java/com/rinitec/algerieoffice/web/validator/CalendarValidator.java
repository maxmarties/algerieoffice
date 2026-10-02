package com.rinitec.algerieoffice.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.joda.time.format.DateTimeFormat;

public class CalendarValidator implements ConstraintValidator<ValidCalendar, String> {

	@Override
	public void initialize(ValidCalendar constraintAnnotation) {
	}

	@Override
	public boolean isValid(final String date, ConstraintValidatorContext context) {
		try {
			DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(date);
			return true;
		} catch (UnsupportedOperationException | IllegalArgumentException e) {
			return false;
		}
	}

}
