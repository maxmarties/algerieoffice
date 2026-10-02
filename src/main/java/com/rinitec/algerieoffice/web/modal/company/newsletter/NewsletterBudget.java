package com.rinitec.algerieoffice.web.modal.company.newsletter;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Budget;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class NewsletterBudget implements Serializable {
	private static final long serialVersionUID = -4612969803263133971L;
	
	private final long emails;
	private final int sendings;
	
	public NewsletterBudget(final Budget budget) {
		if(budget != null) {
			this.emails = budget.getEmails();
			this.sendings = budget.getSendings();
		} else {
			this.emails = 0L;
			this.sendings = 0;
		}
	}

	public long getEmails() {
		return emails;
	}

	public int getSendings() {
		return sendings;
	}
	
	public boolean hasPresentBuget(int index) {
		return index == 1 ? emails > 0L : sendings > 0;
	}
	
	public String getFormattedBudget(int index) {
		switch(index) {
		case 1: return ParseUtil.getFormattedCount(emails);
		case 2: return ParseUtil.getFormattedOrder(sendings);
		default: return ParseUtil.getFormattedOrder(ConstraintesForm.LIMIT_EMAILING);
		}
	}

	@Override
	public String toString() {
		return "NewsletterBudget [emails=" + emails + ", sendings=" + sendings + "]";
	}

}
