package com.rinitec.algerieoffice.persistence.modal.company.posts;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "posts_search")
public class PostSearch implements Serializable {
	private static final long serialVersionUID = -8002739879192297495L;
	
	@Id
	@Column(name = "post_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Column(nullable = false)
	private Long simultude;
	
	@Column(nullable = false)
	private Long token;
	
	@Column(nullable = false)
	private Long filter;
	
	@Column(nullable = false)
	private Long tag;
	
	@Column(nullable = false)
	private Long view;
	
	@Column(nullable = false)
	private Integer clickCount;
	
	@Column(nullable = false)
	private Integer workCount;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Post post;
	
	public PostSearch() {
		this.simultude = this.token = this.filter = this.tag = this.view = 0L;
		this.clickCount = this.workCount = 0;
	}
	
	public PostSearch(final Post post) {
		this();
		this.post = post;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Long getSimultude() {
		return simultude;
	}

	public void setSimultude(Long simultude) {
		this.simultude = simultude;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}

	public Long getFilter() {
		return filter;
	}

	public void setFilter(Long filter) {
		this.filter = filter;
	}

	public Long getTag() {
		return tag;
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}
	
	public Long getView() {
		return view;
	}
	
	public void setView(Long view) {
		this.view = view;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getWorkCount() {
		return workCount;
	}

	public void setWorkCount(Integer workCount) {
		this.workCount = workCount;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "PostSearch [id=" + id + ", simultude=" + simultude + ", token=" + token + ", filter=" + filter
				+ ", tag=" + tag + ", view=" + view + ", clickCount=" + clickCount + ", workCount=" + workCount
				+ ", post=" + post + "]";
	}

}
