package com.rinitec.algerieoffice.web.modal.publics.marketplace;

import java.io.Serializable;
import java.util.List;

public class DocumentsSimultudeList implements Serializable {
	private static final long serialVersionUID = -6120426085300841995L;
	
	private final List<?> proxis;
	private final List<?> sources;
	
	public DocumentsSimultudeList(final List<?> proxis, final List<?> sources) {
		this.proxis = proxis;
		this.sources = sources;
	}

	public List<?> getProxis() {
		return proxis;
	}

	public List<?> getSources() {
		return sources;
	}
	
	public boolean hasPresent() {
		return !proxis.isEmpty() || !sources.isEmpty();
	}

	@Override
	public String toString() {
		return "DocumentsSimultudeList [proxis=" + proxis + ", sources=" + sources + "]";
	}

}
