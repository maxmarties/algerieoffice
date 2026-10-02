package com.rinitec.algerieoffice.web.form.company.newsletter;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.validator.ValidChose;

public class TemplateForm implements Serializable {
	private static final long serialVersionUID = 5590818077132845272L;
	
	@NotNull
	private Long id;
	
	@NotNull(message = "{message.input.required}")
	private String paneColor;
	
	@NotNull(message = "{message.input.required}")
	private String textColor;
	
	private String title;
	
	@ValidChose
	private Integer target;
	
	private String url;
	
	@ValidChose
	private Integer label;
	
	private String description;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private boolean[] params = new boolean[5];
	
	private MultipartFile file;
	
	public TemplateForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaneColor() {
		return paneColor;
	}

	public void setPaneColor(String paneColor) {
		this.paneColor = paneColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getTarget() {
		return target;
	}

	public void setTarget(Integer target) {
		this.target = target;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLabel() {
		return label;
	}

	public void setLabel(Integer label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean[] getParams() {
		return params;
	}

	public void setParams(boolean[] params) {
		this.params = params;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public void initParams() {
		for (int i = 0; i < 5; i++) {
			this.params[i] = true;
		}
	}
	
	public void parseParams(final String params) {
		for (int i = 0; i < 5; i++) {
			this.params[i] = (params.charAt(i) == '1' || i == 2);
		}
	}
	
	public String builderParams() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			builder.append(params[i] || i == 2 ? "1" : "0");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "TemplateForm [id=" + id + ", paneColor=" + paneColor + ", textColor=" + textColor + ", title=" + title
				+ ", target=" + target + ", url=" + url + ", label=" + label + ", description=" + description
				+ ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar=" + urlAvatar
				+ ", params=" + Arrays.toString(params) + "]";
	}

}
