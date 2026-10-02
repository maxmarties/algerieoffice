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

import com.rinitec.algerieoffice.persistence.modal.company.overview.Maincatalog;

@Entity
@Table(name = "catalog_item")
public class CatalogItem implements Serializable {
	private static final long serialVersionUID = 4601729955183805104L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 60)
	private String title;
	
	@Column(nullable = true, length = 30)
	private String textAlt;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@ManyToOne
	@JoinColumn(name = "maincatalog_id", referencedColumnName = "company_id", nullable = false)
	private Maincatalog maincatalog;
	
	public CatalogItem() {
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

	public String getTextAlt() {
		return textAlt;
	}

	public void setTextAlt(String textAlt) {
		this.textAlt = textAlt;
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public Maincatalog getMaincatalog() {
		return maincatalog;
	}

	public void setMaincatalog(Maincatalog maincatalog) {
		this.maincatalog = maincatalog;
	}

	@Override
	public String toString() {
		return "CatalogItem [id=" + id + ", title=" + title + ", textAlt=" + textAlt + ", photoUUID=" + photoUUID
				+ ", maincatalog=" + maincatalog + "]";
	}
	
}
