package com.rinitec.algerieoffice.web.modal.user.easylist;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistCompany;
import com.rinitec.algerieoffice.persistence.modal.users.easylist.EasylistDocument;

public class EasylistLine implements Serializable {
	private static final long serialVersionUID = -3859160126179367790L;
	
	private final String id;
	private final String easyname;
	private final String esayDate;
	private final int potentiel;
	
	public EasylistLine(final EasylistCompany easylistCompany) {
		this.id = easylistCompany.getId().toString();
		this.easyname = easylistCompany.getEasyname();
		this.esayDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(easylistCompany.getEasyDate());
		this.potentiel = easylistCompany.getCompanies().size();
	}
	
	public EasylistLine(final EasylistDocument easylistDocument) {
		this.id = easylistDocument.getId().toString();
		this.easyname = easylistDocument.getEasyname();
		this.esayDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(easylistDocument.getEasyDate());
		this.potentiel = easylistDocument.getDocuments().size();
	}

	public String getId() {
		return id;
	}

	public String getEasyname() {
		return easyname;
	}

	public String getEsayDate() {
		return esayDate;
	}

	public int getPotentiel() {
		return potentiel;
	}

	@Override
	public String toString() {
		return "EasylistCompanyLine [id=" + id + ", easyname=" + easyname + ", esayDate=" + esayDate + ", potentiel="
				+ potentiel + "]";
	}

}
