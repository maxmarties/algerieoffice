package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.WorkDetail;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerInboxWork implements Serializable {
	private static final long serialVersionUID = 2310497812519032414L;
	
	private final String id;
	private final String title;
	private final String urlExtern;
	private final String photoURL;
	private final String detail;
	private final DateTime workDate;
	private final String modifiedDate;
	private final String partner;
	
	public ExplorerInboxWork(final Work work, final WorkDetail workDetail, final String partner) {
		this.id = work.getId().toString();
		this.title = work.getTitle();
		this.urlExtern = workDetail.getUrlExtern();
		this.detail = new String(workDetail.getDetail());
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(workDetail.getPhotoUUID().toString());
		this.workDate = work.getWorkDate();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(work.getModifiedDate());
		this.partner = !StringUtils.isEmpty(partner) ? partner : "-";
	}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getDetail() {
		return detail;
	}

	public DateTime getWorkDate() {
		return workDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getPartner() {
		return partner;
	}

	@Override
	public String toString() {
		return "ExplorerInboxWork [id=" + id + ", title=" + title + ", urlExtern=" + urlExtern + ", photoURL="
				+ photoURL + ", detail=" + detail + ", workDate=" + workDate + ", modifiedDate=" + modifiedDate
				+ ", partner=" + partner + "]";
	}

}
