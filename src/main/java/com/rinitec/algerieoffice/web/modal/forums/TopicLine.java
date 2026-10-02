package com.rinitec.algerieoffice.web.modal.forums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class TopicLine implements Serializable {
	private static final long serialVersionUID = 1517553375088617899L;
	
	private final String id;
	private final Long userId;
	private final String title;
	private final String language;
	private final DateTime createdDate;
	private final String urlAvatar;
	private final String username;
	private final int category;
	private final boolean quiz;
	private final boolean marked;
	private final int viewCount;
	private final long commentCount;
	private List<String> usersAvatar = new ArrayList<String>();
	
	public TopicLine(final User user, final Topic topic, final Long commentCount, final Long markedId) {
		this.id = topic.getId().toString();
		this.userId = topic.getUserId();
		this.title = topic.getTitle();
		this.language = topic.getLanguage();
		this.createdDate = topic.getCreatedDate();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=38&height=38" 
				: "/static/picts/avatars/account_mini-min.jpg";
		this.username = user.getDisplayName();
		this.category = topic.getCategory();
		this.quiz = topic.getHasQuiz();
		this.marked = markedId != null;
		this.viewCount = topic.getViewCount();
		this.commentCount = commentCount;
	}
	
	public TopicLine(final User user, final Topic topic, final Long commentCount, final Long markedId, final DateTime forOrder1, final Integer forOrder2, 
			final DateTime forOrder3, final String forOrder4) {
		this(user, topic, commentCount, markedId);
	}
	
	public void initUsersAvatars(final List<Long> avatarsId) {
		if(!avatarsId.isEmpty()) {
			for (int i = 0; i < 5 && i < avatarsId.size(); i++) {
				this.usersAvatar.add(ConstraintesURL.URL_AVATARS + "?postedId=" + avatarsId.get(i) + "&type=" + AvatarType.account + "&width=30&height=30");
			}
		}
	}

	public String getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getLanguage() {
		return language;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getUsername() {
		return username;
	}

	public int getCategory() {
		return category;
	}

	public boolean isQuiz() {
		return quiz;
	}

	public boolean isMarked() {
		return marked;
	}

	public int getViewCount() {
		return viewCount;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public List<String> getUsersAvatar() {
		return usersAvatar;
	}
	
	public void setUsersAvatar(List<String> usersAvatar) {
		this.usersAvatar = usersAvatar;
	}
	
	public String parseViewCount() {
		return ParseUtil.getFormattedTopicValue(viewCount);
	}
	
	public String parseCommentCount() {
		return ParseUtil.getFormattedTopicValue(commentCount);
	}

	@Override
	public String toString() {
		return "TopicLine [id=" + id + ", userId=" + userId + ", title=" + title + ", language=" + language
				+ ", createdDate=" + createdDate + ", urlAvatar=" + urlAvatar + ", username=" + username + ", category="
				+ category + ", quiz=" + quiz + ", marked=" + marked + ", viewCount=" + viewCount + ", commentCount="
				+ commentCount + ", usersAvatar=" + usersAvatar + "]";
	}

}
