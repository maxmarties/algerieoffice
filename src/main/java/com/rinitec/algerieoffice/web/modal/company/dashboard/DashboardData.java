package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

public class DashboardData implements Serializable {
	private static final long serialVersionUID = 8953681439704564448L;
	
	private final Long countAll;
	private final Long countPublished;
	
	public DashboardData(final Long countAll, final Long countPublished) {
		this.countAll = countAll;
		this.countPublished = countPublished;
	}

	public Long getCountAll() {
		return countAll;
	}

	public Long getCountPublished() {
		return countPublished;
	}
	
	public int parsePesrsentPublished() {
		return countAll == 0L ? 0 : (int) ((countPublished * 100) / countAll);
	}

	@Override
	public String toString() {
		return "DashboardData [countAll=" + countAll + ", countPublished=" + countPublished + "]";
	}

}
