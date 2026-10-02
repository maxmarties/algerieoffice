package com.rinitec.algerieoffice.persistence.modal.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "companies_seo")
public class CompanySeo implements Serializable {
	private static final long serialVersionUID = 7718903029709104461L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = true, unique = true, length = 250)
	private String url;
	
	@Column(nullable = true, length = 60)
	private String tageline;
	
	@Column(nullable = true, length = 512)
	private String keysword;
	
	@Column(nullable = false, length = 250)
	private String description;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Company company;
	
	public CompanySeo() {
	}

	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTageline() {
		return tageline;
	}

	public void setTageline(String tageline) {
		this.tageline = tageline;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "CompanySeo [companyId=" + companyId + ", url=" + url + ", tageline=" + tageline + ", keysword=" + keysword
				+ ", description=" + description + "]";
	}
	
}
