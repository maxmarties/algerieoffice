package com.rinitec.algerieoffice.persistence.modal.company.overview;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.CatalogItem;

@Entity
@Table(name = "maincatalogs")
public class Maincatalog implements Serializable {
	private static final long serialVersionUID = 2722684149593517265L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean style;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "maincatalog")
	private Collection<CatalogItem> items;
	
	public Maincatalog() {
	}
	
	public Maincatalog(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getStyle() {
		return style;
	}

	public void setStyle(Boolean style) {
		this.style = style;
	}

	public Collection<CatalogItem> getItems() {
		return items;
	}

	public void setItems(Collection<CatalogItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Maincatalog [companyId=" + companyId + ", style=" + style + ", items=" + items + "]";
	}
	
}
