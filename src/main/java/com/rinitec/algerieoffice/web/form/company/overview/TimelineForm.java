package com.rinitec.algerieoffice.web.form.company.overview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class TimelineForm implements Serializable {
	private static final long serialVersionUID = -5189273642319527650L;

	@NotNull
	private Long id;
	
	private String history;
	
	private List<Long> idents;
	private List<String> linesDate;
	private List<String> titles;
	private List<String> descriptions;
	private List<Long> updated;
	private List<Long> trashed;
	
	private boolean updateTimeline = false;
	
	public TimelineForm() {
		this.idents = new ArrayList<Long>();
		this.linesDate = new ArrayList<String>();
		this.titles = new ArrayList<String>();
		this.descriptions = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public List<Long> getIdents() {
		return idents;
	}

	public void setIdents(List<Long> idents) {
		this.idents = idents;
	}

	public List<String> getLinesDate() {
		return linesDate;
	}

	public void setLinesDate(List<String> linesDate) {
		this.linesDate = linesDate;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	public List<Long> getUpdated() {
		return updated;
	}

	public void setUpdated(List<Long> updated) {
		this.updated = updated;
	}

	public List<Long> getTrashed() {
		return trashed;
	}

	public void setTrashed(List<Long> trashed) {
		this.trashed = trashed;
	}

	public boolean isUpdateTimeline() {
		return updateTimeline;
	}

	public void setUpdateTimeline(boolean updateTimeline) {
		this.updateTimeline = updateTimeline;
	}
	
	public String getYearlinesDate(int index) {
		return linesDate.get(index).split("\\/")[2];
	}

	@Override
	public String toString() {
		return "TimelineForm [id=" + id + ", history=" + history + ", idents=" + idents + ", linesDate=" + linesDate
				+ ", titles=" + titles + ", descriptions=" + descriptions + ", updated=" + updated + ", trashed="
				+ trashed + ", updateTimeline=" + updateTimeline + "]";
	}
	
}
