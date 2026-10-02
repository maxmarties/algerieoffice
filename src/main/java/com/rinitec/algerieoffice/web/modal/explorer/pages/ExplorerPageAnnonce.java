package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxAnnonce;

public class ExplorerPageAnnonce extends ExplorerPage {
	private static final long serialVersionUID = -248858904976515261L;
	
	private final ExplorerInboxAnnonce inbox;
	
	public ExplorerPageAnnonce(final ExplorerMeta meta, final ExplorerInboxAnnonce inbox) {
		super(meta);
		this.inbox = inbox;
	}

	public ExplorerInboxAnnonce getInbox() {
		return inbox;
	}
	
	public Integer getVisibility() {
		return inbox.getVisibility();
	}
	
	@Override
	public String getInboxId() {
		return inbox.getId();
	}

	@Override
	public String toString() {
		return "ExplorerPageAnnonce [inbox=" + inbox + "]";
	}

}
