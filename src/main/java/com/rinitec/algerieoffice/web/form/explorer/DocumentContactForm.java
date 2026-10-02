package com.rinitec.algerieoffice.web.form.explorer;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidEmail;

public class DocumentContactForm implements Serializable {
	private static final long serialVersionUID = -6908598976466485415L;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	private String documentId;
	
	@NotNull
	private DocumentType type;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String name;
	
	private String phone;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_NOTE, message = "{message.input.lenght}")
	private String message;
	
	private boolean hasFile;
	
	private MultipartFile file;
	
	public DocumentContactForm() {
		this.hasFile = false;
	}
	
	public DocumentContactForm(final Long companyId, final String documentId, final DocumentType type) {
		this();
		this.companyId = companyId;
		this.documentId = documentId;
		this.type = type;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	
	public DocumentType getType() {
		return type;
	}
	
	public void setType(DocumentType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "DocumentContactForm [companyId=" + companyId + ", documentId=" + documentId + ", type=" + type
				+ ", name=" + name + ", phone=" + phone + ", email=" + email + ", message=" + message + ", hasFile="
				+ hasFile + "]";
	}

}
