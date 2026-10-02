package com.rinitec.algerieoffice.web.form.feedback;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class ReportForm implements Serializable {
	private static final long serialVersionUID = -5818385077623272398L;
	
	@NotNull
	private Long companyReport;
	
	@ValidChose
	@NotNull
	private Integer typeReport;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String reasonReport;
	
	private boolean acceptReport;
	private boolean hasFileReport;
	
	private MultipartFile file;
	
	public ReportForm() {
		this.acceptReport = this.hasFileReport = false;
	}
	
	public ReportForm(final Long companyReport) {
		this();
		this.companyReport = companyReport;
	}

	public Long getCompanyReport() {
		return companyReport;
	}

	public void setCompanyReport(Long companyReport) {
		this.companyReport = companyReport;
	}

	public Integer getTypeReport() {
		return typeReport;
	}

	public void setTypeReport(Integer typeReport) {
		this.typeReport = typeReport;
	}

	public String getReasonReport() {
		return reasonReport;
	}

	public void setReasonReport(String reasonReport) {
		this.reasonReport = reasonReport;
	}

	public boolean isAcceptReport() {
		return acceptReport;
	}

	public void setAcceptReport(boolean acceptReport) {
		this.acceptReport = acceptReport;
	}
	
	public boolean isHasFileReport() {
		return hasFileReport;
	}
	
	public void setHasFileReport(boolean hasFileReport) {
		this.hasFileReport = hasFileReport;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "ReportForm [companyReport=" + companyReport + ", typeReport=" + typeReport + ", reasonReport="
				+ reasonReport + ", acceptReport=" + acceptReport + ", hasFileReport=" + hasFileReport + "]";
	}

}
