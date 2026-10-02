package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerWidgetPost implements Serializable {
	private static final long serialVersionUID = 1089682076994445720L;
	
	private final String photoURL;
	private final String photoAlt;
	private final String title;
	private final String identifyURL;
	private final String description;
	private final String category;
	private final String categoryURL;
	private final String urlExtern;
	private final Integer priceType;
	private final String priceValue;
	private final String priceParrain;
	private final String precision;
	private final boolean labelNew;
	private final boolean labelExclusif;
	
	public ExplorerWidgetPost(final Post post, final PostDetail postDetail, final PostPhoto postPhoto, final Category category, final String urlPosts, final String urlCategories) {
		this.photoURL = ConstraintesURL.URL_PHOTOS + "?photoId=".concat(postPhoto.getPhotoUUID().toString());
		this.photoAlt = !StringUtils.isEmpty(postPhoto.getTextAlt()) ? postPhoto.getTextAlt() : post.getTitle();
		this.title = post.getTitle();
		this.identifyURL = urlPosts.concat("/").concat(post.getIdentify());
		this.description = post.getDescription();
		this.urlExtern = postDetail.getUrlExtern();
		this.priceType = postDetail.getPriceType();
		this.priceValue = ParseUtil.getFormattedOrder(postDetail.getPriceValue());
		this.priceParrain = !StringUtils.isEmpty(postDetail.getPriceParrain()) ? ParseUtil.getFormattedCapital(postDetail.getPriceParrain()) : null;
		this.precision = postDetail.getPricePrecision();
		this.labelNew = postDetail.getLabelNew();
		this.labelExclusif = postDetail.getLabelExclusif();
		if(category != null) {
			this.category = category.getName();
			this.categoryURL = urlCategories.concat(category.getIdentify());
		} else {
			this.category = this.categoryURL = null;
		}
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public String getPhotoAlt() {
		return photoAlt;
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

	public String getCategory() {
		return category;
	}

	public String getCategoryURL() {
		return categoryURL;
	}

	public String getUrlExtern() {
		return urlExtern;
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

	@Override
	public String toString() {
		return "ExplorerWidgetPost [photoURL=" + photoURL + ", photoAlt=" + photoAlt + ", title=" + title
				+ ", identifyURL=" + identifyURL + ", description=" + description + ", category=" + category
				+ ", categoryURL=" + categoryURL + ", urlExtern=" + urlExtern + ", priceType=" + priceType
				+ ", priceValue=" + priceValue + ", priceParrain=" + priceParrain + ", precision=" + precision
				+ ", labelNew=" + labelNew + ", labelExclusif=" + labelExclusif + "]";
	}

}
