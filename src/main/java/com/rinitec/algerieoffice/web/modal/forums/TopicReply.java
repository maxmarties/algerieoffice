package com.rinitec.algerieoffice.web.modal.forums;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.forums.TopicComment;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class TopicReply implements Serializable {
	private static final long serialVersionUID = -7432382244150000163L;

	private final String id;
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String parentId;
	private final String message;
	private final DateTime postedDate;
	private final String formatDate;
	private final Long likeCount;
	private final Long replyCount;
	private final boolean liked;
	
	public TopicReply(final TopicComment topicComment, final User user, final Long likeCount, final Long replyCount, final Long userId, final DateTime forOrder) {
		this.id = topicComment.getId().toString();
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.parentId = topicComment.getParentUUID() != null ? topicComment.getParentUUID().toString() : "";
		this.message = new String(topicComment.getMessage());
		this.postedDate = topicComment.getPostedDate();
		this.formatDate = ParseUtil.hasToday(topicComment.getPostedDate()) ? "HH:mm" : "dd MMMM, HH:mm";
		this.likeCount = likeCount;
		this.replyCount = replyCount;
		this.liked = userId != null;
	}
	
	public TopicReply(final TopicComment topicComment, final User user, final Long likeCount, final Long userId, final DateTime forOrder) {
		this.id = topicComment.getId().toString();
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.parentId = topicComment.getParentUUID() != null ? topicComment.getParentUUID().toString() : "";
		this.message = new String(topicComment.getMessage());
		this.postedDate = topicComment.getPostedDate();
		this.formatDate = ParseUtil.hasToday(topicComment.getPostedDate()) ? "HH:mm" : "dd MMMM, HH:mm";
		this.likeCount = likeCount;
		this.replyCount = null;
		this.liked = userId != null;
	}

	public String getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getParentId() {
		return parentId;
	}

	public String getMessage() {
		return message;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public Long getReplyCount() {
		return replyCount;
	}

	public boolean isLiked() {
		return liked;
	}

	@Override
	public String toString() {
		return "TopicReply [id=" + id + ", userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", parentId=" + parentId + ", message=" + message + ", postedDate=" + postedDate + ", formatDate="
				+ formatDate + ", likeCount=" + likeCount + ", replyCount=" + replyCount + ", liked=" + liked + "]";
	}

}
