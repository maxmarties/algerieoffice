package com.rinitec.algerieoffice.web.modal.explorer.pages;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxEmploye;

public class ExplorerPageEmploye extends ExplorerPage {
	private static final long serialVersionUID = -7863608686310953134L;
	
	private final ExplorerInboxEmploye inbox;
	
	public ExplorerPageEmploye(final ExplorerMeta meta, final ExplorerInboxEmploye inbox) {
		super(meta);
		this.inbox = inbox;
	}

	public ExplorerInboxEmploye getInbox() {
		return inbox;
	}
	
	@Override
	public String getInboxId() {
		return inbox.getId();
	}

	@Override
	public String toString() {
		return "ExplorerPageEmploye [inbox=" + inbox + "]";
	}
	
}
