package com.rinitec.algerieoffice.web.listener.events;

public class OnAccessBlogEvent {

	private final String blogId;
	private final boolean viewed;
	
	public OnAccessBlogEvent(final String blogId, final boolean viewed) {
		this.blogId = blogId;
		this.viewed = viewed;
	}
	
	public String getBlogId() {
		return blogId;
	}
	
	public boolean isViewed() {
		return viewed;
	}

	@Override
	public String toString() {
		return "OnAccessBlogEvent [blogId=" + blogId + ", viewed=" + viewed + "]";
	}
	
}
