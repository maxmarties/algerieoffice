package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.CampaignTarget;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmOrderCampagne implements Serializable {
	private static final long serialVersionUID = 7840142163336893057L;
	
	private final String title;
	private final String identifyURL;
	private final String company;
	private final String companyURL;
	private final int type;
	private final int sector;
	private final int wilaya;
	private final Integer pack;
	private final String amount;
	private final String fileUrl;
	
	public AdmOrderCampagne(final Campaign campaign, final CampaignTarget campaignTarget, final DocumentOrder documentOrder, 
			final String tradename, final String url, final Post post) {
		this.title = post != null ? post.getTitle() : "--";
		this.identifyURL = post != null ? ConstraintesURL.getMarketplacePostURL(post.getIdentify(), url) : null;
		this.company = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.sector = campaignTarget.getSector() == null ? 0 : campaignTarget.getSector();
		this.wilaya = campaignTarget.getWilaya() == null ? 0 : campaignTarget.getWilaya();
		this.pack = ConstraintesForm.CAMPAIGNS_CREDIT[documentOrder.getPack() - 1];
		this.amount = ConstraintesForm.CAMPAIGNS_FORMULE[documentOrder.getPack() - 1];
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
	}
	
	public AdmOrderCampagne(final Campaign campaign, final CampaignTarget campaignTarget, final DocumentOrder documentOrder, 
			final String tradename, final String url, final Annonce annonce) {
		this.title = annonce != null ? annonce.getTitle() : "--";
		this.identifyURL = annonce != null ? ConstraintesURL.getMarketplaceAnnonceURL(annonce.getIdentify(), url) : null;
		this.company = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.sector = campaignTarget.getSector() == null ? 0 : campaignTarget.getSector();
		this.wilaya = campaignTarget.getWilaya() == null ? 0 : campaignTarget.getWilaya();
		this.pack = ConstraintesForm.CAMPAIGNS_CREDIT[documentOrder.getPack() - 1];
		this.amount = ConstraintesForm.CAMPAIGNS_FORMULE[documentOrder.getPack() - 1];
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
	}
	
	public AdmOrderCampagne(final Campaign campaign, final CampaignTarget campaignTarget, final DocumentOrder documentOrder, 
			final String tradename, final String url, final Event event) {
		this.title = event != null ? event.getTitle() : "--";
		this.identifyURL = event != null ? ConstraintesURL.getMarketplaceEventURL(event.getIdentify(), url) : null;
		this.company = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.sector = campaignTarget.getSector() == null ? 0 : campaignTarget.getSector();
		this.wilaya = campaignTarget.getWilaya() == null ? 0 : campaignTarget.getWilaya();
		this.pack = ConstraintesForm.CAMPAIGNS_CREDIT[documentOrder.getPack() - 1];
		this.amount = ConstraintesForm.CAMPAIGNS_FORMULE[documentOrder.getPack() - 1];
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getCompany() {
		return company;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public int getType() {
		return type;
	}

	public int getSector() {
		return sector;
	}

	public int getWilaya() {
		return wilaya;
	}

	public Integer getPack() {
		return pack;
	}

	public String getAmount() {
		return amount;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	@Override
	public String toString() {
		return "AdmOrderCampagne [title=" + title + ", identifyURL=" + identifyURL + ", company=" + company
				+ ", companyURL=" + companyURL + ", type=" + type + ", sector=" + sector + ", wilaya=" + wilaya
				+ ", pack=" + pack + ", amount=" + amount + ", fileUrl=" + fileUrl + "]";
	}

}
