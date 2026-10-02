package com.rinitec.algerieoffice.persistence.modal.admins.blog;

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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "blogs_detail")
public class BlogDetail implements Serializable {
	private static final long serialVersionUID = -4975934138409908248L;
	
	@Id
	@Column(name = "blog_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = true, length = 512)
	private String keysword;
	
	@Lob
	@Column(nullable = false)
	private byte[] detail;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID photoUUID;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Blog blog;
	
	public BlogDetail() {
	}
	
	public BlogDetail(final Blog blog) {
		this.blog = blog;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public byte[] getDetail() {
		return detail;
	}

	public void setDetail(byte[] detail) {
		this.detail = detail;
	}

	public UUID getPhotoUUID() {
		return photoUUID;
	}

	public void setPhotoUUID(UUID photoUUID) {
		this.photoUUID = photoUUID;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@Override
	public String toString() {
		return "BlogDetail [id=" + id + ", keysword=" + keysword + ", detail=" + Arrays.toString(detail)
				+ ", photoUUID=" + photoUUID + ", blog=" + blog + "]";
	}

}
