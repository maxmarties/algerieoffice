package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.rinitec.algerieoffice.enums.AvatarType;
import com.rinitec.algerieoffice.persistence.modal.company.overview.Mainheader;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerCompanyHeader implements Serializable {
	private static final long serialVersionUID = 7950549431783802487L;
	
	private final String canva;
	private final String canvaColor;
	private final String textColor;
	private final String urlLogo;
	private final String urlCover;
	private final String updateDate;
	private final Integer evaluation;
	private final Long countEvaluation;
	private final Long countLiked;
	
	public ExplorerCompanyHeader(final Mainheader mainheader, final DateTime modifiedDate, final String urlLogo, final Integer evaluation, 
			final Long countEvaluation, final Long countLiked) {
		if(mainheader != null) {
			this.canva = mainheader.getCanva();
			this.canvaColor = mainheader.getCanvaColor();
			this.textColor = mainheader.getTextColor();
			this.urlCover = mainheader.getHasCover() ? ConstraintesURL.URL_AVATARS + "?postedId=" + mainheader.getCompanyId() + "&type=" + AvatarType.cover 
					: "/static/picts/aobns/header-min.jpg";
		} else {
			this.canva = "0.6";
			this.canvaColor = "#2C3F50";
			this.textColor = "#FFFFFF";
			this.urlCover = "/static/picts/aobns/header-min.jpg";
		}
		this.urlLogo = urlLogo;
		this.updateDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(modifiedDate);
		this.evaluation = evaluation;
		this.countEvaluation = countEvaluation;
		this.countLiked = countLiked;
	}

	public String getCanva() {
		return canva;
	}

	public String getCanvaColor() {
		return canvaColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public String getUrlLogo() {
		return urlLogo;
	}

	public String getUrlCover() {
		return urlCover;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public Long getCountEvaluation() {
		return countEvaluation;
	}

	public Long getCountLiked() {
		return countLiked;
	}
	
	public int averageLiked() {
		return (int) ((100 * countLiked) / countEvaluation);
	}

	@Override
	public String toString() {
		return "ExplorerCompanyHeader [canva=" + canva + ", canvaColor=" + canvaColor + ", textColor=" + textColor
				+ ", urlLogo=" + urlLogo + ", urlCover=" + urlCover + ", updateDate=" + updateDate + ", evaluation="
				+ evaluation + ", countEvaluation=" + countEvaluation + ", countLiked=" + countLiked + "]";
	}

}
