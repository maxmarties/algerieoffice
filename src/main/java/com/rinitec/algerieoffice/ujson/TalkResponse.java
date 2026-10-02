package com.rinitec.algerieoffice.ujson;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.inbox.Talk;

public class TalkResponse implements Serializable {
	private static final long serialVersionUID = 1776351672284943638L;
	
	private final int type;
	private final String link;
	private final String date;
	
	public TalkResponse(final Talk talk) {
		this.type = talk.getType();
		this.link = "/inbox/talk/href?uuid=".concat(talk.getId().toString());
		this.date =  DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm:ss").print(talk.getTalkedDate());
	}

	public int getType() {
		return type;
	}

	public String getLink() {
		return link;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "TalkResponse [type=" + type + ", link=" + link + ", date=" + date + "]";
	}
	
}
