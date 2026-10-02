package com.rinitec.algerieoffice.persistence.modal.company.overview;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "mainoverviews")
public class Mainoverview implements Serializable {
	private static final long serialVersionUID = 4900299855129758536L;
	
	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean hasOverview;
	
	@Column(nullable = true, length = 512)
	private String history;
	
	@Lob
	@Column(nullable = true)
	private byte[] presentation;
	
	public Mainoverview() {
		this.hasOverview = false;
	}
	
	public Mainoverview(final Long companyId) {
		this.companyId = companyId;
		this.hasOverview = false;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getHasOverview() {
		return hasOverview;
	}

	public void setHasOverview(Boolean hasOverview) {
		this.hasOverview = hasOverview;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public byte[] getPresentation() {
		return presentation;
	}

	public void setPresentation(byte[] presentation) {
		this.presentation = presentation;
	}

	@Override
	public String toString() {
		return "Mainoverview [companyId=" + companyId + ", hasOverview=" + hasOverview + ", history=" + history
				+ ", presentation=" + Arrays.toString(presentation) + "]";
	}

}
