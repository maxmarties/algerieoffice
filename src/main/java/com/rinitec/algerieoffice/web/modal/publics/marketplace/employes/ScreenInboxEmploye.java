package com.rinitec.algerieoffice.web.modal.publics.marketplace.employes;

import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentInbox;

public class ScreenInboxEmploye extends DocumentInbox {
	private static final long serialVersionUID = 7330286006026522158L;
	
	private final Integer contract;
	private final Integer domaine;
	private final String expiredDate;
	private final Integer discoverType;
	private final String discoverValue;
	private final List<Integer> locations;
	
	public ScreenInboxEmploye(final Employe employe, final EmployeDetail employeDetail, final List<Integer> locations) {
		super(employe, employeDetail.getDetail(), employeDetail.getUrlExtern());
		this.contract = employe.getContract();
		this.domaine = employeDetail.getDomaine();
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
		this.discoverType = employeDetail.getDiscoverType();
		this.discoverValue = ParseUtil.getFormattedCapital(employeDetail.getDiscoverValue());
		this.locations = locations;
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

	public Integer getDiscoverType() {
		return discoverType;
	}

	public String getDiscoverValue() {
		return discoverValue;
	}

	public List<Integer> getLocations() {
		return locations;
	}

	@Override
	public String toString() {
		return "ScreenInboxEmploye [contract=" + contract + ", domaine=" + domaine + ", expiredDate=" + expiredDate
				+ ", discoverType=" + discoverType + ", discoverValue=" + discoverValue + ", locations=" + locations + "]";
	}

}
