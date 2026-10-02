package com.rinitec.algerieoffice.web.modal.explorer.pages;

import java.io.Serializable;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;

public abstract class ExplorerPage implements Serializable {
	private static final long serialVersionUID = -2678722452729626831L;
	
	private final ExplorerMeta meta;
	
	public ExplorerPage(final ExplorerMeta meta) {
		this.meta = meta;
	}
	
	public ExplorerMeta getMeta() {
		return meta;
	}
	
	public abstract String getInboxId();

	@Override
	public String toString() {
		return "ExplorerPage [meta=" + meta + "]";
	}

}
