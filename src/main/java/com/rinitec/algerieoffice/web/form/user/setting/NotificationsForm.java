package com.rinitec.algerieoffice.web.form.user.setting;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class NotificationsForm implements Serializable {
	private static final long serialVersionUID = 3797661885698966526L;
	
	@NotNull
	private Long id;
	
	private boolean[] communications = new boolean[5];
	private boolean[] notifications = new boolean[5];
	private boolean[] sounds = new boolean[4];
	
	public NotificationsForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean[] getCommunications() {
		return communications;
	}

	public void setCommunications(boolean[] communications) {
		this.communications = communications;
	}

	public boolean[] getNotifications() {
		return notifications;
	}

	public void setNotifications(boolean[] notifications) {
		this.notifications = notifications;
	}

	public boolean[] getSounds() {
		return sounds;
	}

	public void setSounds(boolean[] sounds) {
		this.sounds = sounds;
	}
	
	public void parseSounds(final boolean[] sounds) {
		for (int i = 0; i < 4; i++) {
			this.sounds[i] = sounds[i];
		}
	}
	
	public void parseCommunications(final String communications) {
		for (int i = 0; i < 5; i++) {
			this.communications[i] = (communications.charAt(i) == '1');
		}
	}
	
	public void parseNotifications(final String notifications) {
		this.notifications[0] = true;
		for (int i = 1; i < 5; i++) {
			this.notifications[i] = (notifications.charAt(i) == '1');
		}
	}
	
	public String builderCommunicatios() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			builder.append(communications[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public String builderNotifications() {
		final StringBuilder builder = new StringBuilder();
		builder.append("1");
		for (int i = 1; i < 5; i++) {
			builder.append(notifications[i] ? "1" : "0");
		}
		return builder.toString();
	}
	
	public void parseDefaultNotification() {
		for (int i = 0; i < 5; i++) {
			this.communications[i] = this.notifications[i] = true;
		}
	}

	@Override
	public String toString() {
		return "NotificationsForm [id=" + id + ", communications=" + communications + ", notifications=" + notifications
				+ ", sounds=" + sounds + "]";
	}

}
