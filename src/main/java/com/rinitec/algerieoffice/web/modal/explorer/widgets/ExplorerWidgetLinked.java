package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLinked;
import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyLocation;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.LinkedWebsite;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetLinked implements Serializable {
	private static final long serialVersionUID = -4141882436149305264L;
	
	private final Boolean hasEmbded;
	private final String urlEmbded;
	private final List<String> websiteNames = new ArrayList<String>();
	private final List<String> websiteUrls = new ArrayList<String>();
	private final List<Integer> websiteTypes = new ArrayList<Integer>();
	private final List<String> websitePhotos = new ArrayList<String>();
	private final String[] socialMedias = new String[6];
	
	public ExplorerWidgetLinked(final CompanyLocation companyLocation, final CompanyLinked companyLinked) {
		if(companyLocation != null) {
			this.hasEmbded = companyLocation.getHasEmpded();
			this.urlEmbded = companyLocation.getHasEmpded() ? companyLocation.getEmpded() : companyLocation.getUrlmap();
		} else {
			this.hasEmbded = null;
			this.urlEmbded = null;
		}
		if(companyLinked != null) {
			final List<LinkedWebsite> linkedWebsites = new ArrayList<LinkedWebsite>(companyLinked.getWebsites());
			for (final LinkedWebsite linkedWebsite : linkedWebsites) {
				this.websiteNames.add(linkedWebsite.getName());
				this.websiteUrls.add(linkedWebsite.getUrl());
				this.websiteTypes.add(linkedWebsite.getType());
				this.websitePhotos.add(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(linkedWebsite.getPhotoUUID().toString()));
			}
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

	public List<String> getWebsiteNames() {
		return websiteNames;
	}

	public List<String> getWebsiteUrls() {
		return websiteUrls;
	}

	public List<Integer> getWebsiteTypes() {
		return websiteTypes;
	}

	public List<String> getWebsitePhotos() {
		return websitePhotos;
	}

	public String[] getSocialMedias() {
		return socialMedias;
	}
	
	public boolean isEmptySocialMedia() {
		for (int i = 0; i < 6; i++) {
			if(!StringUtils.isEmpty(socialMedias[i])) return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetLinked [hasEmbded=" + hasEmbded + ", urlEmbded=" + urlEmbded + ", websiteNames="
				+ websiteNames + ", websiteUrls=" + websiteUrls + ", websiteTypes=" + websiteTypes + ", websitePhotos="
				+ websitePhotos + ", socialMedias=" + socialMedias + "]";
	}

}
