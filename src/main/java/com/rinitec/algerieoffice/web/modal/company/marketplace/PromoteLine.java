package com.rinitec.algerieoffice.web.modal.company.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class PromoteLine implements Serializable {
	private static final long serialVersionUID = -1390936443507518106L;
	
	private static final int STATE_PUBLISHED = 1;
	private static final int STATE_PROGRESS = 2;
	private static final int STATE_CONFIRM = 3;
	private static final int STATE_WAIT = 4;
	
	private final String id;
	private final String title;
	private final String url;
	private final String autor;
	private final int state;
	private final String creditCount;
	private final String viewCount;
	private final String clickCount;
	private final int potentiel;
	
	public PromoteLine(final Promote promote, final String autor, final Long orderCount) {
		this.id = promote.getId().toString();
		this.title = promote.getTitle();
		this.url = promote.getUrl();
		this.autor = autor;
		this.state = promote.parseCreditCount() > 0 ? promote.isEnabled() ? STATE_PUBLISHED : STATE_PROGRESS 
				: orderCount == 0L ? STATE_WAIT : STATE_CONFIRM;
		this.creditCount = ParseUtil.getFormattedOrder(promote.getCreditCount());
		this.viewCount = ParseUtil.getFormattedOrder(promote.getViewCount());
		this.clickCount = ParseUtil.getFormattedOrder(promote.getClickCount());
		this.potentiel = promote.parseCreditCount();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getAutor() {
		return autor;
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
		return "PromoteLine [id=" + id + ", title=" + title + ", url=" + url + ", autor=" + autor + ", state=" + state
				+ ", creditCount=" + creditCount + ", viewCount=" + viewCount + ", clickCount=" + clickCount
				+ ", potentiel=" + potentiel + "]";
	}

}
