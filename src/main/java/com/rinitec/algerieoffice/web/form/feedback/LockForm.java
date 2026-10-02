package com.rinitec.algerieoffice.web.form.feedback;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class LockForm implements Serializable {
	private static final long serialVersionUID = -6835569367993465698L;
	
	@NotNull
	private Long memberLock;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_ADRRESS, message = "{message.input.lenght}")
	private String reasonLock;
	
	public LockForm() {
	}
	
	public LockForm(final Long memberLock) {
		this.memberLock = memberLock;
	}

	public Long getMemberLock() {
		return memberLock;
	}

	public void setMemberLock(Long memberLock) {
		this.memberLock = memberLock;
	}

	public String getReasonLock() {
		return reasonLock;
	}

	public void setReasonLock(String reasonLock) {
		this.reasonLock = reasonLock;
	}

	@Override
	public String toString() {
		return "LockForm [memberLock=" + memberLock + ", reasonLock=" + reasonLock + "]";
	}

}
