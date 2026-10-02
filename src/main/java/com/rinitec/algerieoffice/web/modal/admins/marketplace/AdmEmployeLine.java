package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmEmployeLine implements Serializable {
	private static final long serialVersionUID = -128722039512648726L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String modifiedDate;
	private final String company;
	private final String companyURL;
	private final int contract;
	private final int clickCount;
	private final int workCount;
	private final boolean published;
	private final boolean trashed;
	
	public AdmEmployeLine(final Employe employe, final String company, final String companyURL) {
		this.id = employe.getId().toString();
		this.title = employe.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceEmployeURL(employe.getIdentify(), companyURL);
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(employe.getModifiedDate());
		this.company = company;
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(companyURL);
		this.contract = employe.getContract();
		this.clickCount = employe.getClickCount();
		this.workCount = employe.getWorkCount();
		this.published = employe.getHasPublished();
		this.trashed = employe.getHasTrashed();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getCompany() {
		return company;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public int getContract() {
		return contract;
	}

	public int getClickCount() {
		return clickCount;
	}

	public int getWorkCount() {
		return workCount;
	}

	public boolean isPublished() {
		return published;
	}

	public boolean isTrashed() {
		return trashed;
	}
	
	public boolean enabled() {
		return published && !trashed;
	}

	@Override
	public String toString() {
		return "AdmEmployeLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", modifiedDate="
				+ modifiedDate + ", company=" + company + ", companyURL=" + companyURL + ", contract=" + contract
				+ ", clickCount=" + clickCount + ", workCount=" + workCount + ", published=" + published + ", trashed="
				+ trashed + "]";
	}
	
}
