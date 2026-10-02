package com.rinitec.algerieoffice.web.form.admins.datas;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class IdentityResponseForm implements Serializable {
	private static final long serialVersionUID = -5465797614861746378L;
	
	@NotNull
	private Long id;
	
	private boolean response;
	private String message;
	
	public IdentityResponseForm() {
	}
	
	public IdentityResponseForm(final Long id) {
		this.id = id;
		this.response = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isResponse() {
		return response;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "IdentityResponseForm [id=" + id + ", response=" + response + ", message=" + message + "]";
	}

}
