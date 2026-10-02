package com.rinitec.algerieoffice.persistence.modal.company.profile;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.LinkedWebsite;

@Entity
@Table(name = "companies_linked")
public class CompanyLinked implements Serializable {
	private static final long serialVersionUID = -5054362273119788402L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = true, unique = true, length = 250)
	private String facebook;
	
	@Column(nullable = true, unique = true, length = 250)
	private String twitter;
	
	@Column(nullable = true, unique = true, length = 250)
	private String linkedin;
	
	@Column(nullable = true, unique = true, length = 250)
	private String youtube;
	
	@Column(nullable = true, unique = true, length = 250)
	private String google;
	
	@Column(nullable = true, unique = true, length = 250)
	private String instagram;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "companylinked")
	private Collection<LinkedWebsite> websites;
	
	public CompanyLinked() {
	}
	
	public CompanyLinked(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public Collection<LinkedWebsite> getWebsites() {
		return websites;
	}

	public void setWebsites(Collection<LinkedWebsite> websites) {
		this.websites = websites;
	}
	
	public String getSocialMedia(int index) {
		switch(index) {
		case 0: return facebook;
		case 1: return twitter;
		case 2: return google;
		case 3: return linkedin;
		case 4: return youtube;
		case 5: return instagram;
		}
		return null;
	}
	
	public boolean hasPresentSocialMedia() {
		return !StringUtils.isEmpty(facebook) || !StringUtils.isEmpty(twitter) || !StringUtils.isEmpty(google) 
				|| !StringUtils.isEmpty(linkedin) || !StringUtils.isEmpty(youtube) || !StringUtils.isEmpty(instagram);
	}

	@Override
	public String toString() {
		return "CompanyLinked [companyId=" + companyId + ", facebook=" + facebook + ", twitter=" + twitter
				+ ", linkedin=" + linkedin + ", youtube=" + youtube + ", google=" + google + ", instagram=" + instagram
				+ ", websites=" + websites + "]";
	}
	
}
