package com.rinitec.algerieoffice.web.form.admins.marketplace;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class AdmCampaignForm implements Serializable {
	private static final long serialVersionUID = 7548258357397273071L;
	
	@NotNull
	private String id;
	
	private String title;
	private int type;
	private int sector;
	private int wilaya;
	
	@NotNull
	private Integer viewCount;
	
	@NotNull
	private Integer clicCount;
	
	@NotNull
	private Integer creditCount;
	
	private boolean enabled;
	
	public AdmCampaignForm() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSector() {
		return sector;
	}

	public void setSector(int sector) {
		this.sector = sector;
	}

	public int getWilaya() {
		return wilaya;
	}

	public void setWilaya(int wilaya) {
		this.wilaya = wilaya;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getClicCount() {
		return clicCount;
	}

	public void setClicCount(Integer clicCount) {
		this.clicCount = clicCount;
	}

	public Integer getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(Integer creditCount) {
		this.creditCount = creditCount;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "AdmCampaignForm [id=" + id + ", title=" + title + ", type=" + type + ", sector=" + sector + ", wilaya="
				+ wilaya + ", viewCount=" + viewCount + ", clicCount=" + clicCount + ", creditCount=" + creditCount
				+ ", enabled=" + enabled + "]";
	}

}
