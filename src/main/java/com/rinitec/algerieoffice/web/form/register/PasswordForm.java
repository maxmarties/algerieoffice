package com.rinitec.algerieoffice.web.form.register;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidMatches;

@ValidMatches
public class PasswordForm implements Serializable {
	private static final long serialVersionUID = -6226744101576696893L;

	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String password;
	
	@NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String matching;
	
	public PasswordForm() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatching() {
		return matching;
	}

	public void setMatching(String matching) {
		this.matching = matching;
	}

	@Override
	public String toString() {
		return "PasswordForm [password=" + password + ", matching=" + matching + "]";
	}
	
}
