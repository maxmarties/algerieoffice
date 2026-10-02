package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;
import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class AdmChatbotDetail implements Serializable {
	private static final long serialVersionUID = 3400511355334874402L;
	
	private final String tradename;
	private final String urlAvatar;
	private final String email;
	private final String message;
	private final String postedDate;
	private final Boolean account;
	private final int domaine;
	private final int discute;
	private final String key;
	
	public AdmChatbotDetail(final Chatbot chatbot, final Company company) {
		if(company != null) {
			this.tradename = company.getTradename();
			this.urlAvatar = company.getHasAvatar() ? ConstraintesURL.URL_AVATARS + "?postedId=" + company.getId() + "&type=" + AvatarType.company + "&width=42&height=42"
					: "/static/picts/avatars/company_mini-min.jpg";
			this.key = "explorer";
		} else {
			this.tradename = null;
			this.urlAvatar = "/static/icons/icon-black-min.jpg";
			this.key = "home";
		}
		this.email = !StringUtils.isEmpty(chatbot.getEmail()) ? chatbot.getEmail() : null;
		this.message = !StringUtils.isEmpty(chatbot.getMessage()) ? chatbot.getMessage() : null;
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(chatbot.getPostedDate());
		this.account = chatbot.getAccount();
		this.domaine = chatbot.getDomaine();
		this.discute = chatbot.getDiscute() ? 1 : 2;
	}

	public String getTradename() {
		return tradename;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public String getEmail() {
		return email;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public Boolean getAccount() {
		return account;
	}

	public int getDomaine() {
		return domaine;
	}

	public int getDiscute() {
		return discute;
	}
	
	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "AdmChatbotDetail [tradename=" + tradename + ", urlAvatar=" + urlAvatar + ", email=" + email
				+ ", message=" + message + ", postedDate=" + postedDate + ", account=" + account + ", domaine="
				+ domaine + ", discute=" + discute + ", key=" + key + "]";
	}

}
