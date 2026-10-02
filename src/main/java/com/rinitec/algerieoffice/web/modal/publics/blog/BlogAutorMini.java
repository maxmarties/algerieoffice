package com.rinitec.algerieoffice.web.modal.publics.blog;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class BlogAutorMini implements Serializable {
	private static final long serialVersionUID = -3724223689992317276L;
	
	private final Long id;
	private final String urlAvatar;
	private final String autorname;
	private final String function;
	private final String biography;
	private final String email;
	private final String[] socialURL = new String[3];
	
	public BlogAutorMini(final Autor autor) {
		this.id = autor.getId();
		this.urlAvatar = autor.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + autor.getId() + "&type=" + AvatarType.autor 
				: "/static/picts/avatars/account-min.jpg";
		this.autorname = autor.getAutorname();
		this.function = autor.getFunction();
		this.biography = autor.getBiography();
		this.email = autor.getEmail();
		for (int i = 0; i < 3; i++) {
			this.socialURL[i] = autor.getSocial(i);
		}
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

	public String getFunction() {
		return function;
	}

	public String getBiography() {
		return biography;
	}

	public String getEmail() {
		return email;
	}

	public String[] getSocialURL() {
		return socialURL;
	}
	
	public boolean hasPresentSocial() {
		for (int i = 0; i < 3; i++) {
			if(!StringUtils.isEmpty(socialURL[i])) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "BlogAutorMini [id=" + id + ", urlAvatar=" + urlAvatar + ", autorname=" + autorname + ", function="
				+ function + ", biography=" + biography + ", email=" + email + ", socialURL=" + socialURL + "]";
	}

}
