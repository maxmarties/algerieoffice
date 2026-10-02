package com.rinitec.algerieoffice.web.form.company.overview;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class MainaboutForm implements Serializable {
	private static final long serialVersionUID = -8831654362522662134L;
	
	@NotNull
	private Long id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String name;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String function;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String word;
	
	@ValidChose
	private Integer size;
	
	private boolean hasAvatar;
	private boolean hasFileChanged = false;
	
	private String urlAvatar;
	
	private MultipartFile file;
	
	public MainaboutForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "MainaboutForm [id=" + id + ", name=" + name + ", function=" + function + ", word=" + word + ", size="
				+ size + ", hasAvatar=" + hasAvatar + ", hasFileChanged=" + hasFileChanged + ", urlAvatar=" + urlAvatar + "]";
	}

}
