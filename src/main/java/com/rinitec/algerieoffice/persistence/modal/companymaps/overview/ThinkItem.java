package com.rinitec.algerieoffice.persistence.modal.companymaps.overview;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainthink;

@Entity
@Table(name = "think_item")
public class ThinkItem implements Serializable {
	private static final long serialVersionUID = -3432884931363928874L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 10)
	private String think;
	
	@Column(nullable = false, length = 24)
	private String title;
	
	@Column(nullable = true, length = 60)
	private String description;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@ManyToOne
	@JoinColumn(name = "mainthink_id", referencedColumnName = "company_id", nullable = false)
	private Mainthink mainthink;
	
	public ThinkItem() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThink() {
		return think;
	}

	public void setThink(String think) {
		this.think = think;
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

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public Mainthink getMainthink() {
		return mainthink;
	}

	public void setMainthink(Mainthink mainthink) {
		this.mainthink = mainthink;
	}

	@Override
	public String toString() {
		return "ThinkItem [id=" + id + ", think=" + think + ", title=" + title + ", description=" + description
				+ ", photoUUID=" + photoUUID + ", mainthink=" + mainthink + "]";
	}

}
