package com.rinitec.algerieoffice.web.modal.publics.marketplace.posts;

import java.io.Serializable;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class PostSimilarScreen implements Serializable {
	private static final long serialVersionUID = -6772663720735903101L;

	private final UUID id;
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String photoURL;
	private final String photoAlt;
	private final Integer priceType;
	private final String priceValue;
	private final String priceParrain;
	private final String precision;
	private final int type;
	private final boolean labelNew;
	private final boolean labelExclusif;
	
	public PostSimilarScreen(final Post post, final PostDetail postDetail, final PostPhoto postPhoto, final String url, final Long forOrder1, final String forOrder2, final DateTime forOrder3) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePostURL(post.getIdentify(), url);
		this.description = post.getDescription();
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(postPhoto.getPhotoUUID().toString());
		this.photoAlt = !StringUtils.isEmpty(postPhoto.getTextAlt()) ? postPhoto.getTextAlt() : post.getTitle();
		this.priceType = postDetail.getPriceType();
		this.priceValue = ParseUtil.getFormattedOrder(postDetail.getPriceValue());
		this.priceParrain = !StringUtils.isEmpty(postDetail.getPriceParrain()) ? ParseUtil.getFormattedCapital(postDetail.getPriceParrain()) : null;
		this.precision = postDetail.getPricePrecision();
		this.type = post.getService() ? 2 : 1;
		this.labelNew = postDetail.getLabelNew();
		this.labelExclusif = postDetail.getLabelExclusif();
	}
	
	public PostSimilarScreen(final Post post, final PostDetail postDetail, final PostPhoto postPhoto, final String url, final String forOrder1, final DateTime forOrder2) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.identifyURL = ConstraintesURL.getMarketplacePostURL(post.getIdentify(), url);
		this.description = post.getDescription();
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(postPhoto.getPhotoUUID().toString());
		this.photoAlt = !StringUtils.isEmpty(postPhoto.getTextAlt()) ? postPhoto.getTextAlt() : post.getTitle();
		this.priceType = postDetail.getPriceType();
		this.priceValue = ParseUtil.getFormattedOrder(postDetail.getPriceValue());
		this.priceParrain = !StringUtils.isEmpty(postDetail.getPriceParrain()) ? ParseUtil.getFormattedCapital(postDetail.getPriceParrain()) : null;
		this.precision = postDetail.getPricePrecision();
		this.type = post.getService() ? 2 : 1;
		this.labelNew = postDetail.getLabelNew();
		this.labelExclusif = postDetail.getLabelExclusif();
	}

	public UUID getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getDescription() {
		return description;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getPhotoAlt() {
		return photoAlt;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public String getPriceValue() {
		return priceValue;
	}

	public String getPriceParrain() {
		return priceParrain;
	}

	public String getPrecision() {
		return precision;
	}

	public int getType() {
		return type;
	}

	public boolean isLabelNew() {
		return labelNew;
	}

	public boolean isLabelExclusif() {
		return labelExclusif;
	}

	@Override
	public String toString() {
		return "PostSimilarScreen [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", description="
				+ description + ", photoURL=" + photoURL + ", photoAlt=" + photoAlt + ", priceType=" + priceType
				+ ", priceValue=" + priceValue + ", priceParrain=" + priceParrain + ", precision=" + precision
				+ ", type=" + type + ", labelNew=" + labelNew + ", labelExclusif=" + labelExclusif + "]";
	}
	
}
