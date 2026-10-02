package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ExplorerWidgetEmploye implements Serializable {
	private static final long serialVersionUID = 3137686272362829959L;
	
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String expiredDate;
	private final Integer contract;
	private final Integer domaine;
	private final Integer discoverType;
	private final String discoverValue;
	
	public ExplorerWidgetEmploye(final Employe employe, final Integer domaine, final Integer discoverType, final String discoverValue, 
			final String urlEmployes) {
		this.title = employe.getTitle();
		this.identifyURL = urlEmployes.concat("/").concat(employe.getIdentify());
		this.description = employe.getDescription();
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
		this.contract = employe.getContract();
		this.domaine = domaine;
		this.discoverType = discoverType;
		this.discoverValue = ParseUtil.getFormattedCapital(discoverValue);
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
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
		return "ExplorerWidgetEmploye [title=" + title + ", identifyURL=" + identifyURL + ", description=" + description
				+ ", expiredDate=" + expiredDate + ", contract=" + contract + ", domaine=" + domaine + ", discoverType="
				+ discoverType + ", discoverValue=" + discoverValue + "]";
	}

}
