package com.rinitec.algerieoffice.web.modal.company.prospect;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Annonce;
import com.rinitec.algerieoffice.persistence.modal.company.marketplace.Employe;
import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Event;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.companymaps.GuestDocument;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ProspectLine implements Serializable {
	private static final long serialVersionUID = -9129779720735570880L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String username;
	private final String email;
	private final String postedDate;
	private final String fileUrl;
	private final String consultedBy;
	private final boolean consulted;
	private final boolean hasPublished;
	
	public ProspectLine(final GuestDocument guestDocument, final Post post, final String autor) {
		this.id = guestDocument.getId().toString();
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getPostPreviewURL(post.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.consultedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.consulted  = guestDocument.isConsulted();
		this.hasPublished = post.getHasPublished() && !post.getHasTrashed();
	}
	
	public ProspectLine(final GuestDocument guestDocument, final Annonce annonce, final String autor) {
		this.id = guestDocument.getId().toString();
		this.title = annonce.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePreviewURL(annonce.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.consultedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.consulted  = guestDocument.isConsulted();
		this.hasPublished = annonce.getHasPublished() && !annonce.getHasTrashed();
	}
	
	public ProspectLine(final GuestDocument guestDocument, final Event event, final String autor) {
		this.id = guestDocument.getId().toString();
		this.title = event.getTitle();
		this.identifyURL = ConstraintesURL.getEventPreviewURL(event.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.consultedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.consulted  = guestDocument.isConsulted();
		this.hasPublished = event.getHasPublished();
	}
	
	public ProspectLine(final GuestDocument guestDocument, final Employe employe, final String autor) {
		this.id = guestDocument.getId().toString();
		this.title = employe.getTitle();
		this.identifyURL = ConstraintesURL.getEmployePreviewURL(employe.getIdentify());
		this.username = guestDocument.getUsername();
		this.email = guestDocument.getEmail();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(guestDocument.getPostedDate());
		this.fileUrl = guestDocument.getFileUUID() != null 
				? ConstraintesURL.URL_DOCUMENT_ADSBUB + "?documentId=".concat(guestDocument.getFileUUID().toString())
						.concat("&companyId=").concat(guestDocument.getCompanyId().toString()) : null;
		this.consultedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.consulted  = guestDocument.isConsulted();
		this.hasPublished = employe.getHasPublished() && !employe.getHasTrashed();
	}

	public String getId() {
		return id;
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

	public String getPostedDate() {
		return postedDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public String getConsultedBy() {
		return consultedBy;
	}

	public boolean isConsulted() {
		return consulted;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "ProspectLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", username=" + username
				+ ", email=" + email + ", postedDate=" + postedDate + ", fileUrl=" + fileUrl + ", consultedBy="
				+ consultedBy + ", consulted=" + consulted + ", hasPublished=" + hasPublished + "]";
	}

}
