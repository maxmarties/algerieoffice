package com.rinitec.algerieoffice.web.form.user.alerts;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class AlertPostForm implements Serializable {
	private static final long serialVersionUID = 1059957534983087593L;
	
	private String id;
	
	@NotNull
	private Long userId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_MINADRRESS, message = "{message.input.lenght}")
	private String name;
	
	@ValidChose
	private Integer sector;
	
	@ValidChose
	private Integer wilaya;
	
	@ValidChose
	private Integer frequency;
	
	@NotNull
	private DocumentType type;
	
	private boolean enabled;
	
	public AlertPostForm() {
		this.enabled = true;
	}
	
	public AlertPostForm(final Long userId, final DocumentType type) {
		this();
		this.userId = userId;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
		return "AlertPostForm [id=" + id + ", userId=" + userId + ", name=" + name + ", sector=" + sector + ", wilaya="
				+ wilaya + ", frequency=" + frequency + ", type=" + type + ", enabled=" + enabled + "]";
	}

}
