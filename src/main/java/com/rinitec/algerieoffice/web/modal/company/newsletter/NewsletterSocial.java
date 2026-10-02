package com.rinitec.algerieoffice.web.modal.company.newsletter;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;

public class NewsletterSocial implements Serializable {
	private static final long serialVersionUID = -9172701969529432307L;
	
	private final String facebook;
	private final String twitter;
	private final String google;
	private final String linkedin;
	
	public NewsletterSocial(final CompanyLinked companyLinked) {
		if(companyLinked != null) {
			this.facebook = companyLinked.getFacebook();
			this.twitter = companyLinked.getTwitter();
			this.google = companyLinked.getGoogle();
			this.linkedin = companyLinked.getLinkedin();
		} else {
			this.facebook = this.twitter = this.google = this.linkedin = null;
		}
	}

	public String getFacebook() {
		return facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public String getGoogle() {
		return google;
	}

	public String getLinkedin() {
		return linkedin;
	}
	
	public String buildSocial(final String social) {
		switch(social) {
		case "facebook": return facebook;
		case "twitter": return twitter;
		case "google": return google;
		case "linkedin": return linkedin;
		}
		return null;
	}
	
	public boolean hasPresent() {
		return !StringUtils.isEmpty(facebook) || !StringUtils.isEmpty(twitter) || !StringUtils.isEmpty(google) || !StringUtils.isEmpty(linkedin);
	}

	@Override
	public String toString() {
		return "NewsletterSocial [facebook=" + facebook + ", twitter=" + twitter + ", google=" + google + ", linkedin="
				+ linkedin + "]";
	}

}
