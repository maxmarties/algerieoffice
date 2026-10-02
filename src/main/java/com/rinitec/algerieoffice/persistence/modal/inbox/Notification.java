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
@Table(name = "notifications")
public class Notification implements Serializable {
	private static final long serialVersionUID = -106771423296944217L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false)
	private Boolean hasIcon;
	
	@Column(nullable = false, length = 250)
	private String iconimage;
	
	@Column(nullable = true, length = 61)
	private String notifiedname;
	
	@Column(nullable = false, length = 40)
	private String message;
	
	@Column(nullable = true)
	private String link;
	
	@Column(nullable = false)
	private DateTime notifiedDate;
	
	@Column(nullable = false, length = 30)
	private String cmsms;
	
	@Column(nullable = false)
	private Boolean consulted;
	
	public Notification() {
		this.consulted = false;
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

	public Boolean getHasIcon() {
		return hasIcon;
	}

	public void setHasIcon(Boolean hasIcon) {
		this.hasIcon = hasIcon;
	}

	public String getIconimage() {
		return iconimage;
	}

	public void setIconimage(String iconimage) {
		this.iconimage = iconimage;
	}

	public String getNotifiedname() {
		return notifiedname;
	}

	public void setNotifiedname(String notifiedname) {
		this.notifiedname = notifiedname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public DateTime getNotifiedDate() {
		return notifiedDate;
	}

	public void setNotifiedDate(DateTime notifiedDate) {
		this.notifiedDate = notifiedDate;
	}
	
	public String getCmsms() {
		return cmsms;
	}
	
	public void setCmsms(String cmsms) {
		this.cmsms = cmsms;
	}

	public Boolean getConsulted() {
		return consulted;
	}

	public void setConsulted(Boolean consulted) {
		this.consulted = consulted;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", userId=" + userId + ", hasIcon=" + hasIcon + ", iconimage=" + iconimage
				+ ", notifiedname=" + notifiedname + ", message=" + message + ", link=" + link + ", notifiedDate="
				+ notifiedDate + ", cmsms=" + cmsms + ", consulted=" + consulted + "]";
	}
	
}
