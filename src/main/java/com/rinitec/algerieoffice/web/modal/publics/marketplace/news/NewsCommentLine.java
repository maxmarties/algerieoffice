package com.rinitec.algerieoffice.web.modal.publics.marketplace.news;

import java.io.Serializable;

import org.jadira.usertype.spi.utils.lang.StringUtils;
import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.ActualityComment;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class NewsCommentLine implements Serializable {
	private static final long serialVersionUID = -3104268720312469688L;
	
	private final Long userId;
	private final String username;
	private final String urlAvatar;
	private final String companyname;
	private final String commentId;
	private final String message;
	private final DateTime postedDate;
	private final String formatDate;
	private final Long likeCount;
	private final boolean liked;
	
	private String email;
	private boolean online;
	
	public NewsCommentLine(final ActualityComment actualityComment, final User user, final String tradename, final Long likeCount, final Long userId, 
			final DateTime forOrder) {
		this.userId = user.getId();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=40&height=40"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.companyname = !StringUtils.isEmpty(tradename) ? tradename : null;
		this.commentId = actualityComment.getId().toString();
		this.message = actualityComment.getMessage();
		this.postedDate = actualityComment.getPostedDate();
		this.formatDate = ParseUtil.hasToday(actualityComment.getPostedDate()) ? "HH:mm" : "dd MMMM, HH:mm";
		this.likeCount = likeCount;
		this.liked = userId != null;
		this.email = user.getEmail();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
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

	public String getCompanyname() {
		return companyname;
	}

	public String getCommentId() {
		return commentId;
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

	public boolean isLiked() {
		return liked;
	}
	
	public void updateOnline(final boolean hasOnline) {
		this.online = hasOnline;
		this.email = null;
	}

	@Override
	public String toString() {
		return "NewsCommentLine [userId=" + userId + ", username=" + username + ", urlAvatar=" + urlAvatar
				+ ", companyname=" + companyname + ", commentId=" + commentId + ", message=" + message + ", postedDate="
				+ postedDate + ", formatDate=" + formatDate + ", likeCount=" + likeCount + ", liked=" + liked
				+ ", email=" + email + ", online=" + online + "]";
	}

}
