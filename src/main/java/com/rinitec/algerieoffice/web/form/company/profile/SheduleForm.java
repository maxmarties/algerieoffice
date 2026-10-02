package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.validator.ValidEmail;
import com.rinitec.algerieoffice.web.validator.ValidPhone;

public class SheduleForm implements Serializable {
	private static final long serialVersionUID = 4884105183728151975L;

	@NotNull
	private Long id;
	
	@ValidEmail
    @NotNull
    @Size(min = 1, message = "{message.input.lenght}")
    private String email;
	
	@ValidPhone
	@NotNull
	private String phone;
	
	private String fax;
	private String mobile;
	
	private DaySheduleForm[] days = new DaySheduleForm[7];
	
	public SheduleForm() {
		for(int i = 0; i < 7; i++) {
			days[i] = new DaySheduleForm();
		}
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public DaySheduleForm[] getDays() {
		return days;
	}

	public void setDays(DaySheduleForm[] days) {
		this.days = days;
	}
	
	public String getFormattedPhone() {
		if(!StringUtils.isEmpty(phone)) {
			return "+213 (0) ".concat(String.format(Locale.FRENCH, "%,d", Long.valueOf(phone)));
		}
		return "-";
	}
	
	public String getFormattedFax() {
		if(!StringUtils.isEmpty(fax)) {
			return "+213 (0) ".concat(String.format(Locale.FRENCH, "%,d", Long.valueOf(fax)));
		}
		return "-";
	}
	
	public String getFormattedMobile() {
		if(!StringUtils.isEmpty(mobile)) {
			return "+213 (0) ".concat(String.format(Locale.FRENCH, "%,d", Long.valueOf(mobile)));
		}
		return "-";
	}

	@Override
	public String toString() {
		return "SheduleForm [id=" + id + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", mobile=" + mobile
				+ ", days=" + Arrays.toString(days) + "]";
	}
	
}
