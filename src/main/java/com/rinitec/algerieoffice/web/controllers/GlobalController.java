package com.rinitec.algerieoffice.web.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.Langage;

@ControllerAdvice
public class GlobalController {
	
	private HttpServletRequest request;
	
	@Autowired
	public GlobalController(HttpServletRequest request) {
		this.request = request;
	}
	
	@ModelAttribute("staticURL")
	public String getStaticURL() {
		return ConstraintesURL.URL_APPLICATION;
	}
	
	@ModelAttribute("langage")
	public Langage getLangauge() {
		return new Langage(request);
	}
	
}
