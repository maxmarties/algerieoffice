package com.rinitec.algerieoffice.persistence.modal.users.alerts;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "alert_posts")
public class AlertPost implements Serializable {
	private static final long serialVersionUID = -8324453217606034364L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "alert_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(nullable = false, length = 90)
	private String name;
	
	@Max(ConstraintesForm.COUNT_SECTOR_ACTIITY)
	@Column(nullable = false)
	private Integer sector;
	
	@Max(ConstraintesForm.COUNT_WILAYA)
	@Column(nullable = false)
	private Integer wilaya;
	
	@Max(6)
	@Column(nullable = false)
	private Integer frequency;
	
	@Column(nullable = false)
	private Integer potential;
	
	@Column(nullable = false)
	private DateTime postedDate;
	
	@Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)
	private DocumentType type;
	
	private boolean enabled;
	
	public AlertPost() {
		this.potential = 0;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSector() {
		return sector;
	}

	public void setSector(Integer sector) {
		this.sector = sector;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getPotential() {
		return potential;
	}

	public void setPotential(Integer potential) {
		this.potential = potential;
	}

	public DateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(DateTime postedDate) {
		this.postedDate = postedDate;
	}

	public DocumentType getType() {
		return type;
	}

	public void setType(DocumentType type) {
		this.type = type;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "AlertPost [id=" + id + ", userId=" + userId + ", name=" + name + ", sector=" + sector + ", wilaya="
				+ wilaya + ", frequency=" + frequency + ", potential=" + potential + ", postedDate=" + postedDate
				+ ", type=" + type + ", enabled=" + enabled + "]";
	}
	
}
