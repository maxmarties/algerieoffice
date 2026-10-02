package com.rinitec.algerieoffice.web.modal.user.easylist;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;

public class EasylistEmployeLine extends EasylistDocumentLine {
	private static final long serialVersionUID = -4197524516383886695L;
	
	private final Integer contract;
	private final Integer domaine;
	private final String expiredDate;
	
	public EasylistEmployeLine(final Employe employe, final Integer domaine, final Company company, final String companyURL) {
		super(employe, company, companyURL);
		this.contract = employe.getContract();
		this.domaine = domaine;
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
	}

	public Integer getContract() {
		return contract;
	}

	public Integer getDomaine() {
		return domaine;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	@Override
	public String toString() {
		return "EasylistEmployeLine [contract=" + contract + ", domaine=" + domaine + ", expiredDate=" + expiredDate + "]";
	}
	
}
