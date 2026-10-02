package com.rinitec.algerieoffice.web.modal.user.feedback;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.persistence.modal.inbox.Talk;

public class TalkLine implements Serializable {
	private static final long serialVersionUID = -3050162592958272527L;
	
	private final String id;
	private final String link;
	private final String talkedDate;
	private final int type;
	private final boolean consulted;

	public TalkLine(final Talk talk) {
		this.id = talk.getId().toString();
		this.link = talk.getLink();
		this.talkedDate = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm").print(talk.getTalkedDate());
		this.type = talk.getType();
		this.consulted = talk.getConsulted();
	}

	public String getId() {
		return id;
	}

	public String getLink() {
		return link;
	}

	public String getTalkedDate() {
		return talkedDate;
	}

	public int getType() {
		return type;
	}

	public boolean isConsulted() {
		return consulted;
	}

	@Override
	public String toString() {
		return "TalkLine [id=" + id + ", link=" + link + ", talkedDate=" + talkedDate + ", type=" + type
				+ ", consulted=" + consulted + "]";
	}

}
