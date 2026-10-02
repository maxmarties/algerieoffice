package com.rinitec.algerieoffice.web.modal.company.prospect;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ProspectDetail implements Serializable {
	private static final long serialVersionUID = 6904889378366228331L;
	
	private final String title;
	private final String identifyURL;
	private final String username;
	private final String email;
	private final String phone;
	private final String message;
	private final String postedDate;
	private final String fileUrl;
	private final boolean hasPublished;
	
	public ProspectDetail(final GuestDocument guestDocument, final Post post) {
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getPostPreviewURL(post.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.phone = !StringUtils.isEmpty(guestDocument.getPhone()) ? ParseUtil.getFormattedPhone(guestDocument.getPhone()) : "-";
		this.message = guestDocument.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.hasPublished = post.getHasPublished() && !post.getHasTrashed();
	}
	
	public ProspectDetail(final GuestDocument guestDocument, final Annonce annonce) {
		this.title = annonce.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePreviewURL(annonce.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.phone = !StringUtils.isEmpty(guestDocument.getPhone()) ? ParseUtil.getFormattedPhone(guestDocument.getPhone()) : "-";
		this.message = guestDocument.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.hasPublished = annonce.getHasPublished() && !annonce.getHasTrashed();
	}
	
	public ProspectDetail(final GuestDocument guestDocument, final Event event) {
		this.title = event.getTitle();
		this.identifyURL = ConstraintesURL.getEventPreviewURL(event.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.phone = !StringUtils.isEmpty(guestDocument.getPhone()) ? ParseUtil.getFormattedPhone(guestDocument.getPhone()) : "-";
		this.message = guestDocument.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.hasPublished = event.getHasPublished();
	}
	
	public ProspectDetail(final GuestDocument guestDocument, final Employe employe) {
		this.title = employe.getTitle();
		this.identifyURL = ConstraintesURL.getEmployePreviewURL(employe.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.phone = !StringUtils.isEmpty(guestDocument.getPhone()) ? ParseUtil.getFormattedPhone(guestDocument.getPhone()) : "-";
		this.message = guestDocument.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.hasPublished = employe.getHasPublished() && !employe.getHasTrashed();
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "ProspectDetail [title=" + title + ", identifyURL=" + identifyURL + ", username=" + username + ", email="
				+ email + ", phone=" + phone + ", message=" + message + ", postedDate=" + postedDate + ", fileUrl="
				+ fileUrl + ", hasPublished=" + hasPublished + "]";
	}

}
