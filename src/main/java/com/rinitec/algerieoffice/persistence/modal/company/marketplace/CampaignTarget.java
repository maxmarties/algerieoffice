package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "campaigns_target")
public class CampaignTarget implements Serializable {
	private static final long serialVersionUID = -8412004822785733297L;
	
	@Id
	@Column(name = "campaign_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Max(ConstraintesForm.COUNT_SECTOR_ACTIITY)
	@Column(nullable = true)
	private Integer sector;
	
	@Max(ConstraintesForm.COUNT_WILAYA)
	@Column(nullable = true)
	private Integer wilaya;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Campaign campaign;
	
	public CampaignTarget() {
	}
	
	public CampaignTarget(final Campaign campaign) {
		this.campaign = campaign;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public Campaign getCampaign() {
		return campaign;
	}
	
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	@Override
	public String toString() {
		return "CampaignTarget [id=" + id + ", sector=" + sector + ", wilaya=" + wilaya + ", campaign=" + campaign + "]";
	}

}
