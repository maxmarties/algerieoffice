package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;

public class ChatbotLine implements Serializable {
	private static final long serialVersionUID = -3774830930304422675L;
	
	private final String id;
	private final String message;
	private final String postedDate;
	private final String consultedBy;
	private final int domaine;
	private final boolean discute;
	private final boolean consulted;
	
	public ChatbotLine(final Chatbot chatbot, final String autor) {
		this.id = chatbot.getId().toString();
		this.message = chatbot.getDiscute() ? chatbot.getEmail() : chatbot.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(chatbot.getPostedDate());
		this.consultedBy = !StringUtils.isEmpty(autor) ? autor : "-";
		this.domaine = chatbot.getDomaine();
		this.discute = chatbot.getDiscute();
		this.consulted = chatbot.isConsulted();
	}

	public String getId() {
		return id;
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

	@Override
	public String toString() {
		return "ChatbotLine [id=" + id + ", message=" + message + ", postedDate=" + postedDate + ", consultedBy="
				+ consultedBy + ", domaine=" + domaine + ", discute=" + discute + ", consulted=" + consulted + "]";
	}

}
