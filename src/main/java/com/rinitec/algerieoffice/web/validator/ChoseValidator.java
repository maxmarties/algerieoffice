package com.rinitec.algerieoffice.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChoseValidator implements ConstraintValidator<ValidChose, Integer> {

	@Override
	public void initialize(ValidChose constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value != null && value != 0;
	}

}
