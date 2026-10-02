package com.rinitec.algerieoffice.persistence.modal.medias;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.rinitec.algerieoffice.enums.EnvelopeType;

@Entity
@Table(name = "envelopes")
public class Envelope implements Serializable {
	private static final long serialVersionUID = 1156287753452905819L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "envelope_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false, length = 30)
	private String filename;
	
	@Column(nullable = false, length = 30)
	private String contentType;
	
	@Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private EnvelopeType envelopeType;
	
	public Envelope() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public EnvelopeType getEnvelopeType() {
		return envelopeType;
	}

	public void setEnvelopeType(EnvelopeType envelopeType) {
		this.envelopeType = envelopeType;
	}

	@Override
	public String toString() {
		return "Envelope [id=" + id + ", filename=" + filename + ", contentType=" + contentType + ", envelopeType="
				+ envelopeType + "]";
	}

}
