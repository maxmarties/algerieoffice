package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.CampaignTarget;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmCampaignLine implements Serializable {
	private static final long serialVersionUID = 2118782453922858091L;
	
	private final String id;
	private final String company;
	private final String companyURL;
	private final int type;
	private final int clic;
	private final int view;
	private final int credit;
	private final int potentiel;
	private final int sector;
	private final int wilaya;
	private final boolean enabled;
	
	public AdmCampaignLine(final Campaign campaign, final CampaignTarget campaignTarget, final String company, final String companyURL) {
		this.id = campaign.getId().toString();
		this.company = company;
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(companyURL);
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.clic = campaign.getClickCount();
		this.view = campaign.getViewCount();
		this.credit = campaign.getCreditCount();
		this.potentiel = campaign.parseCreditCount();
		this.sector = campaignTarget.getSector() == null ? 0 : campaignTarget.getSector();
		this.wilaya = campaignTarget.getWilaya() == null ? 0 : campaignTarget.getWilaya();
		this.enabled = campaign.isEnabled();
	}

	public String getId() {
		return id;
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

	public int getClic() {
		return clic;
	}

	public int getView() {
		return view;
	}

	public int getCredit() {
		return credit;
	}

	public int getPotentiel() {
		return potentiel;
	}
	
	public int getSector() {
		return sector;
	}
	
	public int getWilaya() {
		return wilaya;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "AdmCampaignLine [id=" + id + ", company=" + company + ", companyURL=" + companyURL + ", type=" + type
				+ ", clic=" + clic + ", view=" + view + ", credit=" + credit + ", potentiel=" + potentiel + ", sector="
				+ sector + ", wilaya=" + wilaya + ", enabled=" + enabled + "]";
	}

}
