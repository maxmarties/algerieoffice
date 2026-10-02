package com.rinitec.algerieoffice.persistence.modal.company;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;

@Entity
@Table(name = "companies")
public class Company implements Serializable {
	private static final long serialVersionUID = 6100448769290216178L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 60)
	private String denomination;
	
	@Column(nullable = false, length = 60)
	private String tradename;
	
	@Column(nullable = false, length = 2)
	private String lang;
	
	@Column(nullable = false, unique = true, length = 10)
	private String phone;
	
	@Column(nullable = false, unique = true)
	private String companymail;
	
	@Column(nullable = false)
	private Boolean hasAvatar;
	
	private boolean enabled;
	private boolean locked;
	private boolean active;
	
	@Column(nullable = true)
	private DateTime buildDate;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
	private Collection<CompanyAddress> addresses;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "companies_activities", joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"))
	private Collection<Activity> activities;
	
	public Company() {
		this.enabled = this.locked = false;
		this.active = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanymail() {
		return companymail;
	}

	public void setCompanymail(String companymail) {
		this.companymail = companymail;
	}

	public Boolean getHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public DateTime getBuildDate() {
		return buildDate;
	}
	
	public void setBuildDate(DateTime buildDate) {
		this.buildDate = buildDate;
	}

	public Collection<CompanyAddress> getAddresses() {
		return addresses;
	}

	public void setAddresses(Collection<CompanyAddress> addresses) {
		this.addresses = addresses;
	}

	public Collection<Activity> getActivities() {
		return activities;
	}
	
	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
	
	public boolean isPublished() {
		return enabled && !locked && active;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", denomination=" + denomination + ", tradename=" + tradename + ", lang=" + lang
				+ ", phone=" + phone + ", companymail=" + companymail + ", hasAvatar=" + hasAvatar + ", enabled="
				+ enabled + ", locked=" + locked + ", active=" + active + ", buildDate=" + buildDate + ", addresses="
				+ addresses + ", activities=" + activities + "]";
	}
	
}
