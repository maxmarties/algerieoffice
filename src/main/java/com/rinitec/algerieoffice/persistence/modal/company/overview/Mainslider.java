package com.rinitec.algerieoffice.persistence.modal.company.overview;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.rinitec.algerieoffice.persistence.modal.companymaps.overview.SliderItem;

@Entity
@Table(name = "mainsliders")
public class Mainslider implements Serializable {
	private static final long serialVersionUID = -8246217929252399805L;

	@Id
	@Column(name = "company_id", nullable = false, updatable = false)
	private Long companyId;
	
	@Column(nullable = false)
	private Boolean hasAutoplay;
	
	@Column(nullable = false)
	private Boolean hasHover;
	
	@Column(nullable = false)
	private Boolean hasNavigation;
	
	@Column(nullable = false)
	private Boolean hasDots;
	
	@Column(nullable = false, length = 3)
	private String speed;
	
	@Column(nullable = false, length = 3)
	private String timeout;
	
	@Column(nullable = false, length = 20)
	private String animateIn;
	
	@Column(nullable = false, length = 20)
	private String animateOut;
	
	@Column(nullable = false, length = 20)
	private String animateFade;
	
	@Column(nullable = false, length = 30)
	private String fadeColor;
	
	@Column(nullable = false, length = 30)
	private String textColor;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "mainslider")
	private Collection<SliderItem> items;
	
	public Mainslider() {
	}
	
	public Mainslider(final Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Boolean getHasAutoplay() {
		return hasAutoplay;
	}

	public void setHasAutoplay(Boolean hasAutoplay) {
		this.hasAutoplay = hasAutoplay;
	}

	public Boolean getHasHover() {
		return hasHover;
	}

	public void setHasHover(Boolean hasHover) {
		this.hasHover = hasHover;
	}

	public Boolean getHasNavigation() {
		return hasNavigation;
	}

	public void setHasNavigation(Boolean hasNavigation) {
		this.hasNavigation = hasNavigation;
	}

	public Boolean getHasDots() {
		return hasDots;
	}

	public void setHasDots(Boolean hasDots) {
		this.hasDots = hasDots;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getAnimateIn() {
		return animateIn;
	}

	public void setAnimateIn(String animateIn) {
		this.animateIn = animateIn;
	}

	public String getAnimateOut() {
		return animateOut;
	}

	public void setAnimateOut(String animateOut) {
		this.animateOut = animateOut;
	}

	public String getAnimateFade() {
		return animateFade;
	}

	public void setAnimateFade(String animateFade) {
		this.animateFade = animateFade;
	}

	public String getFadeColor() {
		return fadeColor;
	}

	public void setFadeColor(String fadeColor) {
		this.fadeColor = fadeColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public Collection<SliderItem> getItems() {
		return items;
	}

	public void setItems(Collection<SliderItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Mainslider [companyId=" + companyId + ", hasAutoplay=" + hasAutoplay + ", hasHover=" + hasHover
				+ ", hasNavigation=" + hasNavigation + ", hasDots=" + hasDots + ", speed=" + speed + ", timeout="
				+ timeout + ", animateIn=" + animateIn + ", animateOut=" + animateOut + ", animateFade=" + animateFade
				+ ", fadeColor=" + fadeColor + ", textColor=" + textColor + ", items=" + items + "]";
	}
	
}
