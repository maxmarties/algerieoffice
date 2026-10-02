package com.rinitec.algerieoffice.web.modal.company.posts;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostSearch;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PostAccess implements Serializable {
	private static final long serialVersionUID = 3355916797212021368L;
	
	private final String photoURL;
	private final String title;
	private final String identifyURL;
	private final String category;
	private final String createdDate;
	private final long view;
	private final long clickCount;
	private final long workCount;
	
	public PostAccess(final PostSearch postSearch, final Post post, final UUID photoUUID, final String category) {
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(photoUUID.toString());
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getPostPreviewURL(post.getIdentify());
		this.category = !StringUtils.isEmpty(category) ? category : "--";
		this.createdDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(post.getCreatedDate());
		this.view = postSearch.getView();
		this.clickCount = postSearch.getClickCount();
		this.workCount = postSearch.getWorkCount();
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getCategory() {
		return category;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}

	public long getView() {
		return view;
	}

	public long getClickCount() {
		return clickCount;
	}

	public long getWorkCount() {
		return workCount;
	}

	@Override
	public String toString() {
		return "PostAccess [photoURL=" + photoURL + ", title=" + title + ", identifyURL=" + identifyURL + ", category="
				+ category + ", createdDate=" + createdDate + ", view=" + view + ", clickCount=" + clickCount
				+ ", workCount=" + workCount + "]";
	}

}
