package com.rinitec.algerieoffice.web.modal.company.communication;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Chatbot;

public class ChatbotDetail implements Serializable {
	private static final long serialVersionUID = 3619735177082307852L;
	
	private final int domaine;
	private final int discute;
	private final String message;
	private final String postedDate;
	
	public ChatbotDetail(final Chatbot chatbot) {
		this.domaine = chatbot.getDomaine();
		this.discute = chatbot.getDiscute() ? 1 : 2;
		this.message = chatbot.getDiscute() ? chatbot.getEmail() : chatbot.getMessage();
		this.postedDate = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm").print(chatbot.getPostedDate());
	}

	public int getDomaine() {
		return domaine;
	}

	public int getDiscute() {
		return discute;
	}

	public String getMessage() {
		return message;
	}

	public String getPostedDate() {
		return postedDate;
	}

	@Override
	public String toString() {
		return "ChatbotDetail [domaine=" + domaine + ", discute=" + discute + ", message=" + message + ", postedDate="
				+ postedDate + "]";
	}

}
