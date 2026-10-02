package com.rinitec.algerieoffice.web.modal.user.easylist;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;

public class EasylistAnnonceLine extends EasylistDocumentLine {
	private static final long serialVersionUID = -100143672709961084L;
	
	private final int type;
	private final String period;

	public EasylistAnnonceLine(final Annonce annonce, final Company company, final String companyURL) {
		super(annonce, company, companyURL);
		this.type = annonce.getType();
		this.period = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate()).concat(" - ")
				.concat(DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate()));
	}

	public int getType() {
		return type;
	}

	public String getPeriod() {
		return period;
	}

	@Override
	public String toString() {
		return "EasylistAnnonceLine [type=" + type + ", period=" + period + "]";
	}
	
}
