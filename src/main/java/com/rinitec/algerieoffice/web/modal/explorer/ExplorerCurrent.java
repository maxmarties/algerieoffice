package com.rinitec.algerieoffice.web.modal.explorer;

import java.io.Serializable;

public class ExplorerCurrent implements Serializable {
	private static final long serialVersionUID = 7529692500056619011L;
	
	private final Long companyId;
	private final String companyURL;
	private final Integer premium;
	private final boolean hasPreview;
	
	public ExplorerCurrent(final Long companyId, final String companyURL, final Integer premium, final boolean hasPreview) {
		this.companyId = companyId;
		this.companyURL = companyURL;
		this.premium = premium;
		this.hasPreview  = hasPreview;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public String getCompanyURL() {
		return companyURL;
	}
	
	public Integer getPremium() {
		return premium;
	}
	
	public boolean isHasPreview() {
		return hasPreview;
	}
	
	public boolean hasPremium() {
		return premium != 0;
	}

	@Override
	public String toString() {
		return "ExplorerCurrent [companyId=" + companyId + ", companyURL=" + companyURL + ", premium=" + premium
				+ ", hasPreview=" + hasPreview + "]";
	}

}
