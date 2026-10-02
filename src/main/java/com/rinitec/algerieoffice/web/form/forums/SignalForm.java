package com.rinitec.algerieoffice.web.form.forums;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.web.validator.ValidChose;

public class SignalForm implements Serializable {
	private static final long serialVersionUID = 3897630908802091694L;
	
	@NotNull
	private Long userSignal;
	
	@ValidChose
	@NotNull
	private Integer typeSignal;
	
	private String reasonSignal;
	
	@NotNull
	private String topicSignal;
	
	private String commentSignal;
	
	private boolean acceptSignal;
	
	public SignalForm() {
		this.acceptSignal = false;
	}
	
	public SignalForm(final Long userSignal) {
		this();
		this.userSignal = userSignal;
	}

	public Long getUserSignal() {
		return userSignal;
	}

	public void setUserSignal(Long userSignal) {
		this.userSignal = userSignal;
	}

	public Integer getTypeSignal() {
		return typeSignal;
	}

	public void setTypeSignal(Integer typeSignal) {
		this.typeSignal = typeSignal;
	}

	public String getReasonSignal() {
		return reasonSignal;
	}

	public void setReasonSignal(String reasonSignal) {
		this.reasonSignal = reasonSignal;
	}

	public String getTopicSignal() {
		return topicSignal;
	}

	public void setTopicSignal(String topicSignal) {
		this.topicSignal = topicSignal;
	}

	public String getCommentSignal() {
		return commentSignal;
	}

	public void setCommentSignal(String commentSignal) {
		this.commentSignal = commentSignal;
	}
	
	public boolean isAcceptSignal() {
		return acceptSignal;
	}
	
	public void setAcceptSignal(boolean acceptSignal) {
		this.acceptSignal = acceptSignal;
	}

	@Override
	public String toString() {
		return "SignalForm [userSignal=" + userSignal + ", typeSignal=" + typeSignal + ", reasonSignal=" + reasonSignal
				+ ", topicSignal=" + topicSignal + ", commentSignal=" + commentSignal + ", acceptSignal=" + acceptSignal + "]";
	}

}
