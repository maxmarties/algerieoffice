package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmMessageLine implements Serializable {
	private static final long serialVersionUID = -249061902745491131L;
	
	private final String id;
	private final String urlAvatar;
	private final String sender;
	private final String tradename;
	private final String recepient;
	private final String message;
	private final String postedDate;
	private final boolean consulted;
	private final boolean emojis;
	
	public AdmMessageLine(final Message message, final User user, final String tradename, final String recepient) {
		this.id = message.getId().toString();
		this.urlAvatar = user.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=32&height=32" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.sender = user.getDisplayName();
		this.tradename = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.recepient = recepient;
		this.message = message.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(message.getPostedDate());
		this.consulted = message.getConsulted();
		this.emojis = message.isEmojis();
	}

	public String getId() {
		return id;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getSender() {
		return sender;
	}

	public String getTradename() {
		return tradename;
	}

	public String getRecepient() {
		return recepient;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public boolean isEmojis() {
		return emojis;
	}

	@Override
	public String toString() {
		return "AdmMessageLine [id=" + id + ", urlAvatar=" + urlAvatar + ", sender=" + sender + ", tradename="
				+ tradename + ", recepient=" + recepient + ", message=" + message + ", postedDate=" + postedDate
				+ ", consulted=" + consulted + ", emojis=" + emojis + "]";
	}

}
