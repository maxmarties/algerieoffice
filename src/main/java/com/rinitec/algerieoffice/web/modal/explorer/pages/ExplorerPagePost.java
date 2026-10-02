package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxPost;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetCredit;

public class ExplorerPagePost extends ExplorerPage {
	private static final long serialVersionUID = -1555360208374611015L;
	
	private final ExplorerInboxPost inbox;
	private final ExplorerWidgetCredit credit;
	
	public ExplorerPagePost(final ExplorerMeta meta, final ExplorerInboxPost inbox, final ExplorerWidgetCredit credit) {
		super(meta);
		this.inbox = inbox;
		this.credit = credit;
	}

	public ExplorerInboxPost getInbox() {
		return inbox;
	}

	public ExplorerWidgetCredit getCredit() {
		return credit;
	}
	
	@Override
	public String getInboxId() {
		return inbox.getId();
	}

	@Override
	public String toString() {
		return "ExplorerPagePost [inbox=" + inbox + ", credit=" + credit + "]";
	}

}
