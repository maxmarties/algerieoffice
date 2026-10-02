package com.rinitec.algerieoffice.persistence.modal.company.posts;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "categories")
public class Category implements Serializable {
	private static final long serialVersionUID = 196020118128751064L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "category_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long companyId;
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 60)
	private String identify;
	
	@Column(nullable = true, length = 250)
	private String description;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID parentUUID;
	
	@Column(nullable = false)
	private Boolean hasPingled;
	
	public Category() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UUID getParentUUID() {
		return parentUUID;
	}

	public void setParentUUID(UUID parentUUID) {
		this.parentUUID = parentUUID;
	}

	public Boolean getHasPingled() {
		return hasPingled;
	}
	
	public void setHasPingled(Boolean hasPingled) {
		this.hasPingled = hasPingled;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", companyId=" + companyId + ", name=" + name + ", identify=" + identify
				+ ", description=" + description + ", parentUUID=" + parentUUID + ", hasPingled=" + hasPingled + "]";
	}
	
}
