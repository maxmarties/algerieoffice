package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxWork;

public class ExplorerPageWork extends ExplorerPage {
	private static final long serialVersionUID = 7597376158247029728L;
	
	private final ExplorerInboxWork inbox;

	public ExplorerPageWork(final ExplorerMeta meta, final ExplorerInboxWork inbox) {
		super(meta);
		this.inbox = inbox;
	}

	public ExplorerInboxWork getInbox() {
		return inbox;
	}
	
	@Override
	public String getInboxId() {
		return inbox.getId();
	}

	@Override
	public String toString() {
		return "ExplorerPageWork [inbox=" + inbox + "]";
	}

}
