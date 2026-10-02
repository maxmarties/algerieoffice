package com.rinitec.algerieoffice.web.modal.forums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.forums.Topic;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class TopicMini implements Serializable {
	private static final long serialVersionUID = 5861576420202088154L;
	
	private final String id;
	private final Long userId;
	private final String title;
	private final String language;
	private final long commentCount;
	private List<String> usersAvatar = new ArrayList<String>();
	
	public TopicMini(final Topic topic, final Long commentCount) {
		this.id = topic.getId().toString();
		this.userId = topic.getUserId();
		this.title = topic.getTitle();
		this.language = topic.getLanguage();
		this.commentCount = commentCount;
	}
	
	public void initUsersAvatars(final List<Long> avatarsId) {
		if(!avatarsId.isEmpty()) {
			for (int i = 0; i < 5 && i < avatarsId.size(); i++) {
				this.usersAvatar.add(ConstraintesURL.URL_AVATARS + "?postedId=" + avatarsId.get(i) + "&type=" + AvatarType.account + "&width=30&height=30");
			}
		}
	}

	public List<String> getUsersAvatar() {
		return usersAvatar;
	}

	public void setUsersAvatar(List<String> usersAvatar) {
		this.usersAvatar = usersAvatar;
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

	public long getCommentCount() {
		return commentCount;
	}

	@Override
	public String toString() {
		return "TopicMini [id=" + id + ", userId=" + userId + ", title=" + title + ", language=" + language
				+ ", commentCount=" + commentCount + ", usersAvatar=" + usersAvatar + "]";
	}

}
