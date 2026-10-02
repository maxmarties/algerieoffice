package com.rinitec.algerieoffice.web.form.company.posts;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidUrl;

public class CategoryForm implements Serializable {
	private static final long serialVersionUID = 8563568931193050186L;

	private String id;
	
	@NotNull
	private Long companyId;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String name;
	
	@ValidUrl
	@NotNull
	private String identify;
	
	private String parentUUID;
	
	@NotNull
	private Boolean hasParent;
	
	private String description;
	
	@NotNull
	private Boolean hasPingled;
	
	public CategoryForm() {
		this.hasParent = false;
	}
	
	public CategoryForm(final Long companyId) {
		this.companyId = companyId;
		this.hasParent = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentify() {
		return identify;
	}
	
	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public String getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(String parentUUID) {
		this.parentUUID = parentUUID;
	}

	public Boolean getHasParent() {
		return hasParent;
	}

	public void setHasParent(Boolean hasParent) {
		this.hasParent = hasParent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getHasPingled() {
		return hasPingled;
	}
	
	public void setHasPingled(Boolean hasPingled) {
		this.hasPingled = hasPingled;
	}
	
	public boolean isPresentParentUUID() {
		return !StringUtils.isEmpty(parentUUID) && !parentUUID.equals("-");
	}

	@Override
	public String toString() {
		return "CategoryForm [id=" + id + ", companyId=" + companyId + ", name=" + name + ", identify=" + identify
				+ ", parentUUID=" + parentUUID + ", hasParent=" + hasParent + ", description=" + description
				+ ", hasPingle=" + hasPingled + "]";
	}
	
}
