package com.rinitec.algerieoffice.persistence.modal.token;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.persistence.modal.users.profiles.Profile;

@Entity
@Table(name = "phone_tokens")
public class PhoneToken implements Serializable {
	private static final long serialVersionUID = 4258103381154637360L;

	private static final int EXPIRATION = 60; //1H
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false, length = 6)
	private String token;
	
	@Column(nullable = false)
	private DateTime expiryDate;
	
	@OneToOne(targetEntity = Profile.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "profile_id", foreignKey = @ForeignKey(name = "FK_VERIFY_PROFILE"))
    private Profile profile;
	
	public PhoneToken() {
	}
	
	public PhoneToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
	
	public PhoneToken(final String token, final Profile profile) {
        this.token = token;
        this.profile = profile;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
	
	private DateTime calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new DateTime(cal.getTime().getTime());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public DateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(DateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

	@Override
	public String toString() {
		return "PhoneToken [id=" + id + ", token=" + token + ", expiryDate=" + expiryDate + ", profile=" + profile + "]";
	}
	
}
