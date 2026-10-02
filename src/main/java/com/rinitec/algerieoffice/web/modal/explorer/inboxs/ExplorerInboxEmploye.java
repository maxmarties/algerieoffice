package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;
import java.util.List;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.EmployeDetail;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ExplorerInboxEmploye implements Serializable {
	private static final long serialVersionUID = 7305729276663940543L;
	
	private final String id;
	private final String title;
	private final String detail;
	private final String urlExtern;
	private final Integer contract;
	private final Integer domaine;
	private final String expiredDate;
	private final String modifiedDate;
	private final Integer discoverType;
	private final String discoverValue;
	private final List<Integer> locations;
	
	public ExplorerInboxEmploye(final Employe employe, final EmployeDetail employeDetail, final List<Integer> locations) {
		this.id = employe.getId().toString();
		this.title = employe.getTitle();
		this.detail = new String(employeDetail.getDetail());
		this.urlExtern = employeDetail.getUrlExtern();
		this.contract = employe.getContract();
		this.domaine = employeDetail.getDomaine();
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getModifiedDate());
		this.discoverType = employeDetail.getDiscoverType();
		this.discoverValue = ParseUtil.getFormattedCapital(employeDetail.getDiscoverValue());
		this.locations = locations;
	}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
	}

	public String getUrlExtern() {
		return urlExtern;
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

	public String getModifiedDate() {
		return modifiedDate;
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
		return "ExplorerInboxEmploye [id=" + id + ", title=" + title + ", detail=" + detail + ", urlExtern=" + urlExtern
				+ ", contract=" + contract + ", domaine=" + domaine + ", expiredDate=" + expiredDate + ", modifiedDate="
				+ modifiedDate + ", discoverType=" + discoverType + ", discoverValue=" + discoverValue + ", locations="
				+ locations + "]";
	}

}
