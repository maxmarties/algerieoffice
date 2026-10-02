package com.rinitec.algerieoffice.web.modal.user.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.team.Guest;
import com.rinitec.algerieoffice.web.modal.company.CompanyAccountMini;

public class UserContributorLine implements Serializable {
	private static final long serialVersionUID = 4750804060643192205L;
	
	private final Long id;
	private final int role;
	private final String username;
	private final String guestDate;
	private final CompanyAccountMini companyMini;
	
	public UserContributorLine(final Company company, final String url, final String username, final Guest guest) {
		this.id = guest.getId();
		this.role = guest.getRole();
		this.username = username;
		this.guestDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guest.getGuestDate());
		this.companyMini = new CompanyAccountMini(company, url);
	}

	public Long getId() {
		return id;
	}

	public int getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	public String getGuestDate() {
		return guestDate;
	}

	public CompanyAccountMini getCompanyMini() {
		return companyMini;
	}

	@Override
	public String toString() {
		return "UserContributorLine [id=" + id + ", role=" + role + ", username=" + username + ", guestDate="
				+ guestDate + ", companyMini=" + companyMini + "]";
	}

}
