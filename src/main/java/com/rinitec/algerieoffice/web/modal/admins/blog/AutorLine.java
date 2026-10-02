package com.rinitec.algerieoffice.web.modal.admins.blog;

import java.io.Serializable;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AutorLine implements Serializable {
	private static final long serialVersionUID = -4170141216906041530L;
	
	private final Long id;
	private final String urlAvatar;
	private final String autorname;
	private final String identifyURL;
	private final String function;
	private final String email;
	private final Long countBlog;
	
	public AutorLine(final Autor autor, final Long countBlog) {
		this.id = autor.getId();
		this.autorname = autor.getAutorname();
		this.identifyURL = ConstraintesURL.getBrowserBlogAutorURL(autor.getIdentify());
		this.function = autor.getFunction();
		this.email = autor.getEmail();
		this.countBlog = countBlog;
		this.urlAvatar = autor.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + autor.getId() + "&type=" + AvatarType.autor + "&width=34&height=34" 
				: "/static/picts/avatars/account_mini-min.jpg";
	}

	public Long getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getAutorname() {
		return autorname;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getFunction() {
		return function;
	}

	public String getEmail() {
		return email;
	}

	public Long getCountBlog() {
		return countBlog;
	}

	@Override
	public String toString() {
		return "AutorLine [id=" + id + ", urlAvatar=" + urlAvatar + ", autorname=" + autorname + ", identifyURL="
				+ identifyURL + ", function=" + function + ", email=" + email + ", countBlog=" + countBlog + "]";
	}

}
