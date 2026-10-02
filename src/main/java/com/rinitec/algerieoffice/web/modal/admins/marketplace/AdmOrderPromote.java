package com.rinitec.algerieoffice.web.modal.admins.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Promote;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmOrderPromote implements Serializable {
	private static final long serialVersionUID = -2477777625475794225L;
	
	private final String promoteId;
	private final String title;
	private final String description;
	private final String url;
	private final String urlAvatar;
	private final Integer pack;
	private final String amount;
	private final String fileUrl;
	
	public AdmOrderPromote(final Promote promote, final DocumentOrder documentOrder) {
		this.promoteId = promote.getId().toString();
		this.title = promote.getTitle();
		this.description = promote.getDescription();
		this.url = promote.getUrl();
		this.urlAvatar = promote.getPhotoUUID() != null ? ConstraintesURL.URL_PHOTOS + "?photoId=".concat(promote.getPhotoUUID().toString()) : null;
		this.pack = ConstraintesForm.ORDERS_CREDIT[documentOrder.getPack() - 1];
		this.amount = ConstraintesForm.ORDERS_FORMULE[documentOrder.getPack() - 1];
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
	}

	public String getPromoteId() {
		return promoteId;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public Integer getPack() {
		return pack;
	}

	public String getAmount() {
		return amount;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	@Override
	public String toString() {
		return "AdmOrderPromote [promoteId=" + promoteId + ", title=" + title + ", description=" + description
				+ ", url=" + url + ", urlAvatar=" + urlAvatar + ", pack=" + pack + ", amount=" + amount + ", fileUrl="
				+ fileUrl + "]";
	}

}
