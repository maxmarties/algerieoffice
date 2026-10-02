package com.rinitec.algerieoffice.persistence.modal.inbox;

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
@Table(name = "chaters")
public class Chater implements Serializable {
	private static final long serialVersionUID = 7354880751930754221L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "chater_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false, length = 1024)
	private String message;
	
	//TODO MORE VERSION
	@Max(4)
	@Column(nullable = true)
	private Integer type;
	
	@Column(nullable = false)
	private DateTime chaterDate;
	
	public Chater() {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public DateTime getChaterDate() {
		return chaterDate;
	}

	public void setChaterDate(DateTime chaterDate) {
		this.chaterDate = chaterDate;
	}

	@Override
	public String toString() {
		return "Chater [id=" + id + ", userId=" + userId + ", message=" + message + ", type=" + type + ", chaterDate="
				+ chaterDate + "]";
	}
	
}
