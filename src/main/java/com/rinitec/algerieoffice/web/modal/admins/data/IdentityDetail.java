package com.rinitec.algerieoffice.web.modal.admins.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class IdentityDetail implements Serializable {
	private static final long serialVersionUID = -2498233543121465860L;
	
	private String createdDate;
	private String requestedDate;
	private String denomination;
	private String tradename;
	private String buildDate;
	private String url;
	private String fileUrl;
	private List<String> activities;
	private List<String> validatesBy;
	private List<Boolean> responses;
	private List<String> historiesDate;
	private List<String> messages;
	
	public IdentityDetail() {
		this.activities = new ArrayList<String>();
		this.validatesBy = new ArrayList<String>();
		this.responses = new ArrayList<Boolean>();
		this.historiesDate = new ArrayList<String>();
		this.messages = new ArrayList<String>();
	}
	
	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}
	
	public String getBuildDate() {
		return buildDate;
	}
	
	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

	public List<String> getValidatesBy() {
		return validatesBy;
	}

	public void setValidatesBy(List<String> validatesBy) {
		this.validatesBy = validatesBy;
	}

	public List<Boolean> getResponses() {
		return responses;
	}

	public void setResponses(List<Boolean> responses) {
		this.responses = responses;
	}

	public List<String> getHistoriesDate() {
		return historiesDate;
	}

	public void setHistoriesDate(List<String> historiesDate) {
		this.historiesDate = historiesDate;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public String getInfoLine(int index) {
		switch(index) {
		case 1: return createdDate;
		case 2: return requestedDate;
		case 3: return denomination;
		case 4: return tradename;
		case 5: return buildDate;
		case 6: return url;
		}
		return "";
	}

	@Override
	public String toString() {
		return "IdentityDetail [createdDate=" + createdDate + ", requestedDate=" + requestedDate + ", denomination="
				+ denomination + ", tradename=" + tradename + ", buildDate=" + buildDate + ", url=" + url + ", fileUrl="
				+ fileUrl + ", activities=" + activities + ", validatesBy=" + validatesBy + ", responses=" + responses
				+ ", historiesDate=" + historiesDate + ", messages=" + messages + "]";
	}

}
