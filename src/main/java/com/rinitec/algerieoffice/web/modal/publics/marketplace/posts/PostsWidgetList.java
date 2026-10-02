package com.rinitec.algerieoffice.web.modal.publics.marketplace.posts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostsWidgetList implements Serializable {
	private static final long serialVersionUID = 3177212009273579012L;
	
	private final long countResult;
	private final List<PostWidgetMini> lines;
	
	public PostsWidgetList(final long countResult, final List<PostWidgetMini> lines) {
		this.countResult = countResult;
		this.lines = lines;
	}

	public long getCountResult() {
		return countResult;
	}

	public List<PostWidgetMini> getLines() {
		return lines;
	}
	
	public List<UUID> getPostsId() {
		final List<UUID> postsId = new ArrayList<UUID>();
		for (final PostWidgetMini line : lines) {
			postsId.add(line.getId());
		}
		return postsId;
	}
	
	public boolean isEmpty() {
		return lines.isEmpty();
	}

	@Override
	public String toString() {
		return "PostsWidgetList [countResult=" + countResult + ", lines=" + lines + "]";
	}

}
