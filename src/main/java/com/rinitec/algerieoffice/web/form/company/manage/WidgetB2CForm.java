package com.rinitec.algerieoffice.web.form.company.manage;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.validator.ValidChose;

public class WidgetB2CForm implements Serializable {
	private static final long serialVersionUID = 1925021759170139544L;
	
	@NotNull
	private Long id;
	
	@ValidChose
	private Integer category;
	
	@ValidChose
	private Integer activity;
	
	private boolean enabled;
	private boolean filtred;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	private String urlCover;
	
	private MultipartFile file;
	
	public WidgetB2CForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getActivity() {
		return activity;
	}

	public void setActivity(Integer activity) {
		this.activity = activity;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isFiltred() {
		return filtred;
	}

	public void setFiltred(boolean filtred) {
		this.filtred = filtred;
	}

	public boolean isHasAvatar() {
		return hasAvatar;
	}

	public void setHasAvatar(boolean hasAvatar) {
		this.hasAvatar = hasAvatar;
	}

	public boolean isHasFileChanged() {
		return hasFileChanged;
	}

	public void setHasFileChanged(boolean hasFileChanged) {
		this.hasFileChanged = hasFileChanged;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}
	
	public String getUrlCover() {
		return urlCover;
	}
	
	public void setUrlCover(String urlCover) {
		this.urlCover = urlCover;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "WidgetB2CForm [id=" + id + ", category=" + category + ", activity=" + activity + ", enabled=" + enabled
				+ ", filtred=" + filtred + ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged
				+ ", urlAvatar=" + urlAvatar + "]";
	}

}
