package com.rinitec.algerieoffice.web.modal.publics.marketplace.posts;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentWidgetMini;

public class PostWidgetMini extends DocumentWidgetMini {
	private static final long serialVersionUID = 7552067515045435385L;
	
	private final String photoURL;
	private final String photoAlt;
	private final Integer priceType;
	private final String priceValue;
	private final String priceParrain;
	private final String precision;
	private final int type;
	private final boolean labelNew;
	private final boolean labelExclusif;
	
	public PostWidgetMini(final Post post, final PostDetail postDetail, final PostPhoto postPhoto, final Company company, final String url, final Long userId, 
			final DateTime forOrder1, final DateTime forOrder2, final Integer forOrder3, final Integer forOrder4) {
		super(post, company, url, userId);
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
		return "PostWidgetMini [photoURL=" + photoURL + ", photoAlt=" + photoAlt + ", priceType=" + priceType
				+ ", priceValue=" + priceValue + ", priceParrain=" + priceParrain + ", precision=" + precision
				+ ", type=" + type + ", labelNew=" + labelNew + ", labelExclusif=" + labelExclusif + "]";
	}
	
}
