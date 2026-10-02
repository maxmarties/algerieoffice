package com.rinitec.algerieoffice.web.modal.publics.companies;

import java.io.Serializable;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CompanySimultudeLine implements Serializable {
	private static final long serialVersionUID = -8087255265006747304L;
	
	private final Long companyId;
	private final String tradename;
	private final String urlCompany;
	
	public CompanySimultudeLine(final Long companyId, final String tradename, final String url, final Long forOrder1) {
		this.companyId = companyId;
		this.tradename = tradename;
		this.urlCompany = ConstraintesURL.getCompanyExplorerURL(url);
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrlCompany() {
		return urlCompany;
	}

	@Override
	public String toString() {
		return "CompanySimultudeLine [companyId=" + companyId + ", tradename=" + tradename + ", urlCompany="
				+ urlCompany + "]";
	}

}
