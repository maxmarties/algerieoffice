package com.rinitec.algerieoffice.web.modal.admins.companies;

import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.Company;

public class AdmCompanyDelete {

	private final Company company;
	private final List<String> users;
	
	public AdmCompanyDelete(final Company company, final List<String> users) {
		this.company = company;
		this.users = users;
	}

	public Company getCompany() {
		return company;
	}

	public List<String> getUsers() {
		return users;
	}

	@Override
	public String toString() {
		return "AdmCompanyDelete [company=" + company + ", users=" + users + "]";
	}
	
}
