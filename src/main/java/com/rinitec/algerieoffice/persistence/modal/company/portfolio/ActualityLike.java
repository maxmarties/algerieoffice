package com.rinitec.algerieoffice.persistence.modal.company.portfolio;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "actualities_like")
public class ActualityLike implements Serializable {
	private static final long serialVersionUID = 6496317861847681423L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "like_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID actualityId;
	
	public ActualityLike() {
	}
	
	public ActualityLike(final Long userId, final UUID actualityId) {
		this.userId = userId;
		this.actualityId = actualityId;
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

	public UUID getActualityId() {
		return actualityId;
	}

	public void setActualityId(UUID actualityId) {
		this.actualityId = actualityId;
	}

	@Override
	public String toString() {
		return "ActualityLike [id=" + id + ", userId=" + userId + ", actualityId=" + actualityId + "]";
	}

}
