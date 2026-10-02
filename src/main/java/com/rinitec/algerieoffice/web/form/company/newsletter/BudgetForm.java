package com.rinitec.algerieoffice.web.form.company.newsletter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class BudgetForm implements Serializable {
	private static final long serialVersionUID = 2038943975730041164L;
	
	@NotNull
	private Long companyId;
	
	@ValidChose
	private Integer pack;
	
	private List<Integer> budgetEmails;
	private List<Integer> budgetSending;
	private List<String> budgetFormule;
	
	private MultipartFile file;
	
	public BudgetForm() {
		this.budgetEmails = new ArrayList<Integer>();
		this.budgetSending = new ArrayList<Integer>();
		this.budgetFormule = new ArrayList<String>();
	}
	
	public BudgetForm(final Long companyId) {
		this.companyId = companyId;
		this.budgetEmails = Arrays.asList(ConstraintesForm.BUDGET_EMAILS);
		this.budgetSending = Arrays.asList(ConstraintesForm.BUDGET_SENDING);
		this.budgetFormule = Arrays.asList(ConstraintesForm.BUDGET_FORMULE);
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}

	public List<Integer> getBudgetEmails() {
		return budgetEmails;
	}

	public void setBudgetEmails(List<Integer> budgetEmails) {
		this.budgetEmails = budgetEmails;
	}

	public List<Integer> getBudgetSending() {
		return budgetSending;
	}

	public void setBudgetSending(List<Integer> budgetSending) {
		this.budgetSending = budgetSending;
	}

	public List<String> getBudgetFormule() {
		return budgetFormule;
	}

	public void setBudgetFormule(List<String> budgetFormule) {
		this.budgetFormule = budgetFormule;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getFormattedEmails(int index) {
		return ParseUtil.getFormattedOrder(budgetEmails.get(index));
	}
	
	public String getFormattedSending(int index) {
		return ParseUtil.getFormattedOrder(budgetSending.get(index));
	}
	
	public String getFormattedFormule(int index) {
		return ParseUtil.getFormattedCapital(budgetFormule.get(index));
	}
	
	public float getEstimattedEmails(int index) {
		return Float.valueOf(budgetFormule.get(index)) / budgetEmails.get(index);
	}
	
	public float getEstimattedSending(int index) {
		return Float.valueOf(budgetFormule.get(index)) / budgetSending.get(index);
	}

	@Override
	public String toString() {
		return "BudgetForm [companyId=" + companyId + ", pack=" + pack + ", budgetEmails=" + budgetEmails
				+ ", budgetSending=" + budgetSending + ", budgetFormule=" + budgetFormule + "]";
	}

}
