package com.rinitec.algerieoffice.web.form.company.team;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rinitec.algerieoffice.web.validator.ValidChose;
import com.rinitec.algerieoffice.web.validator.ValidEmail;

public class TeamguestForm implements Serializable {
	private static final long serialVersionUID = 3008999300852206378L;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String guestmail;
	
	@ValidChose
	private Integer guestrole;
	
	public TeamguestForm() {
	}

	public String getGuestmail() {
		return guestmail;
	}

	public void setGuestmail(String guestmail) {
		this.guestmail = guestmail;
	}

	public Integer getGuestrole() {
		return guestrole;
	}

	public void setGuestrole(Integer guestrole) {
		this.guestrole = guestrole;
	}

	@Override
	public String toString() {
		return "TeamguestForm [guestmail=" + guestmail + ", guestrole=" + guestrole + "]";
	}

}
