package com.rinitec.algerieoffice.web.modal.company.tools;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.Premium;

public class PremiumLine implements Serializable {
	private static final long serialVersionUID = -3485601550567874122L;
	
	private final String id;
	private final int pack;
	private final String createDate;
	private final String expiryDate;
	private final boolean enabled;
	
	public PremiumLine(final Premium premium) {
		this.id = premium.getId().toString();
		this.pack = premium.getPass();
		this.createDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(premium.getCreateDate());
		this.expiryDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(premium.getExpiryDate());
		this.enabled = premium.getEnabled();
	}

	public String getId() {
		return id;
	}

	public int getPack() {
		return pack;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String toString() {
		return "PremiumLine [id=" + id + ", pack=" + pack + ", createDate=" + createDate + ", expiryDate=" + expiryDate
				+ ", enabled=" + enabled + "]";
	}

}
