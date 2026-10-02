package com.rinitec.algerieoffice.web.modal;

import java.io.Serializable;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

public class Langage implements Serializable {
	private static final long serialVersionUID = 3034529848334331584L;

	private final String lang;
	private final String clazz;
	private final String dir;
	
	public Langage(final HttpServletRequest request) {
		this.lang = RequestContextUtils.getLocale(request).getLanguage();
		this.clazz = lang.equals("ar") ? "ar" : "fr";
		this.dir = lang.equals("ar") ? "rtl" : "ltr";
	}
	
	public Langage(final String language) {
		this.lang = language;
		this.clazz = language.equals("ar") ? "ar" : "fr";
		this.dir = language.equals("ar") ? "rtl" : "ltr";
	}
	
	public String getLang() {
		return lang;
	}
	
	public String getClazz() {
		return clazz;
	}
	
	public String getDir() {
		return dir;
	}

	@Override
	public String toString() {
		return "Langage [lang=" + lang + ", clazz=" + clazz + ", dir=" + dir + "]";
	}
	
}
