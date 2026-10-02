package com.rinitec.algerieoffice.web.modal.admins;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;

public class CurrSocialFooter implements Serializable {
	private static final long serialVersionUID = -779624218299870637L;
	
	private final int facebook;
	private final int twitter;
	private final int linkedin;
	private final int youtube;
	private final boolean begginer;
	private final boolean promoted;
	
	private final String socialFrame;
	
	public CurrSocialFooter(final PremiumFormule premiumFormule) {
		if(premiumFormule != null) {
			this.facebook = premiumFormule.getStart();
			this.twitter = premiumFormule.getMedium();
			this.linkedin = premiumFormule.getPro();
			this.youtube = premiumFormule.getExpert();
			this.begginer = premiumFormule.isBegginer();
			this.promoted = premiumFormule.isPromoted();
			this.socialFrame = !StringUtils.isEmpty(premiumFormule.getSocialFrame()) ? premiumFormule.getSocialFrame() : null;
		} else {
			this.facebook = this.twitter = this.linkedin = this.youtube = 0;
			this.begginer = this.promoted = false;
			this.socialFrame = null;
		}
	}

	public int getFacebook() {
		return facebook;
	}

	public int getTwitter() {
		return twitter;
	}

	public int getLinkedin() {
		return linkedin;
	}

	public int getYoutube() {
		return youtube;
	}
	
	public boolean isBegginer() {
		return begginer;
	}
	
	public boolean isPromoted() {
		return promoted;
	}
	
	public String getSocialFrame() {
		return socialFrame;
	}

	@Override
	public String toString() {
		return "CurrSocialFooter [facebook=" + facebook + ", twitter=" + twitter + ", linkedin=" + linkedin
				+ ", youtube=" + youtube + ", begginer=" + begginer + ", promoted=" + promoted + ", socialFrame="
				+ socialFrame + "]";
	}
	
}
