package com.rinitec.algerieoffice.web.validator;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

	private static final String URL_PATTERN = "^[a-z]{1}[a-z0-9\\-]{2,249}$";
    
    @Override
    public void initialize(ValidUrl constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(final String url, ConstraintValidatorContext context) {
    	final Matcher matcher = Pattern.compile(URL_PATTERN).matcher(url);
        return matcher.matches();
    }

}
