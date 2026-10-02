package com.rinitec.algerieoffice.web.listener.events;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class OnReplyEvent {

	private final User user;
	private final Topic topic;
	private final TopicComment topicComment;
	private final Locale locale;
	
	public OnReplyEvent(final User user, final Topic topic, final TopicComment topicComment, final HttpServletRequest request) {
		this.user = user;
		this.topic = topic;
		this.topicComment = topicComment;
		this.locale = RequestContextUtils.getLocale(request);
	}

	public User getUser() {
		return user;
	}

	public Topic getTopic() {
		return topic;
	}

	public TopicComment getTopicComment() {
		return topicComment;
	}

	public Locale getLocale() {
		return locale;
	}
	
	public String getUrlAvatar() {
		return user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=44&height=44"
				: "/static/picts/avatars/account_mini-min.jpg";
	}

	@Override
	public String toString() {
		return "OnReplyEvent [user=" + user + ", topic=" + topic + ", topicComment=" + topicComment + ", locale="
				+ locale + "]";
	}
	
}
