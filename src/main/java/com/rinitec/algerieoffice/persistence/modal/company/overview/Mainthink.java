package com.rinitec.algerieoffice.persistence.modal.company.overview;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.ThinkItem;

@Entity
@Table(name = "mainthinks")
public class Mainthink implements Serializable {
	private static final long serialVersionUID = 3960050490324164323L;
	
	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "mainthink")
	private Collection<ThinkItem> items;
	
	public Mainthink() {
	}
	
	public Mainthink(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Collection<ThinkItem> getItems() {
		return items;
	}

	public void setItems(Collection<ThinkItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Mainthink [companyId=" + companyId + ", items=" + items + "]";
	}

}
