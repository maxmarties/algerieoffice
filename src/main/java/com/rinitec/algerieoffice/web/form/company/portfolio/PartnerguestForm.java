package com.rinitec.algerieoffice.web.form.company.portfolio;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rinitec.algerieoffice.web.validator.ValidEmail;

public class PartnerguestForm implements Serializable {
	private static final long serialVersionUID = -2216940250292625338L;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@NotNull
	private Boolean hasGuestpingled;
	
	public PartnerguestForm() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getHasGuestpingled() {
		return hasGuestpingled;
	}

	public void setHasGuestpingled(Boolean hasGuestpingled) {
		this.hasGuestpingled = hasGuestpingled;
	}

	@Override
	public String toString() {
		return "PartnerguestForm [email=" + email + ", hasGuestpingled=" + hasGuestpingled + "]";
	}

}
