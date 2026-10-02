package com.rinitec.algerieoffice.web.modal.mapsite;

import java.util.UUID;

import org.joda.time.DateTime;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ActualityMapsite extends DocumentMapsite {
	private static final long serialVersionUID = 8182558957512671513L;

	public ActualityMapsite(final UUID actuId, final DateTime modifiedDate) {
		super(ConstraintesURL.getActualiteFavoriteURL(actuId.toString()), modifiedDate);
	}

}
