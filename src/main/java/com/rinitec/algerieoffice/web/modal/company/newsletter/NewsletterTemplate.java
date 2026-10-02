package com.rinitec.algerieoffice.web.modal.company.newsletter;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.newsletter.Maintemplate;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class NewsletterTemplate implements Serializable {
	private static final long serialVersionUID = -1215927610820347532L;
	
	private final String urlCover;
	private final String paneColor;
	private final String textColor;
	private final String title;
	private final String description;
	private final int label;
	private final boolean[] params = new boolean[5];
	private final NewsletterSocial socials;
	
	public NewsletterTemplate(final Maintemplate maintemplate, final NewsletterSocial socials) {
		if(maintemplate != null) {
			this.parseParams(maintemplate.getParams());
			this.urlCover = this.params[0] && maintemplate.getHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + maintemplate.getCompanyId() + "&type=" + AvatarType.template 
					: null;
			this.paneColor = maintemplate.getPaneColor();
			this.textColor = maintemplate.getTextColor();
			this.title = this.params[3] && !StringUtils.isEmpty(maintemplate.getTitle()) ? maintemplate.getTitle() : null;
			this.description = !StringUtils.isEmpty(maintemplate.getDescription()) ? maintemplate.getDescription() : null;
			this.label = maintemplate.getLabel();
		} else {
			this.urlCover = this.title = this.description = null;
			this.paneColor = "#2C3F50";
			this.textColor = "#FFFFFF";
			this.label = 1;
		}
		this.socials = this.params[4] ? socials : null;
	}
	
	private final void parseParams(final String params) {
		for (int i = 0; i < 5; i++) {
			this.params[i] = (params.charAt(i) == '1' || i == 2);
		}
	}

	public String getUrlCover() {
		return urlCover;
	}

	public String getPaneColor() {
		return paneColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public int getLabel() {
		return label;
	}

	public boolean[] getParams() {
		return params;
	}
	
	public NewsletterSocial getSocials() {
		return socials;
	}

	@Override
	public String toString() {
		return "NewsletterTemplate [urlCover=" + urlCover + ", paneColor=" + paneColor + ", textColor=" + textColor
				+ ", title=" + title + ", description=" + description + ", label=" + label + ", params="
				+ Arrays.toString(params) + ", socials=" + socials + "]";
	}

}
