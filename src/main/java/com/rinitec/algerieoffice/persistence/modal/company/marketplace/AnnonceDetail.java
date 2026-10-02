package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "annonces_detail")
public class AnnonceDetail implements Serializable {
	private static final long serialVersionUID = -7736113826687727890L;
	
	@Id
	@Column(name = "annonce_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Lob
	@Column(nullable = false)
	private byte[] detail;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlExtern;
	
	@Column(columnDefinition = "uuid", nullable = true)
	@Type(type = "pg-uuid")
	private UUID fileUUID;
	
	@Max(5)
	@Column(nullable = false)
	private Integer visibility;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Annonce annonce;
	
	public AnnonceDetail() {
	}
	
	public AnnonceDetail(final Annonce annonce) {
		this.annonce = annonce;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public byte[] getDetail() {
		return detail;
	}

	public void setDetail(byte[] detail) {
		this.detail = detail;
	}

	public String getUrlExtern() {
		return urlExtern;
	}

	public void setUrlExtern(String urlExtern) {
		this.urlExtern = urlExtern;
	}

	public UUID getFileUUID() {
		return fileUUID;
	}

	public void setFileUUID(UUID fileUUID) {
		this.fileUUID = fileUUID;
	}

	public Integer getVisibility() {
		return visibility;
	}
	
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public Annonce getAnnonce() {
		return annonce;
	}

	public void setAnnonce(Annonce annonce) {
		this.annonce = annonce;
	}

	@Override
	public String toString() {
		return "AnnonceDetail [id=" + id + ", detail=" + Arrays.toString(detail) + ", urlExtern=" + urlExtern
				+ ", fileUUID=" + fileUUID + ", visibility=" + visibility + "]";
	}

}
