package com.rinitec.algerieoffice.web.modal.explorer.pages;

import java.util.List;

import com.rinitec.algerieoffice.web.modal.explorer.ExplorerMeta;
import com.rinitec.algerieoffice.web.modal.explorer.inboxs.ExplorerInboxtHome;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetActuality;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetBriefcase;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetLinked;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetPartner;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetPost;
import com.rinitec.algerieoffice.web.modal.explorer.widgets.ExplorerWidgetWork;

public class ExplorerPageHome extends ExplorerPage {
	private static final long serialVersionUID = -5565811236118782133L;
	
	private final String presentation;
	private final ExplorerWidgetBriefcase briefcase;
	private final ExplorerWidgetLinked linked;
	private final ExplorerInboxtHome inbox;
	private final Long countAgent;
	private final Long countNotice;
	private final List<ExplorerWidgetPost> posts;
	private final List<ExplorerWidgetActuality> actualities;
	private final List<ExplorerWidgetWork> works;
	private final List<ExplorerWidgetPartner> partners;
	
	public ExplorerPageHome(final ExplorerMeta meta, final String presentation, final ExplorerWidgetBriefcase briefcase, 
			final ExplorerWidgetLinked linked, final ExplorerInboxtHome inbox, final Long countAgent, final Long countNotice, 
			final List<ExplorerWidgetPost> posts, final List<ExplorerWidgetActuality> actualities, 
			final List<ExplorerWidgetWork> works, final List<ExplorerWidgetPartner> partners) {
		super(meta);
		this.presentation = presentation;
		this.briefcase = briefcase;
		this.linked = linked;
		this.inbox = inbox;
		this.countAgent = countAgent;
		this.countNotice = countNotice;
		this.posts = posts;
		this.actualities = actualities;
		this.works = works;
		this.partners = partners;
	}
	
	public String getPresentation() {
		return presentation;
	}
	
	public ExplorerWidgetBriefcase getBriefcase() {
		return briefcase;
	}
	
	public ExplorerWidgetLinked getLinked() {
		return linked;
	}
	
	public ExplorerInboxtHome getInbox() {
		return inbox;
	}
	
	public Long getCountAgent() {
		return countAgent;
	}
	
	public Long getCountNotice() {
		return countNotice;
	}
	
	public List<ExplorerWidgetPost> getPosts() {
		return posts;
	}
	
	public List<ExplorerWidgetActuality> getActualities() {
		return actualities;
	}
	
	public List<ExplorerWidgetWork> getWorks() {
		return works;
	}
	
	public List<ExplorerWidgetPartner> getPartners() {
		return partners;
	}
	
	@Override
	public String getInboxId() {
		return null;
	}

	@Override
	public String toString() {
		return "ExplorerPageHome [presentation=" + presentation + ", briefcase=" + briefcase + ", linked=" + linked
				+ ", inbox=" + inbox + ", countAgent=" + countAgent + ", countNotice=" + countNotice + ", posts="
				+ posts + ", actualities=" + actualities + ", works=" + works + ", partners=" + partners + "]";
	}
	
}
