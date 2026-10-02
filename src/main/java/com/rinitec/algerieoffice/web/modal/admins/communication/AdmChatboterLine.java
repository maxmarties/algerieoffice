package com.rinitec.algerieoffice.web.modal.admins.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.data.Activity;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmChatboterLine implements Serializable {
	private static final long serialVersionUID = -4714488609574941879L;
	
	private final String id;
	private final String tradename;
	private final String urlAvatar;
	private final String activity;
	private final String message;
	private final String postedDate;
	private final int domaine;
	private final boolean discute;
	private final boolean consulted;
	
	public AdmChatboterLine(final Chatbot chatbot, final Company company) {
		this.id = chatbot.getId().toString();
		this.tradename = company.getTradename();
		this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
				: "/static/picts/avatars/company_mini-min.jpg";
		this.activity = ((Activity) company.getActivities().toArray()[0]).getCode();
		this.message = chatbot.getDiscute() ? chatbot.getEmail() : chatbot.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(chatbot.getPostedDate());
		this.domaine = chatbot.getDomaine();
		this.discute = chatbot.getDiscute();
		this.consulted = chatbot.isConsulted();
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

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public int getDomaine() {
		return domaine;
	}

	public boolean isDiscute() {
		return discute;
	}

	public boolean isConsulted() {
		return consulted;
	}

	@Override
	public String toString() {
		return "AdmChatboterLine [id=" + id + ", tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", activity="
				+ activity + ", message=" + message + ", postedDate=" + postedDate + ", domaine=" + domaine
				+ ", discute=" + discute + ", consulted=" + consulted + "]";
	}

}
