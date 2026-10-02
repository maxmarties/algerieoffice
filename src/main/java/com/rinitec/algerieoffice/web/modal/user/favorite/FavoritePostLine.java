package com.rinitec.algerieoffice.web.modal.user.favorite;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class FavoritePostLine extends FavoriteDocumentLine {
	private static final long serialVersionUID = -2812309544387402596L;
	
	private final int type;
	private final int priceType;
	private final String priceValue;
	
	public FavoritePostLine(final FavoriteDocument favoriteDocument, final Post post, final Integer priceType, 
			final Integer priceValue, final String tradename, final String companyURL) {
		super(favoriteDocument, post, tradename, companyURL);
		this.type = post.getService() ? 2 : 1;
		this.priceType = priceType;
		this.priceValue = ParseUtil.getFormattedOrder(priceValue);
	}

	public int getType() {
		return type;
	}

	public int getPriceType() {
		return priceType;
	}

	public String getPriceValue() {
		return priceValue;
	}

	@Override
	public String toString() {
		return "FavoritePostLine [type=" + type + ", priceType=" + priceType + ", priceValue=" + priceValue + "]";
	}

}
