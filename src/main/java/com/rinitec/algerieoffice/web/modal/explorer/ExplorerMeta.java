package com.rinitec.algerieoffice.web.modal.explorer;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.CompanySeo;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.EventDetail;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.WorkDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerMeta implements Serializable {
	private static final long serialVersionUID = 5873512364452778686L;
	
	private final String mapsiteURL;
	private final String description;
	private final String keysword;
	private final String urlOverview;
	
	public ExplorerMeta(final CompanySeo companySeo, final Long companyId, final boolean hasOverview) {
		this.mapsiteURL = ConstraintesURL.getCompanyMapsiteURL(companySeo.getUrl());
		this.description = !StringUtils.isEmpty(companySeo.getDescription()) ? companySeo.getDescription() : null;
		this.keysword = companySeo.getKeysword();
		this.urlOverview = hasOverview ? ConstraintesURL.URL_AVATARS + "?postedId=" + companyId + "&type=" + AvatarType.extra : null;
	}
	
	public ExplorerMeta(final String companyURI, final Post post, final PostPhoto postPhoto) {
		this.mapsiteURL = ConstraintesURL.getPostMapsiteURL(companyURI, post.getIdentify());
		this.description = post.getDescription();
		this.keysword = !StringUtils.isEmpty(post.getKeysword()) ? post.getKeysword() : null;
		this.urlOverview = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(postPhoto.getPhotoUUID().toString());
	}
	
	public ExplorerMeta(final String companyURI, final Event event, final EventDetail eventDetail) {
		this.mapsiteURL = ConstraintesURL.getEventMapsiteURL(companyURI, event.getIdentify());
		this.description = eventDetail.getDescription();
		this.keysword = !StringUtils.isEmpty(event.getKeysword()) ? event.getKeysword() : null;
		this.urlOverview = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(eventDetail.getPhotoUUID().toString());
	}
	
	public ExplorerMeta(final String companyURI, final Work work, final WorkDetail workDetail) {
		this.mapsiteURL = ConstraintesURL.getWorkMapsiteURL(companyURI, work.getIdentify());
		this.description = workDetail.getDescription();
		this.keysword = work.getExpertise();
		this.urlOverview = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(workDetail.getPhotoUUID().toString());
	}
	
	public ExplorerMeta(final String companyURI, final Annonce annonce) {
		this.mapsiteURL = ConstraintesURL.getAnnonceMapsiteURL(companyURI, annonce.getIdentify());
		this.description = annonce.getDescription();
		this.keysword = annonce.getKeysword();
		this.urlOverview = null;
	}
	
	public ExplorerMeta(final String companyURI, final Employe employe) {
		this.mapsiteURL = ConstraintesURL.getEmployeMapsiteURL(companyURI, employe.getIdentify());
		this.description = employe.getDescription();
		this.keysword = employe.getKeysword();
		this.urlOverview = null;
	}

	public String getMapsiteURL() {
		return mapsiteURL;
	}

	public String getDescription() {
		return description;
	}

	public String getKeysword() {
		return keysword;
	}

	public String getUrlOverview() {
		return urlOverview;
	}
	
	public String[] buildKeysword() {
		return keysword.split(",");
	}
	
	public String escapeKeysword() {
		return keysword.replaceAll(",", ", ");
	}
	
	public String parsKey(final String keyword) {
		return keyword.replaceAll(" ", "\\+");
	}

	@Override
	public String toString() {
		return "ExplorerMetaHome [mapsiteURL=" + mapsiteURL + ", description=" + description + ", keysword=" + keysword
				+ ", urlOverview=" + urlOverview + "]";
	}

}
