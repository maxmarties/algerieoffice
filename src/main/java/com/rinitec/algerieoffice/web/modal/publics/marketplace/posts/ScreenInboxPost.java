package com.rinitec.algerieoffice.web.modal.publics.marketplace.posts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.publics.marketplace.DocumentInbox;

public class ScreenInboxPost extends DocumentInbox {
	private static final long serialVersionUID = 7351241944924438877L;
	
	private final Integer priceType;
	private final String priceValue;
	private final String priceParrain;
	private final String precision;
	private final boolean labelNew;
	private final boolean labelExclusif;
	private final int type;
	private final List<String> photosURL = new ArrayList<String>();
	private final List<String> photosAlt = new ArrayList<String>();
	
	public ScreenInboxPost(final Post post, final PostDetail postDetail, final List<PostPhoto> postPhotos) {
		super(post, postDetail.getDetail(), postDetail.getUrlExtern());
		this.priceType = postDetail.getPriceType();
		this.priceValue = ParseUtil.getFormattedOrder(postDetail.getPriceValue());
		this.priceParrain = !StringUtils.isEmpty(postDetail.getPriceParrain()) ? ParseUtil.getFormattedCapital(postDetail.getPriceParrain()) : null;
		this.precision = postDetail.getPricePrecision();
		this.labelNew = postDetail.getLabelNew();
		this.labelExclusif = postDetail.getLabelExclusif();
		this.type = post.getService() ? 2 : 1;
		for (int i = 0; i < postPhotos.size(); i++) {
			final PostPhoto postPhoto = postPhotos.get(i);
			this.photosURL.add(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(postPhoto.getPhotoUUID().toString()));
			this.photosAlt.add(postPhoto.getTextAlt());
		}
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

	public boolean isLabelNew() {
		return labelNew;
	}

	public boolean isLabelExclusif() {
		return labelExclusif;
	}

	public int getType() {
		return type;
	}

	public List<String> getPhotosURL() {
		return photosURL;
	}

	public List<String> getPhotosAlt() {
		return photosAlt;
	}

	@Override
	public String toString() {
		return "ScreenInboxPost [priceType=" + priceType + ", priceValue=" + priceValue + ", priceParrain="
				+ priceParrain + ", precision=" + precision + ", labelNew=" + labelNew + ", labelExclusif="
				+ labelExclusif + ", type=" + type + ", photosURL=" + photosURL + ", photosAlt=" + photosAlt + "]";
	}
	
}
