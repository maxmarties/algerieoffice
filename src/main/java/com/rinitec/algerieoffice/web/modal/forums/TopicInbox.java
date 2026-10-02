package com.rinitec.algerieoffice.web.modal.forums;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class TopicInbox implements Serializable {
	private static final long serialVersionUID = -6111508416073796350L;
	
	private final String id;
	private final String title;
	private final String username;
	private final String urlAvatar;
	private final String detail;
	private final String language;
	private final DateTime createdDate;
	private final String modifiedDate;
	private final Long autorId;
	private final Long likeCount;
	private final Long commentCount;
	private final Long userCount;
	private final int category;
	private final int viewCount;
	private final boolean liked;
	private final boolean marked;
	private final boolean hasQuiz;
	
	public TopicInbox(final Topic topic, final User user, final Long likeCount, final Long commentCount, final Long userCount, final Long likedId, final Long markedId) {
		this.id = topic.getId().toString();
		this.title = topic.getTitle();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=48&height=48"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.detail = new String(topic.getDetail());
		this.language = topic.getLanguage();
		this.createdDate = topic.getCreatedDate();
		this.modifiedDate = topic.getModifiedDate() != null ? DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(topic.getModifiedDate()) : null;
		this.autorId = user.getId();
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.userCount = userCount;
		this.category = topic.getCategory();
		this.viewCount = topic.getViewCount() + 1;
		this.liked = likedId != null;
		this.marked = markedId != null;
		this.hasQuiz = topic.getHasQuiz();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getDetail() {
		return detail;
	}

	public String getLanguage() {
		return language;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public Long getAutorId() {
		return autorId;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public Long getUserCount() {
		return userCount;
	}

	public int getCategory() {
		return category;
	}

	public int getViewCount() {
		return viewCount;
	}

	public boolean isLiked() {
		return liked;
	}

	public boolean isMarked() {
		return marked;
	}
	
	public boolean isHasQuiz() {
		return hasQuiz;
	}

	@Override
	public String toString() {
		return "TopicInbox [id=" + id + ", title=" + title + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", detail=" + detail + ", language=" + language + ", createdDate=" + createdDate + ", modifiedDate="
				+ modifiedDate + ", autorId=" + autorId + ", likeCount=" + likeCount + ", commentCount=" + commentCount
				+ ", userCount=" + userCount + ", category=" + category + ", viewCount=" + viewCount + ", liked="
				+ liked + ", marked=" + marked + ", hasQuiz=" + hasQuiz + "]";
	}

}
