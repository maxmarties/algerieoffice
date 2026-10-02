package com.rinitec.algerieoffice.web.modal.company.profile;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class IdentityState implements Serializable {
	private static final long serialVersionUID = 2018150976829931275L;
	
	private final boolean exists;
	private final boolean consulted;
	private final String requestedDate;
	
	public IdentityState(final boolean exists, final boolean consulted, final DateTime requestedDate) {
		this.exists = exists;
		this.consulted = consulted;
		this.requestedDate = requestedDate != null ? DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(requestedDate) : null;
	}

	public boolean isExists() {
		return exists;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	@Override
	public String toString() {
		return "IdentityState [exists=" + exists + ", consulted=" + consulted + ", requestedDate=" + requestedDate + "]";
	}

}
