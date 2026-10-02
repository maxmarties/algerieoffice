package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.inbox.Message;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class MessagePopup implements Serializable {
	private static final long serialVersionUID = -5582382276839218948L;
	
	private final Long senderId;
	private final String avatarURL;
	private final String message;
	private final String time;
	private final boolean emojis;
	
	public MessagePopup(final Message message, final Long userId, final Boolean hasAvatar) {
		this.senderId = message.getSenderId();
		this.avatarURL = hasAvatar ? ConstraintesURL.URL_AVATARS + "?postedId=" + userId + "&type=" + AvatarType.account + "&width=34&height=34"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.message = message.getMessage();
		this.time = DateTimeFormat.forPattern(ParseUtil.parseFormatDateMessage(message.getPostedDate())).print(message.getPostedDate());
		this.emojis = message.isEmojis();
	}

	public Long getSenderId() {
		return senderId;
	}

	public String getAvatarURL() {
		return avatarURL;
	}

	public String getMessage() {
		return message;
	}

	public String getTime() {
		return time;
	}
	
	public boolean isEmojis() {
		return emojis;
	}

	@Override
	public String toString() {
		return "MessagePopup [senderId=" + senderId + ", avatarURL=" + avatarURL + ", message=" + message + ", time="
				+ time + ", emojis=" + emojis + "]";
	}

}
