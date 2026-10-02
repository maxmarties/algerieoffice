package com.rinitec.algerieoffice.persistence.modal.companymaps.overview;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

@Entity
@Table(name = "timelines")
public class Timeline implements Serializable {
	private static final long serialVersionUID = -3927602939699434833L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = true)
	private DateTime lineDate;
	
	@Column(nullable = false, length = 90)
	private String title;
	
	@Column(nullable = false, length = 512)
	private String description;
	
	public Timeline() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public DateTime getLineDate() {
		return lineDate;
	}

	public void setLineDate(DateTime lineDate) {
		this.lineDate = lineDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Timeline [id=" + id + ", companyId=" + companyId + ", lineDate=" + lineDate + ", title=" + title
				+ ", description=" + description + "]";
	}

}
