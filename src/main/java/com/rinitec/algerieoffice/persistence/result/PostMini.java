package com.rinitec.algerieoffice.persistence.result;

public class PostMini {

	private final String title;
	private final String identify;
	
	public PostMini(final String title, final String identify) {
		this.title = title;
		this.identify = identify;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getIdentify() {
		return identify;
	}

	@Override
	public String toString() {
		return "PostMini [title=" + title + ", identify=" + identify + "]";
	}
	
}
