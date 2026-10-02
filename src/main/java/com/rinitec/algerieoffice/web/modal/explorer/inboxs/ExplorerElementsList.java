package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;
import java.util.List;

public class ExplorerElementsList implements Serializable {
	private static final long serialVersionUID = 8681217586898124511L;
	
	private final Long count;
	private final List<?> lines;
	
	public ExplorerElementsList(final Long count, final List<?> lines) {
		this.count = count;
		this.lines = lines;
	}
	
	public Long getCount() {
		return count;
	}
	
	public List<?> getLines() {
		return lines;
	}

	@Override
	public String toString() {
		return "ExplorerElementsList [count=" + count + ", lines=" + lines + "]";
	}

}
