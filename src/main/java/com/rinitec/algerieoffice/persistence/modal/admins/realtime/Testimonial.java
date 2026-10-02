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
@Table(name = "testimonials")
public class Testimonial implements Serializable {
	private static final long serialVersionUID = -1956125250313961590L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = true)
	private Long userId;
	
	@Max(10)
	@Column(nullable = false)
	private Integer note;
	
	@Column(nullable = false, length = 60)
	private String username;
	
	@Column(nullable = false, length = 60)
	private String function;
	
	@Column(nullable = true, length = 60)
	private String tradename;
	
	@Column(nullable = false, length = 1024)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	private boolean approuved;
	
	public Testimonial() {
		this.approuved = false;
	}
	
	public Testimonial(final Long userId) {
		this();
		this.userId = userId;
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

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
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

	public boolean isApprouved() {
		return approuved;
	}

	public void setApprouved(boolean approuved) {
		this.approuved = approuved;
	}

	@Override
	public String toString() {
		return "Testimonial [id=" + id + ", userId=" + userId + ", note=" + note + ", username=" + username
				+ ", function=" + function + ", tradename=" + tradename + ", message=" + message + ", postedDate="
				+ postedDate + ", approuved=" + approuved + "]";
	}

}
