package com.rinitec.algerieoffice.web.form.user.repport;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class ProblemForm implements Serializable {
	private static final long serialVersionUID = 3040272472600022319L;
	
	@NotNull
	private Long id;
	
	@ValidChose
	@NotNull
	private Integer type;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_MESSAGE, message = "{message.input.lenght}")
	private String message;
	
	private boolean hasFile;
	
	private MultipartFile file;
	
	public ProblemForm() {
	}
	
	public ProblemForm(final Long id) {
		this.id = id;
		this.hasFile = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
		return "ProblemForm [id=" + id + ", type=" + type + ", message=" + message + ", hasFile=" + hasFile + ", file="
				+ file + "]";
	}

}
