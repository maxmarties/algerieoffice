package com.rinitec.algerieoffice.web.form.user.setting;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class SecurityForm implements Serializable {
	private static final long serialVersionUID = -7551303102762435780L;
	
	@NotNull
	private Long id;
	
	private boolean analytic;
	private boolean secured;
	
	private Integer codage;
	
	private boolean logouted;
	
	public SecurityForm() {
		this.logouted = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isAnalytic() {
		return analytic;
	}

	public void setAnalytic(boolean analytic) {
		this.analytic = analytic;
	}

	public boolean isSecured() {
		return secured;
	}

	public void setSecured(boolean secured) {
		this.secured = secured;
	}

	public Integer getCodage() {
		return codage;
	}

	public void setCodage(Integer codage) {
		this.codage = codage;
	}

	public boolean isLogouted() {
		return logouted;
	}

	public void setLogouted(boolean logouted) {
		this.logouted = logouted;
	}

	@Override
	public String toString() {
		return "SecurityForm [id=" + id + ", analytic=" + analytic + ", secured=" + secured + ", codage=" + codage
				+ ", logouted=" + logouted + "]";
	}

}
