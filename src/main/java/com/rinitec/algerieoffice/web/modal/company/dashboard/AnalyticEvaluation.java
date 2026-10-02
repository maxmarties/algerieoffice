package com.rinitec.algerieoffice.web.modal.company.dashboard;

import java.io.Serializable;
import java.util.Locale;

public class AnalyticEvaluation implements Serializable {
	private static final long serialVersionUID = 4799122362622376507L;
	
	private final Long countAll;
	private final Long countLiked;
	private final double note;
	private final Long count;
	
	public AnalyticEvaluation(final Long countAll, final Long countLiked, final Double note, final Long count) {
		this.countAll = countAll;
		this.countLiked = countLiked;
		this.note = note == null ? 0 : note;
		this.count = count;
	}

	public Long getCountAll() {
		return countAll;
	}

	public Long getCountLiked() {
		return countLiked;
	}

	public double getNote() {
		return note;
	}
	
	public Long getCount() {
		return count;
	}
	
	public boolean hasPresent() {
		return countAll > 0L;
	}
	
	public int getPesrsentLiked() {
		return (int) ((countLiked * 100) / countAll);
	}
	
	public int getPesrsentNote() {
		return (int) (note * 10);
	}
	
	public String getFormattedNote() {
		return String.format(Locale.FRENCH, "%,.2f", note);
	}
	
	public int getPesrsentCount() {
		return (int) ((countAll * 100) / count);
	}

	@Override
	public String toString() {
		return "AnalyticEvaluation [countAll=" + countAll + ", countLiked=" + countLiked + ", note=" + note + ", count="
				+ count + "]";
	}

}
