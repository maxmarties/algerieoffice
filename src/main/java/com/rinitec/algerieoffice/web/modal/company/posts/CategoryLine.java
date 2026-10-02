package com.rinitec.algerieoffice.web.modal.company.posts;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class CategoryLine implements Serializable {
	private static final long serialVersionUID = -4765576962321617678L;

	private final String id;
	private final String name;
	private final String identifyURL;
	private final String description;
	private final String parent;
	private final String sizePosts;
	private final boolean hasPingled;
	
	public CategoryLine(final Category category, final String parent, final Long countPost) {
		this.id = category.getId().toString();
		this.name = category.getName();
		this.identifyURL = ConstraintesURL.getCategoryPreviewURL(category.getIdentify());
		this.description = StringUtils.isEmpty(category.getDescription()) ? "-" : category.getDescription();
		this.parent = !StringUtils.isEmpty(parent) ? parent : "-";
		this.sizePosts = ParseUtil.getFormattedCount(countPost);
		this.hasPingled = category.getHasPingled();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
	}

	public String getParent() {
		return parent;
	}

	public String getSizePosts() {
		return sizePosts;
	}

	public boolean isHasPingled() {
		return hasPingled;
	}

	@Override
	public String toString() {
		return "CategoryLine [id=" + id + ", name=" + name + ", identifyURL=" + identifyURL + ", description="
				+ description + ", parent=" + parent + ", sizePosts=" + sizePosts + ", hasPingled=" + hasPingled + "]";
	}
	
}
