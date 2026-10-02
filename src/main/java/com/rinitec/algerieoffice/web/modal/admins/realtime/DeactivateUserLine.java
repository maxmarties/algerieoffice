package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;
import com.rinitec.algerieoffice.persistence.modal.users.User;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class DeactivateUserLine implements Serializable {
	private static final long serialVersionUID = -1799314588424468815L;
	
	private final String id;
	private final String username;
	private final String urlAvatar;
	private final String observation;
	private final String deactivateDate;
	private final int reason;
	private final boolean consulted;
	
	public DeactivateUserLine(final Deactivate deactivate, final User user) {
		this.id = deactivate.getId().toString();
		this.username = user.getDisplayName();
		this.urlAvatar = user.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + user.getId() + "&type=" + AvatarType.account + "&width=36&height=36"
				: "/static/picts/avatars/account_mini-min.jpg";
		this.observation = !StringUtils.isEmpty(deactivate.getObservation()) ? deactivate.getObservation() : "--";
		this.deactivateDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(deactivate.getDeactivateDate());
		this.reason = deactivate.getReason();
		this.consulted = deactivate.isConsulted();
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getObservation() {
		return observation;
	}

	public String getDeactivateDate() {
		return deactivateDate;
	}

	public int getReason() {
		return reason;
	}

	public boolean isConsulted() {
		return consulted;
	}

	@Override
	public String toString() {
		return "DeactivateUserLine [id=" + id + ", username=" + username + ", urlAvatar=" + urlAvatar + ", observation="
				+ observation + ", deactivateDate=" + deactivateDate + ", reason=" + reason + ", consulted=" + consulted + "]";
	}

}
