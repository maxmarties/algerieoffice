package com.rinitec.algerieoffice.persistence.modal.admins.ads;

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

@Entity
@Table(name = "sponsores")
public class Sponsore implements Serializable {
	private static final long serialVersionUID = -1043749560044176296L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "sponsore_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false, length = 250)
	private String url;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID bannerUUID;
	
	@Max(5)
	@Column(nullable = false)
	private Integer type;
	
	@Column(nullable = false)
	private Integer viewCount;
	
	@Column(nullable = false)
	private Integer clickCount;
	
	@Column(nullable = false)
	private Integer creditCount;
	
	@Column(nullable = false)
	private Boolean hasPublished;
	
	public Sponsore() {
		this.viewCount = this.clickCount = 0;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UUID getBannerUUID() {
		return bannerUUID;
	}

	public void setBannerUUID(UUID bannerUUID) {
		this.bannerUUID = bannerUUID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}

	public Boolean getHasPublished() {
		return hasPublished;
	}

	public void setHasPublished(Boolean hasPublished) {
		this.hasPublished = hasPublished;
	}

	@Override
	public String toString() {
		return "Sponsore [id=" + id + ", url=" + url + ", bannerUUID=" + bannerUUID + ", type=" + type + ", viewCount="
				+ viewCount + ", clickCount=" + clickCount + ", creditCount=" + creditCount + ", hasPublished="
				+ hasPublished + "]";
	}
	
}
