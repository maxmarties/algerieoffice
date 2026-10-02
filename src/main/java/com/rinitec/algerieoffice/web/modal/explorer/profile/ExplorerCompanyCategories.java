package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import com.rinitec.algerieoffice.persistence.result.CategoryMini;

public class ExplorerCompanyCategories implements Serializable {
	private static final long serialVersionUID = -3000264704711499785L;
	
	private final CategoryMini item;
	private final List<CategoryMini> childs;
	
	public ExplorerCompanyCategories(final CategoryMini item, final List<CategoryMini> childs) {
		this.item = item;
		this.childs = childs;
	}

	public CategoryMini getItem() {
		return item;
	}

	public List<CategoryMini> getChilds() {
		return childs;
	}
	
	public Long count() {
		if(childs.isEmpty()) {
			return item.getCount();
		}
		long count = item.getCount();
		for (final CategoryMini child : childs) {
			count += child.getCount();
		}
		return count;
	}
	
	public boolean inChilds(final UUID categoryId) {
		for (final CategoryMini child : childs) {
			if(child.getId().equals(categoryId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "ExplorerCompanyCategories [item=" + item + ", childs=" + childs + "]";
	}

}
