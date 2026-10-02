package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyCredit;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTaxe;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.CreditTruck;

public class ExplorerWidgetCredit implements Serializable {
	private static final long serialVersionUID = -2044350541318211973L;
	
	private final Boolean[] ticket = new Boolean[5];
	private final String taxe;
	private final String truck;
	
	public ExplorerWidgetCredit(final CompanyCredit companyCredit) {
		if(companyCredit != null) {
			final List<CreditTaxe> taxes = new ArrayList<CreditTaxe>(companyCredit.getTaxes());
			final List<CreditTruck> trucks = new ArrayList<CreditTruck>(companyCredit.getTrucks());
			for (int i = 0; i < 5; i++) {
				this.ticket[i] = companyCredit.getTicket(i);
			}
			this.taxe = !taxes.isEmpty() ? taxes.get(0).getTaxename().concat(" ").concat(taxes.get(0).getTaxetaux().toString()).concat("%") : null;
			this.truck = !trucks.isEmpty() ? trucks.get(0).getIndtruck() : null;
			
		} else {
			this.taxe = this.truck = null;
		}
	}

	public Boolean[] getTicket() {
		return ticket;
	}

	public String getTaxe() {
		return taxe;
	}

	public String getTruck() {
		return truck;
	}
	
	public boolean hasPresentTicket() {
		for (int i = 0; i < 5; i++) {
			if(ticket[i] != null && ticket[i]) return true;
		}
		return false;
	}
	
	public boolean hasPresentCredit() {
		return !StringUtils.isEmpty(taxe) || !StringUtils.isEmpty(truck) || hasPresentTicket();
	}

	@Override
	public String toString() {
		return "ExplorerWidgetCredit [ticket=" + Arrays.toString(ticket) + ", taxe=" + taxe + ", truck=" + truck + "]";
	}

}
