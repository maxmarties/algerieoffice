package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmAnnonceLine implements Serializable {
	private static final long serialVersionUID = -7097005313004099431L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String modifiedDate;
	private final String company;
	private final String companyURL;
	private final int type;
	private final int clickCount;
	private final int workCount;
	private final boolean published;
	private final boolean trashed;
	
	public AdmAnnonceLine(final Annonce annonce, final String company, final String companyURL) {
		this.id = annonce.getId().toString();
		this.title = annonce.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplaceAnnonceURL(annonce.getIdentify(), companyURL);
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(annonce.getModifiedDate());
		this.company = company;
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(companyURL);
		this.type = annonce.getType();
		this.clickCount = annonce.getClickCount();
		this.workCount = annonce.getWorkCount();
		this.published = annonce.getHasPublished();
		this.trashed = annonce.getHasTrashed();
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

	public int getType() {
		return type;
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
		return "AdmAnnonceLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", modifiedDate="
				+ modifiedDate + ", company=" + company + ", companyURL=" + companyURL + ", type=" + type
				+ ", clickCount=" + clickCount + ", workCount=" + workCount + ", published=" + published + ", trashed="
				+ trashed + "]";
	}

}
