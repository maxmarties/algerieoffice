package com.rinitec.algerieoffice.web.modal.explorer.pages;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyCategories;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetSticky;

public class ExplorerPagePosts extends ExplorerPage {
	private static final long serialVersionUID = -4793265855813191463L;
	
	private final ExplorerWidgetSticky sticky;
	private final List<ExplorerCompanyCategories> categories;
	
	public ExplorerPagePosts(final ExplorerMeta meta, final ExplorerWidgetSticky sticky, final List<ExplorerCompanyCategories> categories) {
		super(meta);
		this.sticky = sticky;
		this.categories = categories;
	}

	public ExplorerWidgetSticky getSticky() {
		return sticky;
	}

	public List<ExplorerCompanyCategories> getCategories() {
		return categories;
	}
	
	@Override
	public String getInboxId() {
		return null;
	}

	@Override
	public String toString() {
		return "ExplorerPagePosts [sticky=" + sticky + ", categories=" + categories + "]";
	}

}
