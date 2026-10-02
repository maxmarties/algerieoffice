package com.rinitec.algerieoffice.web.modal.user.easylist;

import com.rinitec.algerieoffice.persistence.modal.company.Company;
import com.rinitec.algerieoffice.persistence.modal.company.posts.Post;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class EasylistPostLine extends EasylistDocumentLine {
	private static final long serialVersionUID = -8949653607775704390L;
	
	private final int type;
	private final int priceType;
	private final String priceValue;
	
	public EasylistPostLine(final Post post, final Integer priceType, final Integer priceValue, final Company company, final String companyURL) {
		super(post, company, companyURL);
		this.type = post.getService() ? 2 : 1;
		this.priceType = priceType;
		this.priceValue = ParseUtil.getFormattedOrder(priceValue);
	}

	public int getType() {
		return type;
	}

	public int getPriceType() {
		return priceType;
	}

	public String getPriceValue() {
		return priceValue;
	}

	@Override
	public String toString() {
		return "EasylistPostLine [type=" + type + ", priceType=" + priceType + ", priceValue=" + priceValue + "]";
	}

}
