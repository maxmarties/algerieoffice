package com.rinitec.algerieoffice.web.modal.user.favorite;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;

public class FavoriteAnnonceLine extends FavoriteDocumentLine {
	private static final long serialVersionUID = 6873387586325071670L;
	
	private final int type;
	private final String period;
	
	public FavoriteAnnonceLine(final FavoriteDocument favoriteDocument, final Annonce annonce, final String tradename, final String companyURL) {
		super(favoriteDocument, annonce, tradename, companyURL);
		this.type = annonce.getType();
		this.period = DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getStartDate()).concat(" - ")
				.concat(DateTimeFormat.forPattern("dd/MM/yyyy").print(annonce.getEndDate()));
	}

	public int getType() {
		return type;
	}

	public String getPeriod() {
		return period;
	}

	@Override
	public String toString() {
		return "FavoriteAnnonceLine [type=" + type + ", period=" + period + "]";
	}

}
