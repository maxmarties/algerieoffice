package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Sticky;

public class ExplorerWidgetSticky implements Serializable {
	private static final long serialVersionUID = 8983729528146004098L;
	
	private final String title;
	private final String description;
	private final String label;
	private final boolean target;
	private final String urlExtern;
	
	public ExplorerWidgetSticky(final Sticky sticky) {
		if(sticky != null) {
			this.title = sticky.getTitle();
			this.description = sticky.getDescription();
			this.label = sticky.getLabel();
			this.target = StringUtils.isEmpty(sticky.getUrlExtern());
			this.urlExtern = sticky.getUrlExtern();
		} else {
			this.title = this.description = this.label = this.urlExtern = null;
			this.target = true;
		}
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getLabel() {
		return label;
	}

	public boolean isTarget() {
		return target;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetSticky [title=" + title + ", description=" + description + ", label=" + label
				+ ", target=" + target + ", urlExtern=" + urlExtern + "]";
	}

}
