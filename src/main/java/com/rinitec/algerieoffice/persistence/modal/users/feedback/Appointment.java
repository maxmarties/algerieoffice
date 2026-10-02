package com.rinitec.algerieoffice.persistence.modal.users.feedback;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "appointments")
public class Appointment implements Serializable {
	private static final long serialVersionUID = -4570441624136105993L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "appointment_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false, length = 150)
	private String motif;
	
	@Max(4)
	@Column(nullable = false)
	private Integer degree;
	
	@Column(nullable = true)
	private DateTime forDate;
	
	@Column(nullable = true)
	private DateTime toDate;
	
	@Max(4)
	@Column(nullable = true)
	private Integer period;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean approuved;
	
	@Column(nullable = true)
	private DateTime appointDate;
	
	@Column(nullable = true)
	private Long approuvedBy;
	
	public Appointment() {
		this.approuved = false;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	public Integer getDegree() {
		return degree;
	}
	
	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public DateTime getForDate() {
		return forDate;
	}

	public void setForDate(DateTime forDate) {
		this.forDate = forDate;
	}

	public DateTime getToDate() {
		return toDate;
	}

	public void setToDate(DateTime toDate) {
		this.toDate = toDate;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}

	public DateTime getAppointDate() {
		return appointDate;
	}

	public void setAppointDate(DateTime appointDate) {
		this.appointDate = appointDate;
	}

	public Long getApprouvedBy() {
		return approuvedBy;
	}

	public void setApprouvedBy(Long approuvedBy) {
		this.approuvedBy = approuvedBy;
	}

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", motif=" + motif
				+ ", degree=" + degree + ", forDate=" + forDate + ", toDate=" + toDate + ", period=" + period
				+ ", postedDate=" + postedDate + ", approuved=" + approuved + ", appointDate=" + appointDate
				+ ", approuvedBy=" + approuvedBy + "]";
	}
	
}
