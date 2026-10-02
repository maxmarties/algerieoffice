package com.rinitec.algerieoffice.web.form.company.overview;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class MainheaderForm implements Serializable {
	private static final long serialVersionUID = 7478410535360100171L;

	@NotNull
	private Long id;
	
	private boolean hasLogo;
	private boolean hasLogoChanged = false;
	private String urlLogo;
	
	private boolean hasCover;
	private boolean hasCoverChanged = false;
	private String urlCover;
	
	@NotNull(message = "{message.input.required}")
	private String canva;
	
	@NotNull(message = "{message.input.required}")
	private String canvaColor;
	
	@NotNull(message = "{message.input.required}")
	private String textColor;
	
	private String address;
	private Integer wilaya;
	private String activity;
	private String language;
	private String modifiedDate;
	
	private MultipartFile[] files;
	
	public MainheaderForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isHasLogo() {
		return hasLogo;
	}

	public void setHasLogo(boolean hasLogo) {
		this.hasLogo = hasLogo;
	}

	public boolean isHasLogoChanged() {
		return hasLogoChanged;
	}

	public void setHasLogoChanged(boolean hasLogoChanged) {
		this.hasLogoChanged = hasLogoChanged;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public void setUrlLogo(String urlLogo) {
		this.urlLogo = urlLogo;
	}

	public boolean isHasCover() {
		return hasCover;
	}

	public void setHasCover(boolean hasCover) {
		this.hasCover = hasCover;
	}

	public boolean isHasCoverChanged() {
		return hasCoverChanged;
	}

	public void setHasCoverChanged(boolean hasCoverChanged) {
		this.hasCoverChanged = hasCoverChanged;
	}

	public String getUrlCover() {
		return urlCover;
	}

	public void setUrlCover(String urlCover) {
		this.urlCover = urlCover;
	}

	public String getCanva() {
		return canva;
	}

	public void setCanva(String canva) {
		this.canva = canva;
	}

	public String getCanvaColor() {
		return canvaColor;
	}

	public void setCanvaColor(String canvaColor) {
		this.canvaColor = canvaColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getWilaya() {
		return wilaya;
	}
	
	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getModifiedDate() {
		return modifiedDate;
	}
	
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public MultipartFile[] getFiles() {
		return files;
	}
	
	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "MainheaderForm [id=" + id + ", hasLogo=" + hasLogo + ", hasLogoChanged=" + hasLogoChanged
				+ ", hasCover=" + hasCover + ", hasCoverChanged=" + hasCoverChanged + ", canva=" + canva
				+ ", canvaColor=" + canvaColor + ", textColor=" + textColor + "]";
	}

}
