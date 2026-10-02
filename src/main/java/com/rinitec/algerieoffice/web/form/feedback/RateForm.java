package com.rinitec.algerieoffice.web.form.feedback;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class RateForm implements Serializable {
	private static final long serialVersionUID = 4246275163898331633L;
	
	@NotNull
	private Long memberRate;
	
	@ValidChose
	@NotNull
	private Integer typeRate;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String reasonRate;
	
	private boolean acceptRate;
	
	public RateForm() {
		this.acceptRate = false;
	}
	
	public RateForm(final Long memberRate) {
		this();
		this.memberRate = memberRate;
	}

	public Long getMemberRate() {
		return memberRate;
	}

	public void setMemberRate(Long memberRate) {
		this.memberRate = memberRate;
	}

	public Integer getTypeRate() {
		return typeRate;
	}

	public void setTypeRate(Integer typeRate) {
		this.typeRate = typeRate;
	}

	public String getReasonRate() {
		return reasonRate;
	}

	public void setReasonRate(String reasonRate) {
		this.reasonRate = reasonRate;
	}

	public boolean isAcceptRate() {
		return acceptRate;
	}

	public void setAcceptRate(boolean acceptRate) {
		this.acceptRate = acceptRate;
	}

	@Override
	public String toString() {
		return "RateForm [memberRate=" + memberRate + ", typeRate=" + typeRate + ", reasonRate=" + reasonRate
				+ ", acceptRate=" + acceptRate + "]";
	}

}
