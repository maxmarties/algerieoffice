package com.rinitec.algerieoffice.persistence.modal.inbox;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(name = "messages")
public class Message implements Serializable {
	private static final long serialVersionUID = 2391183792371032127L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "message_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long senderId;
	
	@Column(nullable = false)
	private Long recepientId;
	
	@Column(nullable = false, length = 512)
	private String message;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	@Column(nullable = false)
	private Boolean consulted;
	
	private boolean emojis;
	
	public Message() {
		this.consulted = this.emojis = false;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getRecepientId() {
		return recepientId;
	}

	public void setRecepientId(Long recepientId) {
		this.recepientId = recepientId;
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

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}
	
	public boolean isEmojis() {
		return emojis;
	}
	
	public void setEmojis(boolean emojis) {
		this.emojis = emojis;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", senderId=" + senderId + ", recepientId=" + recepientId + ", message=" + message
				+ ", postedDate=" + postedDate + ", consulted=" + consulted + ", emojis=" + emojis + "]";
	}
	
}
