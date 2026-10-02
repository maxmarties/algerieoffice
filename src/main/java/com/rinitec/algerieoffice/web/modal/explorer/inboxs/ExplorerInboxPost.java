package com.rinitec.algerieoffice.web.modal.explorer.inboxs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.posts.Category;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostDetail;
import com.rinitec.algerieoffice.persistence.modal.company.posts.PostPhoto;
import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerInboxPost implements Serializable {
	private static final long serialVersionUID = -5367174423836194590L;
	
	private final String id;
	private final String title;
	private final String detail;
	private final String urlExtern;
	private final Integer priceType;
	private final String priceValue;
	private final String priceParrain;
	private final String precision;
	private final String modifiedDate;
	private final String category;
	private final String categoryURL;
	private final boolean labelNew;
	private final boolean labelExclusif;
	private final int type;
	private final List<String> photosURL = new ArrayList<String>();
	private final List<String> photosAlt = new ArrayList<String>();
	
	public ExplorerInboxPost(final Post post, final PostDetail postDetail, final List<PostPhoto> postPhotos, 
			final Category category, final String urlCategories) {
		this.id = post.getId().toString();
		this.title = post.getTitle();
		this.detail = new String(postDetail.getDetail());
		this.urlExtern = postDetail.getUrlExtern();
		this.priceType = postDetail.getPriceType();
		this.priceValue = ParseUtil.getFormattedOrder(postDetail.getPriceValue());
		this.priceParrain = !StringUtils.isEmpty(postDetail.getPriceParrain()) ? ParseUtil.getFormattedCapital(postDetail.getPriceParrain()) : null;
		this.precision = postDetail.getPricePrecision();
		this.modifiedDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(post.getModifiedDate());
		this.labelNew = postDetail.getLabelNew();
		this.labelExclusif = postDetail.getLabelExclusif();
		this.type = post.getService() ? 2 : 1;
		for (int i = 0; i < postPhotos.size(); i++) {
			final PostPhoto postPhoto = postPhotos.get(i);
			this.photosURL.add(ConstraintesURL.URL_PHOTOS + "?photoId=".concat(postPhoto.getPhotoUUID().toString()));
			this.photosAlt.add(postPhoto.getTextAlt());
		}
		if(category != null) {
			this.category = category.getName();
			this.categoryURL = urlCategories.concat(category.getIdentify());
		} else {
			this.category = this.categoryURL = null;
		}
	}
	
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDetail() {
		return detail;
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public String getCategory() {
		return category;
	}

	public String getCategoryURL() {
		return categoryURL;
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
		return "ExplorerInboxPost [id=" + id + ", title=" + title + ", detail=" + detail + ", urlExtern=" + urlExtern
				+ ", priceType=" + priceType + ", priceValue=" + priceValue + ", priceParrain=" + priceParrain
				+ ", precision=" + precision + ", modifiedDate=" + modifiedDate + ", category=" + category
				+ ", categoryURL=" + categoryURL + ", labelNew=" + labelNew + ", labelExclusif=" + labelExclusif
				+ ", type=" + type + ", photosURL=" + photosURL + ", photosAlt=" + photosAlt + "]";
	}

}
