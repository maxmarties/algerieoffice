package com.rinitec.algerieoffice.persistence.modal.company.manage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "widgets_btoc")
public class WidgetB2C implements Serializable {
	private static final long serialVersionUID = 364818267404851991L;
	
	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Max(9)
	@Column(nullable = false)
	private Integer category;
	
	@Max(24)
	@Column(nullable = false)
	private Integer activity;
	
	private boolean enabled;
	private boolean filtred;
	
	@Column(nullable = false)
	private Boolean hasCover;
	
	public WidgetB2C() {
		this.hasCover = false;
	}
	
	public WidgetB2C(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isFiltred() {
		return filtred;
	}

	public void setFiltred(boolean filtred) {
		this.filtred = filtred;
	}

	public Boolean getHasCover() {
		return hasCover;
	}

	public void setHasCover(Boolean hasCover) {
		this.hasCover = hasCover;
	}

	@Override
	public String toString() {
		return "WidgetB2C [companyId=" + companyId + ", category=" + category + ", activity=" + activity + ", enabled="
				+ enabled + ", filtred=" + filtred + ", hasCover=" + hasCover + "]";
	}

}
