package com.rinitec.algerieoffice.web.modal.company.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Campaign;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class CampaignLine implements Serializable {
	private static final long serialVersionUID = -5877456809575471614L;
	
	private static final int STATE_PUBLISHED = 1;
	private static final int STATE_CONFIRM = 3;
	private static final int STATE_WAIT = 4;
	private static final int STATE_INPUBLISH = 5;
	private static final int STATE_TRASHED = 6;
	private static final int STATE_DELETED = 7;
	
	private final String id;
	private final String title;
	private final String autor;
	private final int type;
	private final int state;
	private final String creditCount;
	private final String viewCount;
	private final String clickCount;
	private final int potentiel;
	
	public CampaignLine(final Campaign campaign, final String autor, final Long orderCount, final Post post) {
		this.id = campaign.getId().toString();
		this.title = post.getTitle();
		this.autor = autor;
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.state = campaign.parseCreditCount() > 0 ? post == null ? STATE_DELETED : post.getHasTrashed() ? STATE_TRASHED 
				: !post.getHasPublished() ? STATE_INPUBLISH : STATE_PUBLISHED : orderCount == 0L ? STATE_WAIT : STATE_CONFIRM;
		this.creditCount = ParseUtil.getFormattedOrder(campaign.getCreditCount());
		this.viewCount = ParseUtil.getFormattedOrder(campaign.getViewCount());
		this.clickCount = ParseUtil.getFormattedOrder(campaign.getClickCount());
		this.potentiel = campaign.parseCreditCount();
	}
	
	public CampaignLine(final Campaign campaign, final String autor, final Long orderCount, final Annonce annonce) {
		this.id = campaign.getId().toString();
		this.title = annonce.getTitle();
		this.autor = autor;
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.state = campaign.parseCreditCount() > 0 ? annonce == null ? STATE_DELETED : annonce.getHasTrashed() ? STATE_TRASHED 
				: !annonce.getHasPublished() ? STATE_INPUBLISH : STATE_PUBLISHED : orderCount == 0L ? STATE_WAIT : STATE_CONFIRM;
		this.creditCount = ParseUtil.getFormattedOrder(campaign.getCreditCount());
		this.viewCount = ParseUtil.getFormattedOrder(campaign.getViewCount());
		this.clickCount = ParseUtil.getFormattedOrder(campaign.getClickCount());
		this.potentiel = campaign.parseCreditCount();
	}
	
	public CampaignLine(final Campaign campaign, final String autor, final Long orderCount, final Event event) {
		this.id = campaign.getId().toString();
		this.title = event.getTitle();
		this.autor = autor;
		this.type = ParseUtil.parseTypeDocument(campaign.getType());
		this.state = campaign.parseCreditCount() > 0 ? event == null ? STATE_DELETED : !event.getHasPublished() ? STATE_INPUBLISH 
				: STATE_PUBLISHED : orderCount == 0L ? STATE_WAIT : STATE_CONFIRM;
		this.creditCount = ParseUtil.getFormattedOrder(campaign.getCreditCount());
		this.viewCount = ParseUtil.getFormattedOrder(campaign.getViewCount());
		this.clickCount = ParseUtil.getFormattedOrder(campaign.getClickCount());
		this.potentiel = campaign.parseCreditCount();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAutor() {
		return autor;
	}

	public int getType() {
		return type;
	}

	public int getState() {
		return state;
	}

	public String getCreditCount() {
		return creditCount;
	}

	public String getViewCount() {
		return viewCount;
	}

	public String getClickCount() {
		return clickCount;
	}
	
	public int getPotentiel() {
		return potentiel;
	}
	
	public String parsePotentiel() {
		return ParseUtil.getFormattedOrder(potentiel);
	}

	@Override
	public String toString() {
		return "CampaignLine [id=" + id + ", title=" + title + ", autor=" + autor + ", type=" + type + ", state="
				+ state + ", creditCount=" + creditCount + ", viewCount=" + viewCount + ", clickCount=" + clickCount
				+ ", potentiel=" + potentiel + "]";
	}
	
}
