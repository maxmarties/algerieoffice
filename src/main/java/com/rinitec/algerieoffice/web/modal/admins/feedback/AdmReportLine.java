package com.rinitec.algerieoffice.web.modal.admins.feedback;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.users.feedback.Report;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmReportLine implements Serializable {
	private static final long serialVersionUID = 5348232117302899136L;
	
	private final String id;
	private final Long companyId;
	private final String tradename;
	private final String companyURL;
	private final String urlAvatar;
	private final String activity;
	private final String username;
	private final String identifyURL;
	private final int type;
	private final String reason;
	private final String fileUrl;
	private final String postedDate;
	private final boolean approuved;
	private final boolean locked;
	
	public AdmReportLine(final Report report, final Company company, final String url, final String username) {
		this.id = report.getId().toString();
		this.companyId = company.getId();
		this.tradename = company.getTradename();
		this.companyURL = ConstraintesURL.getCompanyExplorerURL(url);
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=40&height=40"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.username = username;
		this.identifyURL = ConstraintesURL.URL_PROFILES.concat("?id=").concat(report.getUserId().toString());
		this.type = report.getType();
		this.reason = report.getReason();
		this.fileUrl = report.getFileUUID() != null ? ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(report.getFileUUID().toString()) : null;
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(report.getPostedDate());
		this.approuved = report.isApprouved();
		this.locked = company.isLocked();
	}

	public String getId() {
		return id;
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public String getTradename() {
		return tradename;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}
	
	public String getActivity() {
		return activity;
	}

	public String getUsername() {
		return username;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public int getType() {
		return type;
	}

	public String getReason() {
		return reason;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public boolean isApprouved() {
		return approuved;
	}
	
	public boolean isLocked() {
		return locked;
	}

	@Override
	public String toString() {
		return "AdmReportLine [id=" + id + ", companyId=" + companyId + ", tradename=" + tradename + ", companyURL="
				+ companyURL + ", urlAvatar=" + urlAvatar + ", activity=" + activity + ", username=" + username
				+ ", identifyURL=" + identifyURL + ", type=" + type + ", reason=" + reason + ", fileUrl=" + fileUrl
				+ ", postedDate=" + postedDate + ", approuved=" + approuved + ", locked=" + locked + "]";
	}

}
