package com.rinitec.algerieoffice.web.modal.explorer.pages;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxContact;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetAgent;

public class ExplorerPageContact extends ExplorerPage {
	private static final long serialVersionUID = -4004772765461465835L;
	
	private final ExplorerInboxContact inbox;
	private final List<ExplorerWidgetAgent> agents;
	
	public ExplorerPageContact(final ExplorerMeta meta, final ExplorerInboxContact inbox, 
			final List<ExplorerWidgetAgent> agents) {
		super(meta);
		this.inbox = inbox;
		this.agents = agents;
	}

	public ExplorerInboxContact getInbox() {
		return inbox;
	}

	public List<ExplorerWidgetAgent> getAgents() {
		return agents;
	}
	
	@Override
	public String getInboxId() {
		return null;
	}

	@Override
	public String toString() {
		return "ExplorerPageContact [inbox=" + inbox + ", agents=" + agents + "]";
	}

}
