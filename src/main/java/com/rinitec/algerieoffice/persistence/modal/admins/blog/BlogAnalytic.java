package com.rinitec.algerieoffice.persistence.modal.admins.blog;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "blogs_search")
public class BlogAnalytic implements Serializable {
	private static final long serialVersionUID = -1027241769979456067L;
	
	@Id
	@Column(name = "blog_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID blogId;
	
	@Column(nullable = false)
	private Integer simultude;
	
	@Column(nullable = false)
	private Integer follow;
	
	@Column(nullable = false)
	private Integer market;
	
	public BlogAnalytic() {
		this.simultude = this.follow = this.market = 0;
	}
	
	public BlogAnalytic(final UUID blogId) {
		this();
		this.blogId = blogId;
	}

	public UUID getBlogId() {
		return blogId;
	}

	public void setBlogId(UUID blogId) {
		this.blogId = blogId;
	}

	public Integer getSimultude() {
		return simultude;
	}

	public void setSimultude(Integer simultude) {
		this.simultude = simultude;
	}

	public Integer getFollow() {
		return follow;
	}

	public void setFollow(Integer follow) {
		this.follow = follow;
	}

	public Integer getMarket() {
		return market;
	}

	public void setMarket(Integer market) {
		this.market = market;
	}

	@Override
	public String toString() {
		return "BlogAnalytic [blogId=" + blogId + ", simultude=" + simultude + ", follow=" + follow + ", market="
				+ market + "]";
	}

}
