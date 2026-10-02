package com.rinitec.algerieoffice.web.modal.admins.premium;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.companymaps.DocumentOrder;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmPremiumOrderLine implements Serializable {
	private static final long serialVersionUID = -845003866678767132L;
	
	private final String id;
	private final Long companyId;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String companyURL;
	private final int pack;
	private final int monthly;
	private final String orderDate;
	private final String fileUrl;
	private final boolean consulted;
	
	public AdmPremiumOrderLine(final DocumentOrder documentOrder, final Company company, final String url) {
		final List<Activity> activities = new ArrayList<Activity>(company.getActivities());
		this.id = documentOrder.getId().toString();
		this.companyId = company.getId();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() 
				? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = activities.get(0).getCode();
		this.companyURL = !StringUtils.isEmpty(url) ? ConstraintesURL.getCompanyExplorerURL(url) : null;
		this.pack = documentOrder.getPack();
		this.monthly = documentOrder.getMonthly() != null ? documentOrder.getMonthly() : 0;
		this.orderDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(documentOrder.getOrderDate());
		this.fileUrl = ConstraintesURL.URL_ENVELOPES + "?fileId=".concat(documentOrder.getFileUUID().toString());
		this.consulted = documentOrder.getConsulted();
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

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getActivity() {
		return activity;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public int getPack() {
		return pack;
	}

	public int getMonthly() {
		return monthly;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public boolean isConsulted() {
		return consulted;
	}
	
	public int parseEmails() {
		return ConstraintesForm.BUDGET_EMAILS[this.pack - 1];
	}
	
	public int parseSendings() {
		return ConstraintesForm.BUDGET_SENDING[this.pack - 1];
	}
	
	public String parseAmount() {
		return ConstraintesForm.BUDGET_FORMULE[this.pack - 1];
	}

	@Override
	public String toString() {
		return "AdmPremiumOrderLine [id=" + id + ", companyId=" + companyId + ", tradename=" + tradename
				+ ", urlAvatar=" + urlAvatar + ", activity=" + activity + ", companyURL=" + companyURL + ", pack="
				+ pack + ", monthly=" + monthly + ", orderDate=" + orderDate + ", fileUrl=" + fileUrl + ", consulted="
				+ consulted + "]";
	}

}
