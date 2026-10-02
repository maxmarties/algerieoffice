package com.rinitec.algerieoffice.web.modal.publics.marketplace.employes;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentWidgetMini;

public class EmployeWidgetMini extends DocumentWidgetMini {
	private static final long serialVersionUID = 5462651668059600007L;
	
	private final String expiredDate;
	private final Integer contract;
	private final Integer domaine;
	private final Integer discoverType;
	private final String discoverValue;
	
	public EmployeWidgetMini(final Employe employe, final Integer domaine, final Integer discoverType, final String discoverValue, 
			final Company company, final String url, final Long userId, final DateTime forOrder1, final Integer forOrder2) {
		super(employe, company, url, userId);
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
		this.contract = employe.getContract();
		this.domaine = domaine;
		this.discoverType = discoverType;
		this.discoverValue = ParseUtil.getFormattedCapital(discoverValue);
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public Integer getContract() {
		return contract;
	}

	public Integer getDomaine() {
		return domaine;
	}

	public Integer getDiscoverType() {
		return discoverType;
	}

	public String getDiscoverValue() {
		return discoverValue;
	}

	@Override
	public String toString() {
		return "EmployeWidgetMini [expiredDate=" + expiredDate + ", contract=" + contract + ", domaine=" + domaine
				+ ", discoverType=" + discoverType + ", discoverValue=" + discoverValue + "]";
	}

}
