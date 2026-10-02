package com.rinitec.algerieoffice.web.modal.feedback;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CampaignFeedback implements Serializable {
	private static final long serialVersionUID = -4911478363153518272L;
	
	private final String id;
	private final String urlAvatar;
	private final String title;
	private final String identifyURL;
	private final String companyURL;
	private final DateTime modifiedDate;
	private final int type;
	
	public CampaignFeedback(final Campaign campaign, final String urlAvatar, final String url, final Post post) {
		this.id = campaign.getId().toString();
		this.urlAvatar = urlAvatar;
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePostURL(post.getIdentify(), url);
		this.modifiedDate = post.getModifiedDate();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.type = post.getService() ? 2 : 1;
	}
	
	public CampaignFeedback(final Campaign campaign, final String urlAvatar, final String url, final Annonce annonce) {
		this.id = campaign.getId().toString();
		this.urlAvatar = urlAvatar;
		this.title = annonce.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceAnnonceURL(annonce.getIdentify(), url);
		this.modifiedDate = annonce.getModifiedDate();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.type = annonce.getType() + 2;
	}
	
	public CampaignFeedback(final Campaign campaign, final String urlAvatar, final String url, final Event event) {
		this.id = campaign.getId().toString();
		this.urlAvatar = urlAvatar;
		this.title = event.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceEventURL(event.getIdentify(), url);
		this.modifiedDate = event.getModifiedDate();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.type = 8;
	}

	public String getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getCompanyURL() {
		return companyURL;
	}
	
	public DateTime getModifiedDate() {
		return modifiedDate;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "CampaignFeedback [id=" + id + ", urlAvatar=" + urlAvatar + ", title=" + title + ", identifyURL="
				+ identifyURL + ", modifiedDate=" + modifiedDate + ", companyURL=" + companyURL + ", type=" + type + "]";
	}

}
