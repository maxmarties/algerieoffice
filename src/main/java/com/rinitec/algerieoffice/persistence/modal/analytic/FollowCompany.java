package com.rinitec.algerieoffice.persistence.modal.analytic;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "follows_company")
public class FollowCompany implements Serializable {
	private static final long serialVersionUID = -2996776330624942253L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long facebook;
	
	@Column(nullable = false)
	private Long twitter;
	
	@Column(nullable = false)
	private Long google;
	
	@Column(nullable = false)
	private Long linkedin;
	
	@Column(nullable = false)
	private Long viadeo;
	
	public FollowCompany() {
		this.facebook = this.twitter = this.google = this.linkedin = this.viadeo = 0L;
	}
	
	public FollowCompany(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getFacebook() {
		return facebook;
	}

	public void setFacebook(Long facebook) {
		this.facebook = facebook;
	}

	public Long getTwitter() {
		return twitter;
	}

	public void setTwitter(Long twitter) {
		this.twitter = twitter;
	}

	public Long getGoogle() {
		return google;
	}

	public void setGoogle(Long google) {
		this.google = google;
	}

	public Long getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(Long linkedin) {
		this.linkedin = linkedin;
	}

	public Long getViadeo() {
		return viadeo;
	}

	public void setViadeo(Long viadeo) {
		this.viadeo = viadeo;
	}

	@Override
	public String toString() {
		return "Follow [companyId=" + companyId + ", facebook=" + facebook + ", twitter=" + twitter + ", google="
				+ google + ", linkedin=" + linkedin + ", viadeo=" + viadeo + "]";
	}
	
}
