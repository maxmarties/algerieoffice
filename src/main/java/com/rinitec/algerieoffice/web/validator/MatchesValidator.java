package com.rinitec.algerieoffice.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.rinitec.algerieoffice.web.form.register.PasswordForm;

public class MatchesValidator implements ConstraintValidator<ValidMatches, Object> {

	@Override
	public void initialize(final ValidMatches constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(final Object obj, ConstraintValidatorContext context) {
		final PasswordForm passForm = (PasswordForm) obj;
		return passForm.getPassword().equals(passForm.getMatching());
	}

}
