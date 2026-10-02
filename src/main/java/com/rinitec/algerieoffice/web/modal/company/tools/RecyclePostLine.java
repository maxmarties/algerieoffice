package com.rinitec.algerieoffice.web.modal.company.tools;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;

public class RecyclePostLine implements Serializable {
	private static final long serialVersionUID = 4126744515000274938L;
	
	private final String id;
	private final String title;
	private final String category;
	private final String modifiedDate;
	private final String autor;
	private final boolean service;
	
	public RecyclePostLine(final Post post, final String autor, final String category) {
		this.id = post.getId().toString();
		this.title = post.getTitle();
		this.category = !StringUtils.isEmpty(category) ? category : "-";
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
		this.autor = autor;
		this.service = post.getService();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getAutor() {
		return autor;
	}

	public boolean isService() {
		return service;
	}

	@Override
	public String toString() {
		return "RecyclePostLine [id=" + id + ", title=" + title + ", category=" + category + ", modifiedDate="
				+ modifiedDate + ", autor=" + autor + ", service=" + service + "]";
	}

}
