package com.rinitec.algerieoffice.persistence.modal.company.posts;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "posts_photos")
public class PostPhoto implements Serializable {
	private static final long serialVersionUID = -1740394965567062666L;

	@Id
	@Column(name = "photo_id", columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID postUUID;
	
	@Column(nullable = true, length = 30)
	private String textAlt;
	
	@Column(nullable = false)
	private Boolean hasPrincipal;
	
	public PostPhoto() {
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public UUID getPostUUID() {
		return postUUID;
	}

	public void setPostUUID(UUID postUUID) {
		this.postUUID = postUUID;
	}
	
	public String getTextAlt() {
		return textAlt;
	}
	
	public void setTextAlt(String textAlt) {
		this.textAlt = textAlt;
	}

	public Boolean getHasPrincipal() {
		return hasPrincipal;
	}

	public void setHasPrincipal(Boolean hasPrincipal) {
		this.hasPrincipal = hasPrincipal;
	}

	@Override
	public String toString() {
		return "PostPhoto [photoUUID=" + photoUUID + ", postUUID=" + postUUID + ", textAlt=" + textAlt
				+ ", hasPrincipal=" + hasPrincipal + "]";
	}
	
}
