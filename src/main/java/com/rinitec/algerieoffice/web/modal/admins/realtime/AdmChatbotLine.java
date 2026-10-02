package com.rinitec.algerieoffice.web.modal.admins.realtime;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;

public class AdmChatbotLine implements Serializable {
	private static final long serialVersionUID = -5863567366829449128L;
	
	private final String id;
	private final String tradename;
	private final String email;
	private final String message;
	private final String postedDate;
	private final String consultedBy;
	private final int domaine;
	private final boolean discute;
	private final boolean consulted;
	private final Boolean account;
	private final String key;
	
	public AdmChatbotLine(final Chatbot chatbot, final String tradename, final String autor) {
		this.id = chatbot.getId().toString();
		this.tradename = !StringUtils.isEmpty(tradename) ? tradename : "--";
		this.email = !StringUtils.isEmpty(chatbot.getEmail()) ? chatbot.getEmail() : "-";
		this.message = !StringUtils.isEmpty(chatbot.getMessage()) ? chatbot.getMessage() : "-";
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(chatbot.getPostedDate());
		this.consultedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.domaine = chatbot.getDomaine();
		this.discute = chatbot.getDiscute();
		this.consulted = chatbot.isConsulted();
		this.account = chatbot.getAccount();
		this.key = chatbot.getCompanyId() == null ? "home" : "explorer";
	}

	public String getId() {
		return id;
	}

	public String getTradename() {
		return tradename;
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

	public String getConsultedBy() {
		return consultedBy;
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

	public Boolean getAccount() {
		return account;
	}
	
	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "AdmChatbotLine [id=" + id + ", tradename=" + tradename + ", email=" + email + ", message=" + message
				+ ", postedDate=" + postedDate + ", consultedBy=" + consultedBy + ", domaine=" + domaine + ", discute="
				+ discute + ", consulted=" + consulted + ", account=" + account + ", key=" + key + "]";
	}

}
