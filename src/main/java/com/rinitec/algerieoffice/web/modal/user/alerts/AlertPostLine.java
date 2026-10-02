package com.rinitec.algerieoffice.web.modal.user.alerts;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;

public class AlertPostLine implements Serializable {
	private static final long serialVersionUID = -5622017843109197640L;
	
	private final String id;
	private final String name;
	private final Integer sector;
	private final Integer wilaya;
	private final Integer frequency;
	private final Integer potential;
	private final String postedDate;
	private final boolean enabled;
	
	public AlertPostLine(final AlertPost alertPost) {
		this.id = alertPost.getId().toString();
		this.name = alertPost.getName();
		this.sector = alertPost.getSector();
		this.wilaya = alertPost.getWilaya();
		this.frequency = alertPost.getFrequency();
		this.potential = alertPost.getPotential();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(alertPost.getPostedDate());
		this.enabled = alertPost.isEnabled();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getSector() {
		return sector;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public Integer getPotential() {
		return potential;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "AlertPostLine [id=" + id + ", name=" + name + ", sector=" + sector + ", wilaya=" + wilaya
				+ ", frequency=" + frequency + ", potential=" + potential + ", postedDate=" + postedDate + ", enabled="
				+ enabled + "]";
	}
	
}
