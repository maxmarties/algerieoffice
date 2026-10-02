package com.rinitec.algerieoffice.web.form.feedback;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class NoticeForm implements Serializable {
	private static final long serialVersionUID = -9005597906589294389L;
	
	@NotNull
	private Long companyNotice;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String titleNotice;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String messageNotice;
	
	private boolean autorisedNotice;
	
	public NoticeForm() {
	}
	
	public NoticeForm(Long companyNotice) {
		this.companyNotice = companyNotice;
	}

	public Long getCompanyNotice() {
		return companyNotice;
	}

	public void setCompanyNotice(Long companyNotice) {
		this.companyNotice = companyNotice;
	}

	public String getTitleNotice() {
		return titleNotice;
	}

	public void setTitleNotice(String titleNotice) {
		this.titleNotice = titleNotice;
	}

	public String getMessageNotice() {
		return messageNotice;
	}

	public void setMessageNotice(String messageNotice) {
		this.messageNotice = messageNotice;
	}

	public boolean isAutorisedNotice() {
		return autorisedNotice;
	}

	public void setAutorisedNotice(boolean autorisedNotice) {
		this.autorisedNotice = autorisedNotice;
	}

	@Override
	public String toString() {
		return "NoticeForm [companyNotice=" + companyNotice + ", titleNotice=" + titleNotice + ", messageNotice="
				+ messageNotice + ", autorisedNotice=" + autorisedNotice + "]";
	}

}
