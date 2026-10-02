package com.rinitec.algerieoffice.web.form.user.account;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.modal.user.account.IdentityProvider;

public class IdentitiesForm implements Serializable {
	private static final long serialVersionUID = -7863255160551711801L;
	
	@NotNull
	private Long id;
	
	private String email;
	private String phone;
	private IdentityProvider[] social = new IdentityProvider[3];
	
	private boolean enabledEmail;
	private boolean enabledPhone;
	
	public IdentitiesForm() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public IdentityProvider[] getSocial() {
		return social;
	}
	
	public void setSocial(IdentityProvider[] social) {
		this.social = social;
	}

	public boolean isEnabledEmail() {
		return enabledEmail;
	}

	public void setEnabledEmail(boolean enabledEmail) {
		this.enabledEmail = enabledEmail;
	}

	public boolean isEnabledPhone() {
		return enabledPhone;
	}

	public void setEnabledPhone(boolean enabledPhone) {
		this.enabledPhone = enabledPhone;
	}
	
	public String getFormattedPhone() {
		return "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(phone));
	}
	
	public int countValidIdentities() {
		int valid = 0;
		if(enabledEmail) valid++;
		if(enabledPhone) valid++;
		for(int i = 0; i < 3; i++) {
			if(social[i] != null) valid++;
		}
		return valid;
	}

	@Override
	public String toString() {
		return "IdentitiesForm [id=" + id + ", email=" + email + ", phone=" + phone + ", social="
				+ Arrays.toString(social) + ", enabledEmail=" + enabledEmail + ", enabledPhone=" + enabledPhone + "]";
	}

}
