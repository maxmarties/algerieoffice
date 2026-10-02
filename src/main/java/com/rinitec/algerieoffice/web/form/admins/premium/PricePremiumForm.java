package com.rinitec.algerieoffice.web.form.admins.premium;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.persistence.modal.admins.premium.PremiumFormule;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class PricePremiumForm implements Serializable {
	private static final long serialVersionUID = -9157394802475728786L;
	
	@NotNull
	private Integer start;
	
	@NotNull
	private Integer medium;
	
	@NotNull
	private Integer pro;
	
	@NotNull
	private Integer expert;
	
	public PricePremiumForm() {
	}
	
	public PricePremiumForm(final PremiumFormule premiumFormule) {
		if(premiumFormule != null) {
			this.start = premiumFormule.getStart();
			this.medium = premiumFormule.getMedium();
			this.pro = premiumFormule.getPro();
			this.expert = premiumFormule.getExpert();
		} else {
			this.start = ConstraintesForm.PREMIUM_FORMULE[0];
			this.medium = ConstraintesForm.PREMIUM_FORMULE[1];
			this.pro = ConstraintesForm.PREMIUM_FORMULE[2];
			this.expert = ConstraintesForm.PREMIUM_FORMULE[3];
		}
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getMedium() {
		return medium;
	}

	public void setMedium(Integer medium) {
		this.medium = medium;
	}

	public Integer getPro() {
		return pro;
	}

	public void setPro(Integer pro) {
		this.pro = pro;
	}

	public Integer getExpert() {
		return expert;
	}

	public void setExpert(Integer expert) {
		this.expert = expert;
	}

	@Override
	public String toString() {
		return "PricePremiumForm [start=" + start + ", medium=" + medium + ", pro=" + pro + ", expert=" + expert + "]";
	}

}
