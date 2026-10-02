package com.rinitec.algerieoffice.persistence.modal.company.manage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "maindisplays")
public class Maindisplay implements Serializable {
	private static final long serialVersionUID = -6953644151308844977L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 4)
	private String mainmenu;
	
	@Column(nullable = false, length = 6)
	private String society;
	
	@Column(nullable = false, length = 6)
	private String mainsidbar;
	
	@Column(nullable = false, length = 7)
	private String mainfooter;
	
	@Column(nullable = false, length = 24)
	private String display;
	
	public Maindisplay() {
	}
	
	public Maindisplay(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getMainmenu() {
		return mainmenu;
	}

	public void setMainmenu(String mainmenu) {
		this.mainmenu = mainmenu;
	}

	public String getSociety() {
		return society;
	}

	public void setSociety(String society) {
		this.society = society;
	}

	public String getMainsidbar() {
		return mainsidbar;
	}

	public void setMainsidbar(String mainsidbar) {
		this.mainsidbar = mainsidbar;
	}

	public String getMainfooter() {
		return mainfooter;
	}

	public void setMainfooter(String mainfooter) {
		this.mainfooter = mainfooter;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Maindisplay [companyId=" + companyId + ", mainmenu=" + mainmenu + ", society=" + society
				+ ", mainsidbar=" + mainsidbar + ", mainfooter=" + mainfooter + ", display=" + display + "]";
	}
	
}
