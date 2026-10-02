package com.rinitec.algerieoffice.web.modal.user.favorite;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.users.favorite.FavoriteDocument;

public class FavoriteEmployeLine extends FavoriteDocumentLine {
	private static final long serialVersionUID = 3712922755219468896L;
	
	private final Integer contract;
	private final Integer domaine;
	private final String expiredDate;
	
	public FavoriteEmployeLine(final FavoriteDocument favoriteDocument, final Employe employe, final Integer domaine, final String tradename, 
			final String companyURL) {
		super(favoriteDocument, employe, tradename, companyURL);
		this.contract = employe.getContract();
		this.domaine = domaine;
		this.expiredDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(employe.getExpiredDate());
	}

	public Integer getContract() {
		return contract;
	}

	public Integer getDomaine() {
		return domaine;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	@Override
	public String toString() {
		return "FavoriteEmployeLine [contract=" + contract + ", domaine=" + domaine + ", expiredDate=" + expiredDate + "]";
	}
	
}
