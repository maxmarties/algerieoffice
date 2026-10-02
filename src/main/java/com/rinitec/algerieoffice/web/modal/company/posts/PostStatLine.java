package com.rinitec.algerieoffice.web.modal.company.posts;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostSearch;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PostStatLine implements Serializable {
	private static final long serialVersionUID = 1042005725417546282L;

	private final String id;
	private final String title;
	private final String identifyURL;
	private final String modifiedDate;
	private final long token;
	private final long filter;
	private final long tag;
	private final long simultude;
	private final long view;
	private final long clickCount;
	private final boolean service;
	
	public PostStatLine(final Post post, final PostSearch postSearch) {
		this.id = post.getId().toString();
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getPostPreviewURL(post.getIdentify());
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
		this.token = postSearch.getToken();
		this.filter = postSearch.getFilter();
		this.tag = postSearch.getTag();
		this.simultude = postSearch.getSimultude();
		this.view = postSearch.getView();
		this.clickCount = postSearch.getClickCount();
		this.service = post.getService();
	}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public long getToken() {
		return token;
	}

	public long getFilter() {
		return filter;
	}

	public long getTag() {
		return tag;
	}

	public long getSimultude() {
		return simultude;
	}

	public long getView() {
		return view;
	}

	public long getClickCount() {
		return clickCount;
	}

	public boolean isService() {
		return service;
	}
	
	public int getPesrsentClicks() {
		return view > 0 ? (int) ((clickCount * 100) / view) : 0;
	}

	@Override
	public String toString() {
		return "PostStatLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", modifiedDate="
				+ modifiedDate + ", token=" + token + ", filter=" + filter + ", tag=" + tag + ", simultude=" + simultude
				+ ", view=" + view + ", clickCount=" + clickCount + ", service=" + service + "]";
	}
	
}
