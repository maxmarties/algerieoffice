package com.rinitec.algerieoffice.persistence.modal.users;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jboss.aerogear.security.otp.api.Base32;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 7545704966117631078L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	@Column(nullable = true)
	private Long companyId;
	
	@Column(nullable = false, length = 30)
	private String firstName;
	
	@Column(nullable = false, length = 30)
	private String lastName;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Transient
	private String randomPassword;
	
	@Column(nullable = false)
	private Boolean hasAvatar;
	
	@Column(nullable = false)
	private Boolean admin;
	
	private boolean enabled;
	private boolean locked;
	private boolean expired;
	
	//TODO MORE VERSION
	@Column(nullable = true)
	private Boolean using2FA;
	
	@Column(nullable = false)
	private String secret;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;
	
	public User() {
		this.secret = Base32.random();
		this.hasAvatar = this.admin = this.enabled = this.locked = this.expired = false;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRandomPassword() {
		return randomPassword;
	}

	public void setRandomPassword(String randomPassword) {
		this.randomPassword = randomPassword;
	}

	public Boolean getHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(Boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}
	
	public Boolean getAdmin() {
		return admin;
	}
	
	public void setAdmin(Boolean admin) {
		this.admin = admin;
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

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public Boolean getUsing2FA() {
		return using2FA;
	}

	public void setUsing2FA(Boolean using2fa) {
		using2FA = using2fa;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public String getDisplayName() {
		return firstName.concat(" ").concat(lastName);
	}
	
	public boolean isMember() {
		return !admin && enabled && !locked && !expired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return email;
	}
	
	public String hashString() {
		return "User [id=" + id + ", companyId=" + companyId + ", firstName=" + firstName + ", lastName=" + lastName 
				+ ", email=" + email + ", password=" + password + ", hasAvatar=" + hasAvatar + ", enabled=" + enabled 
				+ ", locked=" + locked + ", expired=" + expired + ", using2FA=" + using2FA + ", secret=" + secret + "]";
	}

}
