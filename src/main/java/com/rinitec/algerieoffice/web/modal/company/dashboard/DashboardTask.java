package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;

public class DashboardTask implements Serializable {
	private static final long serialVersionUID = -1845118761390130558L;
	
	private final boolean[][] tasks;
	
	public DashboardTask(final boolean[][] tasks) {
		this.tasks = tasks;
	}
	
	public boolean[][] getTasks() {
		return tasks;
	}

	@Override
	public String toString() {
		return "DashboardTask [tasks=" + tasks + "]";
	}

}
