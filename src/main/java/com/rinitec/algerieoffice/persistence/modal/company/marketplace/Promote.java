package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

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
@Table(name = "promotes")
public class Promote implements Serializable {
	private static final long serialVersionUID = -8816113642564993625L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "promote_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 60)
	private String title;
	
	@Column(nullable = false, length = 250)
	private String description;
	
	@Max(3)
	@Column(nullable = false)
	private Integer label;
	
	@Column(nullable = true, length = 250)
	private String url;
	
	@Column(nullable = false)
	private Boolean hasPageonly;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@Column(nullable = false)
	private Long autorId;
	
	private boolean enabled;
	
	@Column(nullable = false)
	private Integer viewCount;
	
	@Column(nullable = false)
	private Integer clickCount;
	
	@Column(nullable = false)
	private Integer creditCount;
	
	@Column(nullable = false)
	private Boolean hasTrashed;
	
	public Promote() {
		this.enabled = this.hasTrashed = false;
		this.viewCount = this.clickCount = this.creditCount = 0;
	}
	
	public Promote(final Long companyId) {
		this();
		this.companyId = companyId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Boolean getHasPageonly() {
		return hasPageonly;
	}
	
	public void setHasPageonly(Boolean hasPageonly) {
		this.hasPageonly = hasPageonly;
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public Long getAutorId() {
		return autorId;
	}

	public void setAutorId(Long autorId) {
		this.autorId = autorId;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public Boolean getHasTrashed() {
		return hasTrashed;
	}

	public void setHasTrashed(Boolean hasTrashed) {
		this.hasTrashed = hasTrashed;
	}
	
	public int parseCreditCount() {
		return creditCount - viewCount;
	}

	@Override
	public String toString() {
		return "Promote [id=" + id + ", companyId=" + companyId + ", title=" + title + ", description=" + description
				+ ", label=" + label + ", url=" + url + ", hasPageonly=" + hasPageonly + ", photoUUID=" + photoUUID
				+ ", autorId=" + autorId + ", enabled=" + enabled + ", viewCount=" + viewCount + ", clickCount="
				+ clickCount + ", creditCount=" + creditCount + ", hasTrashed=" + hasTrashed + "]";
	}

}
