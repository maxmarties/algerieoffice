package com.rinitec.algerieoffice.persistence.modal.medias;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.enums.BannerType;

@Entity
@Table(name = "banners")
public class Banner implements Serializable {
	private static final long serialVersionUID = 6698014860927383664L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "banner_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false, length = 30)
	private String filename;
	
	@Column(nullable = false, length = 30)
	private String contentType;
	
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private BannerType bannerType;
	
	public Banner() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public BannerType getBannerType() {
		return bannerType;
	}

	public void setBannerType(BannerType bannerType) {
		this.bannerType = bannerType;
	}

	@Override
	public String toString() {
		return "Banner [id=" + id + ", filename=" + filename + ", contentType=" + contentType + ", bannerType="
				+ bannerType + "]";
	}

}
