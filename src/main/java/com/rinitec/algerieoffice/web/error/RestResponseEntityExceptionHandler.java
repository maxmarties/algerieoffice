package com.rinitec.algerieoffice.web.error;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.ujson.GenericResponse;
import com.rinitec.algerieoffice.web.error.captcha.ReCaptchaInvalidException;
import com.rinitec.algerieoffice.web.error.captcha.ReCaptchaUnavailableException;
import com.rinitec.algerieoffice.web.error.exception.AccessAuthorityException;
import com.rinitec.algerieoffice.web.error.exception.AccessLeaderException;
import com.rinitec.algerieoffice.web.error.exception.AccessUploadException;
import com.rinitec.algerieoffice.web.error.exception.AlreadyExistException;
import com.rinitec.algerieoffice.web.error.exception.CompanymailExistException;
import com.rinitec.algerieoffice.web.error.exception.EmptyElementException;
import com.rinitec.algerieoffice.web.error.exception.InvalidFileException;
import com.rinitec.algerieoffice.web.error.exception.InvalidImageException;
import com.rinitec.algerieoffice.web.error.exception.InvalidPasswordException;
import com.rinitec.algerieoffice.web.error.exception.InvalidResourceException;
import com.rinitec.algerieoffice.web.error.exception.MaxKeyswordException;
import com.rinitec.algerieoffice.web.error.exception.MaxPlanException;
import com.rinitec.algerieoffice.web.error.exception.MobileExistException;
import com.rinitec.algerieoffice.web.error.exception.NotFoundException;
import com.rinitec.algerieoffice.web.error.exception.PhoneExistException;
import com.rinitec.algerieoffice.web.error.exception.SocialExistException;
import com.rinitec.algerieoffice.web.error.exception.UrlUnavailableException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private MessageSource messages;
	
	@Autowired
	public RestResponseEntityExceptionHandler(MessageSource messages) {
		this.messages = messages;
	}
	
	private Locale castLocale(final WebRequest request) {
		return RequestContextUtils.getLocale(((ServletWebRequest) request).getRequest());
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		final BindingResult result = ex.getBindingResult();
		final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final BindingResult result = ex.getBindingResult();
        final GenericResponse bodyOfResponse = new GenericResponse(result.getAllErrors(), "Invalid" + result.getObjectName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error.internal", null, 
				castLocale(request)), "Internal");
		return new ResponseEntity<Object>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({ReCaptchaInvalidException.class})
	public ResponseEntity<Object> handleReCaptchaInvalid(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "InvalidReCaptcha");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ReCaptchaUnavailableException.class})
	public ResponseEntity<Object> handleReCaptchaUnavailable(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("auth.recaptcha.message.unavailable", null, 
				castLocale(request)), "InvalidReCaptcha");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler({AlreadyExistException.class})
	public ResponseEntity<Object> handleAlreadyExist(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "AlreadyExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({PhoneExistException.class})
	public ResponseEntity<Object> handlePhoneExist(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "PhoneExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({MobileExistException.class})
	public ResponseEntity<Object> handleMobileExist(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "MobileExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({CompanymailExistException.class})
	public ResponseEntity<Object> handleCompanymailExist(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "CompanymailExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
        		castLocale(request)), "NotFound");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
	
	@ExceptionHandler({InvalidPasswordException.class})
    public ResponseEntity<Object> handleInvalidPassword(final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
        		castLocale(request)), "InvalidPassword");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
	
	@ExceptionHandler({InvalidImageException.class})
	public ResponseEntity<Object> handleInvalidImage(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "InvalidImage");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({MaxUploadSizeExceededException.class})
	public ResponseEntity<Object> handleMaxUpload(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error.size", null, 
				castLocale(request)), "InvalidSize");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({InvalidFileException.class})
	public ResponseEntity<Object> handleInvalidFile(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "InvalidFile");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({AccessUploadException.class})
	public ResponseEntity<Object> handleAccessUpload(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "AccessUpload");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler({UrlUnavailableException.class})
	public ResponseEntity<Object> handleUrlUnavailable(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "UrlUnavailable");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({AccessAuthorityException.class})
	public ResponseEntity<Object> handleAccessAuthority(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error.authority", null, 
				castLocale(request)), "AccessAuthority");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({InvalidResourceException.class})
	public ResponseEntity<Object> handleInvalidResource(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage("message.error.parsing", null, 
				castLocale(request)), "InvalidResource");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({SocialExistException.class})
	public ResponseEntity<Object> handleSocialExist(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), "SocialExist");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
	@ExceptionHandler({MaxKeyswordException.class})
	public ResponseEntity<Object> handleMaxKeysword(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "MaxKeysword");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({MaxPlanException.class})
	public ResponseEntity<Object> handleMaxPlan(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "MaxPlan");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyElementException.class})
	public ResponseEntity<Object> handleEmptyElement(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "EmptyElement");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({AccessLeaderException.class})
	public ResponseEntity<Object> handleAccessLeader(final RuntimeException ex, final WebRequest request) {
		final GenericResponse bodyOfResponse = new GenericResponse(messages.getMessage(ex.getMessage(), null, 
				castLocale(request)), "AccessLeader");
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
}
