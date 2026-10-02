package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxEvent;

public class ExplorerPageEvent extends ExplorerPage {
	private static final long serialVersionUID = -3164545373989718486L;
	
	private final ExplorerInboxEvent inbox;
	
	public ExplorerPageEvent(final ExplorerMeta meta, final ExplorerInboxEvent inbox) {
		super(meta);
		this.inbox = inbox;
	}
	
	public ExplorerInboxEvent getInbox() {
		return inbox;
	}
	
	@Override
	public String getInboxId() {
		return inbox.getId();
	}

	@Override
	public String toString() {
		return "ExplorerPageEvent [inbox=" + inbox + "]";
	}

}
