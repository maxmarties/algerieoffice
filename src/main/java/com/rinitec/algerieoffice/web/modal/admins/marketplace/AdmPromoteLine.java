package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmPromoteLine implements Serializable {
	private static final long serialVersionUID = -4685585809536467920L;
	
	private final String id;
	private final String title;
	private final String description;
	private final String url;
	private final String company;
	private final String companyURL;
	private final boolean enabled;
	private final int view;
	private final int credit;
	private final int potentiel;
	private final boolean trashed;
	
	public AdmPromoteLine(final Promote promote, final String company, final String companyURL) {
		this.id = promote.getId().toString();
		this.title = promote.getTitle();
		this.description = promote.getDescription();
		this.url = promote.getUrl();
		this.company = company;
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(companyURL);
		this.enabled = promote.isEnabled();
		this.view = promote.getViewCount();
		this.credit = promote.getCreditCount();
		this.potentiel = promote.parseCreditCount();
		this.trashed = promote.getHasTrashed();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public String getCompany() {
		return company;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public boolean isEnabled() {
		return enabled;
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

	public boolean isTrashed() {
		return trashed;
	}

	@Override
	public String toString() {
		return "AdmPromoteLine [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url
				+ ", company=" + company + ", companyURL=" + companyURL + ", enabled=" + enabled + ", view=" + view
				+ ", credit=" + credit + ", potentiel=" + potentiel + ", trashed=" + trashed + "]";
	}

}
