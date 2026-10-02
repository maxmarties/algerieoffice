package com.rinitec.algerieoffice.persistence.modal.company.marketplace;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

@Entity
@Table(name = "annonces_wilayas")
public class AnnonceWilaya implements Serializable {
	private static final long serialVersionUID = -2951499334945333779L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "target_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;

	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID annonceUUID;
	
	@Max(ConstraintesForm.COUNT_WILAYA)
	@Column(nullable = true)
	private Integer wilaya;
	
	public AnnonceWilaya() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getAnnonceUUID() {
		return annonceUUID;
	}

	public void setAnnonceUUID(UUID annonceUUID) {
		this.annonceUUID = annonceUUID;
	}

	public Integer getWilaya() {
		return wilaya;
	}

	public void setWilaya(Integer wilaya) {
		this.wilaya = wilaya;
	}

	@Override
	public String toString() {
		return "AnnonceWilaya [id=" + id + ", annonceUUID=" + annonceUUID + ", wilaya=" + wilaya + "]";
	}
	
}
