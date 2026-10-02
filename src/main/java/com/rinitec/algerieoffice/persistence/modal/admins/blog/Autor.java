package com.rinitec.algerieoffice.persistence.modal.admins.blog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "autors")
public class Autor implements Serializable {
	private static final long serialVersionUID = 4753124946596034877L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "autor_id")
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String autorname;
	
	@Column(nullable = false, unique = true, length = 30)
	private String identify;
	
	@Column(nullable = false, length = 60)
	private String function;
	
	@Column(nullable = true, length = 512)
	private String biography;
	
	@Column(nullable = true, unique = true, length = 100)
	private String email;
	
	@Column(nullable = true, unique = true, length = 250)
	private String facebook;
	
	@Column(nullable = true, unique = true, length = 250)
	private String twitter;
	
	@Column(nullable = true, unique = true, length = 250)
	private String linkedin;
	
	@Column(nullable = false)
	private Boolean hasAvatar;
	
	public Autor() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAutorname() {
		return autorname;
	}

	public void setAutorname(String autorname) {
		this.autorname = autorname;
	}

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public Boolean getHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public String getSocial(int index) {
		switch(index) {
		case 0: return facebook;
		case 1: return twitter;
		case 2: return linkedin;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", autorname=" + autorname + ", identify=" + identify + ", function=" + function
				+ ", biography=" + biography + ", email=" + email + ", facebook=" + facebook + ", twitter=" + twitter
				+ ", linkedin=" + linkedin + ", hasAvatar=" + hasAvatar + "]";
	}

}
