package com.rinitec.algerieoffice.persistence.modal.admins.realtime;

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
@Table(name = "assists")
public class Assist implements Serializable {
	private static final long serialVersionUID = -2468150601085703713L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Max(3)
	@Column(nullable = true)
	private Integer app;
	
	@Max(3)
	@Column(nullable = true)
	private Integer management;
	
	@Max(3)
	@Column(nullable = true)
	private Integer program;
	
	@Column(nullable = false, length = 60)
	private String object;
	
	@Column(nullable = false, length = 1024)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	public Assist() {
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

	public Integer getApp() {
		return app;
	}

	public void setApp(Integer app) {
		this.app = app;
	}

	public Integer getManagement() {
		return management;
	}

	public void setManagement(Integer management) {
		this.management = management;
	}

	public Integer getProgram() {
		return program;
	}

	public void setProgram(Integer program) {
		this.program = program;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	@Override
	public String toString() {
		return "Assist [id=" + id + ", userId=" + userId + ", app=" + app + ", management=" + management + ", program="
				+ program + ", object=" + object + ", message=" + message + ", postedDate=" + postedDate + "]";
	}

}
