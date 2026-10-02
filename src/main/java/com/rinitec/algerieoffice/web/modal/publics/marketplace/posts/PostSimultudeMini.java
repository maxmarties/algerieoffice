package com.rinitec.algerieoffice.web.modal.publics.marketplace.posts;

import java.util.UUID;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentSimultudeMini;

public class PostSimultudeMini extends DocumentSimultudeMini {
	private static final long serialVersionUID = -6591601892648260189L;
	
	private final UUID postId;

	public PostSimultudeMini(final UUID postId, final String title, final String identify, final String url, final Long forOrder1, final DateTime forOrder2) {
		super(title, ConstraintesURL.getMarketplacePostURL(identify, url));
		this.postId = postId;
	}
	
	public PostSimultudeMini(final UUID postId, final String title, final String identify, final String url, final DateTime forOrder) {
		super(title, ConstraintesURL.getMarketplacePostURL(identify, url));
		this.postId = postId;
	}

	public UUID getPostId() {
		return postId;
	}

	@Override
	public String toString() {
		return "PostSimultudeMini [postId=" + postId + "]";
	}
	
}
