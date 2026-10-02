package com.rinitec.algerieoffice.mail;

import java.util.List;
import java.util.Map;

public class EmailModel {
	
	private String from;
    private String to;
    private String subject;
    private String message;
    private List<Object> attachments;
    private Map<String, Object> model;
    
    public EmailModel() {
	}
    
    public EmailModel(final String from, final String to, final String subject, final String message) {
    	this.from = from;
    	this.to = to;
    	this.subject = subject;
    	this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Object> attachments) {
		this.attachments = attachments;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "Email [from=" + from + ", to=" + to + ", subject=" + subject + ", message=" + message + ", attachments="
				+ attachments + ", model=" + model + "]";
	}

}
