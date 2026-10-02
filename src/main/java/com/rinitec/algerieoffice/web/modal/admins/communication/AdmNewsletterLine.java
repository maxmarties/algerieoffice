package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.ads.Newsletter;

public class AdmNewsletterLine implements Serializable {
	private static final long serialVersionUID = -5933441653942543028L;
	
	private final Long id;
	private final String email;
	private final String suscribeDate;
	private final boolean enabled;
	
	public AdmNewsletterLine(final Newsletter newsletter) {
		this.id = newsletter.getId();
		this.email = newsletter.getEmail();
		this.suscribeDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(newsletter.getSuscribeDate());
		this.enabled = newsletter.isEnabled();
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getSuscribeDate() {
		return suscribeDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "AdmNewsletterLine [id=" + id + ", email=" + email + ", suscribeDate=" + suscribeDate + ", enabled="
				+ enabled + "]";
	}

}
