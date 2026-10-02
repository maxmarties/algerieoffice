package com.rinitec.algerieoffice.persistence.modal.admins.blog;

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
@Table(name = "blogs_like")
public class BlogLike implements Serializable {
	private static final long serialVersionUID = -3661936310335707015L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "like_id", columnDefinition = "uuid", updatable = false, nullable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long userId;
	
	@Column(columnDefinition = "uuid", nullable = false)
	@Type(type = "pg-uuid")
	private UUID blogId;
	
	public BlogLike() {
	}
	
	public BlogLike(final Long userId, final UUID blogId) {
		this.userId = userId;
		this.blogId = blogId;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UUID getBlogId() {
		return blogId;
	}

	public void setBlogId(UUID blogId) {
		this.blogId = blogId;
	}

	@Override
	public String toString() {
		return "BlogLike [id=" + id + ", userId=" + userId + ", blogId=" + blogId + "]";
	}

}
