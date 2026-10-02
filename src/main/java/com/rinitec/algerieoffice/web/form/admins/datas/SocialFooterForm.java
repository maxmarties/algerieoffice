package com.rinitec.algerieoffice.web.form.admins.datas;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;

public class SocialFooterForm implements Serializable {
	private static final long serialVersionUID = -4667266177631860985L;
	
	@NotNull
	private Integer facebook;
	
	@NotNull
	private Integer twitter;
	
	@NotNull
	private Integer linkedin;
	
	@NotNull
	private Integer youtube;
	
	private boolean begginer;
	private boolean promoted;
	
	private String socialForm;
	
	public SocialFooterForm() {
	}
	
	public SocialFooterForm(final PremiumFormule premiumFormule) {
		if(premiumFormule != null) {
			this.facebook = premiumFormule.getStart();
			this.twitter = premiumFormule.getMedium();
			this.linkedin = premiumFormule.getPro();
			this.youtube = premiumFormule.getExpert();
			this.begginer = premiumFormule.isBegginer();
			this.promoted = premiumFormule.isPromoted();
			this.socialForm = premiumFormule.getSocialFrame();
		} else {
			this.facebook = this.twitter = this.linkedin = this.youtube = 0;
			this.begginer = this.promoted = false;
		}
	}

	public Integer getFacebook() {
		return facebook;
	}

	public void setFacebook(Integer facebook) {
		this.facebook = facebook;
	}

	public Integer getTwitter() {
		return twitter;
	}

	public void setTwitter(Integer twitter) {
		this.twitter = twitter;
	}

	public Integer getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(Integer linkedin) {
		this.linkedin = linkedin;
	}

	public Integer getYoutube() {
		return youtube;
	}

	public void setYoutube(Integer youtube) {
		this.youtube = youtube;
	}
	
	public boolean isBegginer() {
		return begginer;
	}
	
	public void setBegginer(boolean begginer) {
		this.begginer = begginer;
	}
	
	public boolean isPromoted() {
		return promoted;
	}
	
	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}
	
	public String getSocialForm() {
		return socialForm;
	}
	
	public void setSocialForm(String socialForm) {
		this.socialForm = socialForm;
	}

	@Override
	public String toString() {
		return "SocialFooterForm [facebook=" + facebook + ", twitter=" + twitter + ", linkedin=" + linkedin
				+ ", youtube=" + youtube + ", begginer=" + begginer + ", promoted=" + promoted + ", socialForm="
				+ socialForm + "]";
	}

}
