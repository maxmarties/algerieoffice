package com.rinitec.algerieoffice.persistence.modal.admins.ads;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "newsletters")
public class Newsletter implements Serializable {
	private static final long serialVersionUID = 450206050354308656L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "newsletter_id")
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false)
	private DateTime suscribeDate;
	
	private boolean enabled;
	
	public Newsletter() {
		this.enabled = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DateTime getSuscribeDate() {
		return suscribeDate;
	}

	public void setSuscribeDate(DateTime suscribeDate) {
		this.suscribeDate = suscribeDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Newsletter [id=" + id + ", email=" + email + ", suscribeDate=" + suscribeDate + ", enabled=" + enabled + "]";
	}

}
