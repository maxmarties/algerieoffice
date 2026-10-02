package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;

public class ExplorerInboxContact implements Serializable {
	private static final long serialVersionUID = -9208107724931019375L;
	
	private final Boolean hasEmbded;
	private final String urlEmbded;
	private final Integer briefcase;
	private final String[] socialMedias = new String[6];
	
	public ExplorerInboxContact(final CompanyLocation companyLocation, final Integer briefcase, final CompanyLinked companyLinked) {
		if(companyLocation != null) {
			this.hasEmbded = companyLocation.getHasEmpded();
			this.urlEmbded = companyLocation.getHasEmpded() ? companyLocation.getEmpded() : companyLocation.getUrlmap();
		} else {
			this.hasEmbded = null;
			this.urlEmbded = null;
		}
		this.briefcase = briefcase;
		if(companyLinked != null) {
			for (int i = 0; i < 6; i++) {
				this.socialMedias[i] = companyLinked.getSocialMedia(i);
			}
		}
	}

	public Boolean getHasEmbded() {
		return hasEmbded;
	}
	
	public String getUrlEmbded() {
		return urlEmbded;
	}
	
	public Integer getBriefcase() {
		return briefcase;
	}

	public String[] getSocialMedias() {
		return socialMedias;
	}
	
	public int stateBriefcase() {
		return briefcase == null || briefcase < 9 ? 1 : briefcase == 9 ? 2 : 3;
	}
	
	public boolean hasPresentSocialMedias() {
		for (int i = 0; i < 6; i++) {
			if(!StringUtils.isEmpty(socialMedias[i])) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ExplorerInboxContact [hasEmbded=" + hasEmbded + ", urlEmbded=" + urlEmbded + ", briefcase=" + briefcase
				+ ", socialMedias=" + socialMedias + "]";
	}

}
