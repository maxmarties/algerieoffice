package com.rinitec.algerieoffice.persistence.modal.company.posts;

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
@Table(name = "posts_detail")
public class PostDetail implements Serializable {
	private static final long serialVersionUID = -3962785883590233648L;

	@Id
	@Column(name = "post_id", columnDefinition = "uuid", nullable = false, updatable = false)
	@Type(type = "pg-uuid")
	private UUID id;
	
	@Lob
	@Column(nullable = false)
	private byte[] detail;
	
	@Column(nullable = true, unique = true, length = 250)
	private String urlExtern;
	
	@Max(3)
	@Column(nullable = false)
	private Integer priceType;
	
	@Column(nullable = true)
	private Integer priceValue;
	
	@Column(nullable = true, length = 12)
	private String priceParrain;
	
	@Column(nullable = true, length = 60)
	private String pricePrecision;
	
	@Column(nullable = false)
	private Boolean labelNew;
	
	@Column(nullable = false)
	private Boolean labelExclusif;
	
	@OneToOne(fetch = FetchType.LAZY)
    @MapsId
	private Post post;
	
	public PostDetail() {
	}
	
	public PostDetail(final Post post) {
		this.post = post;
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

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public Integer getPriceValue() {
		return priceValue;
	}
	
	public void setPriceValue(Integer priceValue) {
		this.priceValue = priceValue;
	}

	public String getPriceParrain() {
		return priceParrain;
	}

	public void setPriceParrain(String priceParrain) {
		this.priceParrain = priceParrain;
	}

	public String getPricePrecision() {
		return pricePrecision;
	}

	public void setPricePrecision(String pricePrecision) {
		this.pricePrecision = pricePrecision;
	}

	public Boolean getLabelNew() {
		return labelNew;
	}

	public void setLabelNew(Boolean labelNew) {
		this.labelNew = labelNew;
	}

	public Boolean getLabelExclusif() {
		return labelExclusif;
	}

	public void setLabelExclusif(Boolean labelExclusif) {
		this.labelExclusif = labelExclusif;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "PostDetail [id=" + id + ", detail=" + Arrays.toString(detail) + ", urlExtern=" + urlExtern
				+ ", priceType=" + priceType + ", priceValue=" + priceValue + ", priceParrain=" + priceParrain
				+ ", pricePrecision=" + pricePrecision + ", labelNew=" + labelNew + ", labelExclusif=" + labelExclusif
				+ ", post=" + post + "]";
	}
	
}
