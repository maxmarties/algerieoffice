package com.rinitec.algerieoffice.web.modal.user.alerts;

import java.io.Serializable;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.persistence.modal.users.alerts.AlertPost;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AlertPostResult implements Serializable {
	private static final long serialVersionUID = -6919482881863824419L;
	
	private final UUID id;
	private final Long userId;
	private final String firstname;
	private final String email;
	private final String name;
	private final Long count;
	private final String resultURL;
	
	public AlertPostResult(final AlertPost alertPost, final User user, final Long count) {
		this.id = alertPost.getId();
		this.userId = user.getId();
		this.firstname = user.getFirstName();
		this.email = user.getEmail();
		this.name = alertPost.getName();
		this.count = count;
		this.resultURL = ConstraintesURL.getAlerteMarketplaceURL(alertPost.getType()) + "?secteur=" + alertPost.getSector() + "&location=" + alertPost.getWilaya();
	}

	public UUID getId() {
		return id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public Long getCount() {
		return count;
	}

	public String getResultURL() {
		return resultURL;
	}

	@Override
	public String toString() {
		return "AlertPostResult [id=" + id + ", userId=" + userId + ", firstname=" + firstname + ", email=" + email
				+ ", name=" + name + ", count=" + count + ", resultURL=" + resultURL + "]";
	}

}
