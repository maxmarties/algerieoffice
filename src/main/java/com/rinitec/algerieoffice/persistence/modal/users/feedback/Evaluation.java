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
@Table(name = "evaluations")
public class Evaluation implements Serializable {
	private static final long serialVersionUID = -1297093828588652753L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "evaluation_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean liked;
	
	@Max(10)
	@Column(nullable = false)
	private Integer note;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	public Evaluation() {
	}
	
	public Evaluation(final Long userId, final Long companyId) {
		this.userId = userId;
		this.companyId = companyId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	@Override
	public String toString() {
		return "Evaluation [id=" + id + ", userId=" + userId + ", companyId=" + companyId + ", liked=" + liked
				+ ", note=" + note + ", postedDate=" + postedDate + "]";
	}

}
