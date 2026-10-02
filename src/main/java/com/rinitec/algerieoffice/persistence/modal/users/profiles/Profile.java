package com.rinitec.algerieoffice.persistence.modal.users.profiles;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.joda.time.DateTime;

@Entity
@Table(name = "profiles")
public class Profile implements Serializable {
	private static final long serialVersionUID = 8463431375105980633L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Boolean sexe;
	
	@Column(nullable = false)
	private DateTime birthDate;
	
	@Column(nullable = true, length = 60)
	private String function;
	
	@Column(nullable = true, length = 512)
	private String biography;
	
	@Column(nullable = false, length = 90)
	private String address;
	
	@Column(nullable = false, length = 5)
	private String postal;
	
	@Max(48)
	@Column(nullable = false)
	private Integer wilaya;
	
	@Column(nullable = false, unique = true, length = 10)
	private String phone;
	
	@Column(nullable = false)
	private Boolean hasPhone;
	
	@Column(nullable = true, unique = true, length = 250)
	private String website;
	
	private boolean enabled;
	
	public Profile() {
		this.enabled = false;
	}
	
	public Profile(final Long userId) {
		this.userId = userId;
		this.enabled = false;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getSexe() {
		return sexe;
	}

	public void setSexe(Boolean sexe) {
		this.sexe = sexe;
	}

	public DateTime getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Boolean getHasPhone() {
		return hasPhone;
	}

	public void setHasPhone(Boolean hasPhone) {
		this.hasPhone = hasPhone;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Profile [userId=" + userId + ", sexe=" + sexe + ", birthDate=" + birthDate + ", function=" + function
				+ ", biography=" + biography + ", address=" + address + ", postal=" + postal + ", wilaya=" + wilaya
				+ ", phone=" + phone + ", hasPhone=" + hasPhone + ", website=" + website + ", enabled=" + enabled + "]";
	}
	
}
