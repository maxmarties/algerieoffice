package com.rinitec.algerieoffice.web.form.company.overview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class MainsliderForm implements Serializable {
	private static final long serialVersionUID = 684111847042659544L;

	@NotNull
	private Long id;
	
	@NotNull
	private Boolean hasAutoplay;
	
	@NotNull
	private Boolean hasHover;
	
	@NotNull
	private Boolean hasNavigation;
	
	@NotNull
	private Boolean hasDots;
	
	@NotNull(message = "{message.input.required}")
	private String speed;
	
	@NotNull(message = "{message.input.required}")
	private String timeout;
	
	@NotNull(message = "{message.input.required}")
	private String animateIn;
	
	@NotNull(message = "{message.input.required}")
	private String animateOut;
	
	@NotNull(message = "{message.input.required}")
	private String animateFade;
	
	@NotNull(message = "{message.input.required}")
	private String fadeColor;
	
	@NotNull(message = "{message.input.required}")
	private String textColor;
	
	private List<Long> idents;
	private List<String> titles;
	private List<String> descriptions;
	private List<String> photosUUID;
	private List<Long> updated;
	private List<Long> trashed;
	
	private MultipartFile[] files;
	
	private boolean updateSlider = false;
	
	public MainsliderForm() {
		this.idents = new ArrayList<Long>();
		this.titles = new ArrayList<String>();
		this.descriptions = new ArrayList<String>();
		this.photosUUID = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Long> getIdents() {
		return idents;
	}

	public void setIdents(List<Long> idents) {
		this.idents = idents;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}

	public List<String> getPhotosUUID() {
		return photosUUID;
	}

	public void setPhotosUUID(List<String> photosUUID) {
		this.photosUUID = photosUUID;
	}

	public List<Long> getUpdated() {
		return updated;
	}

	public void setUpdated(List<Long> updated) {
		this.updated = updated;
	}

	public List<Long> getTrashed() {
		return trashed;
	}

	public void setTrashed(List<Long> trashed) {
		this.trashed = trashed;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public boolean isUpdateSlider() {
		return updateSlider;
	}

	public void setUpdateSlider(boolean updateSlider) {
		this.updateSlider = updateSlider;
	}

	@Override
	public String toString() {
		return "MainsliderForm [id=" + id + ", hasAutoplay=" + hasAutoplay + ", hasHover=" + hasHover
				+ ", hasNavigation=" + hasNavigation + ", hasDots=" + hasDots + ", speed=" + speed + ", timeout="
				+ timeout + ", animateIn=" + animateIn + ", animateOut=" + animateOut + ", animateFade=" + animateFade
				+ ", fadeColor=" + fadeColor + ", textColor=" + textColor + ", idents=" + idents + ", titles=" + titles
				+ ", descriptions=" + descriptions + ", photosUUID=" + photosUUID + ", updated=" + updated
				+ ", trashed=" + trashed + ", updateSlider=" + updateSlider + "]";
	}
	
}
