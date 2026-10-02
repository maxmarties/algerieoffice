package com.rinitec.algerieoffice.web.form.user.repport;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class AssistForm implements Serializable {
	private static final long serialVersionUID = 5116410103424852673L;
	
	@NotNull
	private Long id;
	
	private Integer app;
	private Integer management;
	private Integer program;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String object;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_MESSAGE, message = "{message.input.lenght}")
	private String message;
	
	public AssistForm() {
	}
	
	public AssistForm(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getApp() {
		return app;
	}

	public void setApp(Integer app) {
		this.app = app;
	}

	public Integer getManagement() {
		return management;
	}

	public void setManagement(Integer management) {
		this.management = management;
	}

	public Integer getProgram() {
		return program;
	}

	public void setProgram(Integer program) {
		this.program = program;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "AssistForm [id=" + id + ", app=" + app + ", management=" + management + ", program=" + program
				+ ", object=" + object + ", message=" + message + "]";
	}

}
