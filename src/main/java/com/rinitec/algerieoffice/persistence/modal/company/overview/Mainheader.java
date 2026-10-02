package com.rinitec.algerieoffice.persistence.modal.company.overview;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mainheaders")
public class Mainheader implements Serializable {
	private static final long serialVersionUID = -1202474233158076766L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean hasCover;
	
	@Column(nullable = false, length = 3)
	private String canva;
	
	@Column(nullable = false, length = 30)
	private String canvaColor;
	
	@Column(nullable = false, length = 30)
	private String textColor;
	
	public Mainheader() {
	}
	
	public Mainheader(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getHasCover() {
		return hasCover;
	}

	public void setHasCover(Boolean hasCover) {
		this.hasCover = hasCover;
	}

	public String getCanva() {
		return canva;
	}

	public void setCanva(String canva) {
		this.canva = canva;
	}

	public String getCanvaColor() {
		return canvaColor;
	}

	public void setCanvaColor(String canvaColor) {
		this.canvaColor = canvaColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	@Override
	public String toString() {
		return "Mainheader [companyId=" + companyId + ", hasCover=" + hasCover + ", canva=" + canva + ", canvaColor="
				+ canvaColor + ", textColor=" + textColor + "]";
	}
	
}
