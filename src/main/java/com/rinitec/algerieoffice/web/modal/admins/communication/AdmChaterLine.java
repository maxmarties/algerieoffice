package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Chater;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmChaterLine implements Serializable {
	private static final long serialVersionUID = 8379227858719131872L;
	
	private final String id;
	private final String urlAvatar;
	private final String username;
	private final String tradename;
	private final String message;
	private final String chaterDate;
	private final int type;
	
	public AdmChaterLine(final Chater chater, final User user, final String tradename) {
		this.id = chater.getId().toString();
		this.urlAvatar = user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.tradename = tradename;
		this.message = chater.getMessage();
		this.chaterDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(chater.getChaterDate());
		this.type = chater.getType() != null ? chater.getType() : 0;
	}

	public String getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public String getTradename() {
		return tradename;
	}

	public String getMessage() {
		return message;
	}

	public String getChaterDate() {
		return chaterDate;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AdmChaterLine [id=" + id + ", urlAvatar=" + urlAvatar + ", username=" + username + ", tradename="
				+ tradename + ", message=" + message + ", chaterDate=" + chaterDate + ", type=" + type + "]";
	}

}
