package com.rinitec.algerieoffice.web.modal.inbox;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.inbox.Support;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class SupportSheet implements Serializable {
	private static final long serialVersionUID = 3119313543605406586L;
	
	private final Long adminId;
	private final String adminName;
	private final String message;
	private final String time;
	private final boolean screenshot;
	
	public SupportSheet(final Support support) {
		this.adminId = support.getAdminId();
		this.adminName = null;
		this.message = support.getScreenUUID() == null ? support.getMessage() : ConstraintesURL.URL_SCREENSHOT + "?id=" + support.getScreenUUID().toString();
		this.time = DateTimeFormat.forPattern(ParseUtil.parseFormatDateMessage(support.getPostedDate())).print(support.getPostedDate());
		this.screenshot = support.getScreenUUID() != null;
	}
	
	public SupportSheet(final Support support, final String adminName) {
		this.adminId = support.getAdminId();
		this.adminName = adminName;
		this.message = support.getScreenUUID() == null ? support.getMessage() : ConstraintesURL.URL_SCREENSHOT + "?id=" + support.getScreenUUID().toString();
		this.time = DateTimeFormat.forPattern(ParseUtil.parseFormatDateMessage(support.getPostedDate())).print(support.getPostedDate());
		this.screenshot = support.getScreenUUID() != null;
	}

	public Long getAdminId() {
		return adminId;
	}
	
	public String getAdminName() {
		return adminName;
	}

	public String getMessage() {
		return message;
	}

	public String getTime() {
		return time;
	}

	public boolean isScreenshot() {
		return screenshot;
	}

	@Override
	public String toString() {
		return "SupportSheet [adminId=" + adminId + ", adminName=" + adminName + ", message=" + message + ", time="
				+ time + ", screenshot=" + screenshot + "]";
	}

}
