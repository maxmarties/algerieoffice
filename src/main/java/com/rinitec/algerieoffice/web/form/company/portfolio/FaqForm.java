package com.rinitec.algerieoffice.web.form.company.portfolio;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class FaqForm implements Serializable {
	private static final long serialVersionUID = -1062912430420373663L;
	
	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String question;
	
	@NotNull(message = "{message.input.required}")
	private String detail;
	
	private String urlExtern;
	
	@NotNull
	private Boolean hasPublished;
	
	public FaqForm() {
	}
	
	public FaqForm(final Long companyId) {
		this.companyId = companyId;
		this.hasPublished = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	@Override
	public String toString() {
		return "FaqForm [id=" + id + ", companyId=" + companyId + ", question=" + question + ", detail=" + detail
				+ ", urlExtern=" + urlExtern + ", hasPublished=" + hasPublished + "]";
	}

}
