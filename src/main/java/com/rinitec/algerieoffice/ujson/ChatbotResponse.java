package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;

public class ChatbotResponse implements Serializable {
	private static final long serialVersionUID = -3822892279545332706L;
	
	private Long companyId;
	private int domaine;
	private boolean discute;
	private Boolean account;
	private String email;
	private String message;
	
	public ChatbotResponse() {
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public int getDomaine() {
		return domaine;
	}

	public void setDomaine(int domaine) {
		this.domaine = domaine;
	}

	public boolean isDiscute() {
		return discute;
	}

	public void setDiscute(boolean discute) {
		this.discute = discute;
	}

	public Boolean getAccount() {
		return account;
	}

	public void setAccount(Boolean account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ChatbotResponse [companyId=" + companyId + ", domaine=" + domaine + ", discute=" + discute
				+ ", account=" + account + ", email=" + email + ", message=" + message + "]";
	}

}
