package com.rinitec.algerieoffice.web.modal.company.portfolio;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.portfolio.Work;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class WorkLine implements Serializable {
	private static final long serialVersionUID = -6145760771086851092L;
	
	private final String id;
	private final String title;
	private final String identifyURL;
	private final String workDate;
	private final String expertise;
	private final String partner;
	private final String autor;
	private final boolean hasPublished;
	
	public WorkLine(final Work work, final String autor, final String partner) {
		this.id = work.getId().toString();
		this.title = work.getTitle();
		this.identifyURL = ConstraintesURL.getWorkPreviewURL(work.getIdentify());
		this.workDate = DateTimeFormat.forPattern("dd/MM/yyyy").print(work.getWorkDate());
		this.expertise = !StringUtils.isEmpty(work.getExpertise()) ? work.getExpertise().replaceAll(",", ", ") : "-";
		this.partner = !StringUtils.isEmpty(partner) ? partner : "-";
		this.autor = autor;
		this.hasPublished = work.getHasPublished();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getIdentifyURL() {
		return identifyURL;
	}

	public String getWorkDate() {
		return workDate;
	}

	public String getExpertise() {
		return expertise;
	}

	public String getPartner() {
		return partner;
	}

	public String getAutor() {
		return autor;
	}

	public boolean isHasPublished() {
		return hasPublished;
	}

	@Override
	public String toString() {
		return "WorkLine [id=" + id + ", title=" + title + ", identifyURL=" + identifyURL + ", workDate=" + workDate
				+ ", expertise=" + expertise + ", partner=" + partner + ", autor=" + autor + ", hasPublished="
				+ hasPublished + "]";
	}

}
