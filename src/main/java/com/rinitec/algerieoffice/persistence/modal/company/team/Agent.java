package com.rinitec.algerieoffice.persistence.modal.company.team;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "agents")
public class Agent implements Serializable {
	private static final long serialVersionUID = 8030004884531125391L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_id")
	private Long id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean sexe;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = false, length = 60)
	private String function;
	
	@Column(nullable = true, length = 250)
	private String biography;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false, unique = true, length = 10)
	private String phone;
	
	@Column(nullable = true, unique = true, length = 250)
	private String facebook;
	
	@Column(nullable = true, unique = true, length = 250)
	private String twitter;
	
	@Column(nullable = true, unique = true, length = 250)
	private String linkedin;
	
	@Column(nullable = false)
	private Boolean hasAvatar;
	
	@Column(nullable = true, unique = true)
	private Long userId;
	
	@Column(nullable = false)
	private Boolean hasPingled;
	
	public Agent() {
	}
	
	public Agent(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public Boolean getSexe() {
		return sexe;
	}
	
	public void setSexe(Boolean sexe) {
		this.sexe = sexe;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Boolean getHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Boolean getHasPingled() {
		return hasPingled;
	}
	
	public void setHasPingled(Boolean hasPingled) {
		this.hasPingled = hasPingled;
	}
	
	public String getSocial(int index) {
		switch(index) {
		case 0: return facebook;
		case 1: return twitter;
		case 2: return linkedin;
		}
		return null;
	}
	
	public String getDisplayName() {
		return firstName.concat(" ").concat(lastName);
	}

	@Override
	public String toString() {
		return "Agent [id=" + id + ", companyId=" + companyId + ", sexe=" + sexe + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", function=" + function + ", biography=" + biography + ", email=" + email
				+ ", phone=" + phone + ", facebook=" + facebook + ", twitter=" + twitter + ", linkedin=" + linkedin
				+ ", hasAvatar=" + hasAvatar + ", userId=" + userId + ", hasPingled=" + hasPingled + "]";
	}
	
}
