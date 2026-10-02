package com.rinitec.algerieoffice.web.modal.admins.ads;

import java.io.Serializable;
import java.util.UUID;

public class AnnonceNewsletter implements Serializable {
	private static final long serialVersionUID = 5549143826918779937L;
	
	private final String uuid;
	private final String title;
	private final int type;
	
	public AnnonceNewsletter(final UUID id, final String title, final Integer type) {
		this.uuid = id.toString();
		this.title = title;
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AnnonceNewsletter [uuid=" + uuid + ", title=" + title + ", type=" + type + "]";
	}

}
