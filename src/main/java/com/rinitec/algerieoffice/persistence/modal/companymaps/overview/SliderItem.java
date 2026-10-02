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

import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainslider;

@Entity
@Table(name = "slider_item")
public class SliderItem implements Serializable {
	private static final long serialVersionUID = 127932423985886655L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String title;
	
	@Column(nullable = false, length = 124)
	private String description;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@ManyToOne
	@JoinColumn(name = "mainslider_id", referencedColumnName = "company_id", nullable = false)
	private Mainslider mainslider;
	
	public SliderItem() {
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

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public Mainslider getMainslider() {
		return mainslider;
	}

	public void setMainslider(Mainslider mainslider) {
		this.mainslider = mainslider;
	}

	@Override
	public String toString() {
		return "SliderItem [id=" + id + ", title=" + title + ", description=" + description + ", photoUUID=" + photoUUID
				+ ", mainslider=" + mainslider + "]";
	}

}
