package com.rinitec.algerieoffice.web.modal.company.help;

import java.io.Serializable;

public class BegginerTask implements Serializable {
	private static final long serialVersionUID = -2333540119628506212L;
	
	private final boolean[] tasks;
	
	public BegginerTask(final boolean[] tasks) {
		this.tasks = tasks;
	}

	public boolean[] getTasks() {
		return tasks;
	}
	
	public int countFinished() {
		int count = 0;
		for (final boolean task : tasks) {
			if(task) count++;
		}
		return count + 1;
	}
	
	public int countReleased() {
		int count = 0;
		for (final boolean task : tasks) {
			if(!task) count++;
		}
		return count;
	}
	
	public int countTasks() {
		return tasks.length + 1;
	}
	
	public int persentFinished() {
		final int finished = countFinished();
		return (finished * 100) / (tasks.length + 1);
	}

	@Override
	public String toString() {
		return "BegginerTask [tasks=" + tasks + "]";
	}

}
