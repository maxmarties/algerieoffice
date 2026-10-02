package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.CompanyAddress;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DeactivateCompanyLine implements Serializable {
	private static final long serialVersionUID = -606086822127351332L;
	
	private final String id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String observation;
	private final String deactivateDate;
	private final int wilaya;
	private final int reason;
	private final boolean consulted;
	
	public DeactivateCompanyLine(final Deactivate deactivate, final Company company) {
		this.id = deactivate.getId().toString();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.observation = !StringUtils.isEmpty(deactivate.getObservation()) ? deactivate.getObservation() : "--";
		this.deactivateDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(deactivate.getDeactivateDate());
		this.wilaya = ((CompanyAddress) company.getAddresses().toArray()[0]).getWilaya();
		this.reason = deactivate.getReason();
		this.consulted = deactivate.isConsulted();
	}

	public String getId() {
		return id;
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

	public String getObservation() {
		return observation;
	}

	public String getDeactivateDate() {
		return deactivateDate;
	}

	public int getWilaya() {
		return wilaya;
	}

	public int getReason() {
		return reason;
	}

	public boolean isConsulted() {
		return consulted;
	}

	@Override
	public String toString() {
		return "DeactivateCompanyLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar
				+ ", activity=" + activity + ", observation=" + observation + ", deactivateDate=" + deactivateDate
				+ ", wilaya=" + wilaya + ", reason=" + reason + ", consulted=" + consulted + "]";
	}

}
