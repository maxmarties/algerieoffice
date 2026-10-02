package com.rinitec.algerieoffice.persistence.modal.inbox;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

@Entity
@Table(name = "inboxs")
public class Inbox implements Serializable {
	private static final long serialVersionUID = -1812286642216511859L;

	@Id
	@Column(nullable = false, updatable = false)
	private Long userId;
	
	@Max(99)
	@Column(nullable = false)
	private Integer countNotification;
	
	@Max(99)
	@Column(nullable = false)
	private Integer countMessage;
	
	public Inbox() {
		this.countNotification = this.countMessage = 0;
	}
	
	public Inbox(Long userId) {
		this();
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getCountNotification() {
		return countNotification;
	}

	public void setCountNotification(Integer countNotification) {
		this.countNotification = countNotification;
	}

	public Integer getCountMessage() {
		return countMessage;
	}

	public void setCountMessage(Integer countMessage) {
		this.countMessage = countMessage;
	}

	@Override
	public String toString() {
		return "Inbox [userId=" + userId + ", countNotification=" + countNotification + ", countMessage=" + countMessage + "]";
	}
	
}
