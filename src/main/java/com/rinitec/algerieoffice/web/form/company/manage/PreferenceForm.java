package com.rinitec.algerieoffice.web.form.company.manage;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class PreferenceForm implements Serializable {
	private static final long serialVersionUID = -3052958509941845210L;
	
	@NotNull
	private Long id;
	
	private String companyURL;
	private String email;
	private String phone;
	
	@NotNull(message = "{message.input.required}")
	private String language;
	
	private Long messengerId;
	
	private boolean hasActive;
	private boolean disabledMessenger;
	
	private boolean[] communications = new boolean[7];
	private boolean[] perspects = new boolean[6];
	private boolean[] mails = new boolean[5];
	
	private boolean published;
	
	public PreferenceForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyURL() {
		return companyURL;
	}
	
	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getMessengerId() {
		return messengerId;
	}

	public void setMessengerId(Long messengerId) {
		this.messengerId = messengerId;
	}

	public boolean isHasActive() {
		return hasActive;
	}
	
	public void setHasActive(boolean hasActive) {
		this.hasActive = hasActive;
	}

	public boolean isDisabledMessenger() {
		return disabledMessenger;
	}
	
	public void setDisabledMessenger(boolean disabledMessenger) {
		this.disabledMessenger = disabledMessenger;
	}

	public boolean[] getCommunications() {
		return communications;
	}

	public void setCommunications(boolean[] communications) {
		this.communications = communications;
	}

	public boolean[] getPerspects() {
		return perspects;
	}

	public void setPerspects(boolean[] perspects) {
		this.perspects = perspects;
	}

	public boolean[] getMails() {
		return mails;
	}

	public void setMails(boolean[] mails) {
		this.mails = mails;
	}
	
	public boolean isPublished() {
		return published;
	}
	
	public void setPublished(boolean published) {
		this.published = published;
	}
	
	public void parseCommunications(final String communications) {
		for (int i = 0; i < 7; i++) {
			this.communications[i] = (communications.charAt(i) == '1');
		}
	}
	
	public void parsePerspects(final String perspects) {
		for (int i = 0; i < 6; i++) {
			this.perspects[i] = (perspects.charAt(i) == '1');
		}
	}
	
	public void parseMails(final String mails) {
		for (int i = 0; i < 5; i++) {
			this.mails[i] = (mails.charAt(i) == '1');
		}
	}
	
	public String builderCommunicatios() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			builder.append(communications[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderPerspects() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			builder.append(perspects[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderMails() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			builder.append(mails[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public void parseDefaultNotification() {
		for (int i = 0; i < 7; i++) {
			this.communications[i] = true;
		}
		for (int i = 0; i < 6; i++) {
			this.perspects[i] = true;
		}
		for (int i = 0; i < 5; i++) {
			this.mails[i] = true;
		}
	}

	@Override
	public String toString() {
		return "PreferenceForm [id=" + id + ", companyURL=" + companyURL + ", email=" + email + ", phone=" + phone
				+ ", language=" + language + ", messengerId=" + messengerId + ", hasActive=" + hasActive
				+ ", disabledMessenger=" + disabledMessenger + ", communications=" + communications + ", perspects="
				+ perspects + ", mails=" + mails + ", published=" + published + "]";
	}

}
