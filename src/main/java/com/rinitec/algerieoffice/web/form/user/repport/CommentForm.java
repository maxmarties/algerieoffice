package com.rinitec.algerieoffice.web.form.user.repport;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class CommentForm implements Serializable {
	private static final long serialVersionUID = 6276847218482480191L;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private String actualityId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_MESSAGE, message = "{message.input.lenght}")
	private String message;
	
	public CommentForm() {
	}
	
	public CommentForm(final Long userId, final String actualityId) {
		this.userId = userId;
		this.actualityId = actualityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getActualityId() {
		return actualityId;
	}

	public void setActualityId(String actualityId) {
		this.actualityId = actualityId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CommentForm [userId=" + userId + ", actualityId=" + actualityId + ", message=" + message + "]";
	}

}
