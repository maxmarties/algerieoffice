package com.rinitec.algerieoffice.web.form.company.manage;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class StickyForm implements Serializable {
	private static final long serialVersionUID = -2443156733425193387L;
	
	@NotNull
	private Long id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String title;
	
	private String description;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_FIELD, message = "{message.input.lenght}")
	private String label;
	
	private boolean target;
	
	private String urlExtern;
	
	public StickyForm() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean isTarget() {
		return target;
	}
	
	public void setTarget(boolean target) {
		this.target = target;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	@Override
	public String toString() {
		return "StickyForm [id=" + id + ", title=" + title + ", description=" + description + ", label=" + label
				+ ", target=" + target + ", urlExtern=" + urlExtern + "]";
	}

}
