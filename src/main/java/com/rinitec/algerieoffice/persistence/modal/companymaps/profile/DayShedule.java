package com.rinitec.algerieoffice.persistence.modal.companymaps.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;

@Entity
@Table(name = "dayshedule")
public class DayShedule implements Serializable {
	private static final long serialVersionUID = 592747025284048260L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Max(7)
	@Column(nullable = false, updatable = false)
	private Integer indexday;
	
	@Column(nullable = true)
	private Boolean stateday;
	
	@Min(8)
	@Max(24)
	@Column(nullable = true)
	private Integer timeone;
	
	@Min(8)
	@Max(24)
	@Column(nullable = true)
	private Integer timetho;
	
	@Min(8)
	@Max(24)
	@Column(nullable = true)
	private Integer timetree;
	
	@Min(8)
	@Max(24)
	@Column(nullable = true)
	private Integer timefour;
	
	@ManyToOne
	@JoinColumn(name = "companyshedule_id", referencedColumnName = "company_id", nullable = false)
	private CompanyShedule companyshedule;
	
	public DayShedule() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIndexday() {
		return indexday;
	}

	public void setIndexday(Integer indexday) {
		this.indexday = indexday;
	}

	public Boolean getStateday() {
		return stateday;
	}

	public void setStateday(Boolean stateday) {
		this.stateday = stateday;
	}

	public Integer getTimeone() {
		return timeone;
	}

	public void setTimeone(Integer timeone) {
		this.timeone = timeone;
	}

	public Integer getTimetho() {
		return timetho;
	}

	public void setTimetho(Integer timetho) {
		this.timetho = timetho;
	}

	public Integer getTimetree() {
		return timetree;
	}

	public void setTimetree(Integer timetree) {
		this.timetree = timetree;
	}

	public Integer getTimefour() {
		return timefour;
	}

	public void setTimefour(Integer timefour) {
		this.timefour = timefour;
	}

	public CompanyShedule getCompanyshedule() {
		return companyshedule;
	}

	public void setCompanyshedule(CompanyShedule companyshedule) {
		this.companyshedule = companyshedule;
	}
	
	public Integer getTimeIndex(int index) {
		switch(index) {
		case 0: return timeone;
		case 1: return timetho;
		case 2: return timetree;
		case 3: return timefour;
		}
		return null;
	}

	@Override
	public String toString() {
		return "DayShedule [id=" + id + ", indexday=" + indexday + ", stateday=" + stateday + ", timeone=" + timeone
				+ ", timetho=" + timetho + ", timetree=" + timetree + ", timefour=" + timefour + ", companyshedule=" 
				+ companyshedule + "]";
	}
	
}
