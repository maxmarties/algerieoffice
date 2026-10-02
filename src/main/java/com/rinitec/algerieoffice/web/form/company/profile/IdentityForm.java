package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidActivity;
import com.rinitec.algerieoffice.web.validator.ValidDate;

public class IdentityForm implements Serializable {
	private static final long serialVersionUID = -2401956435817784070L;
	
	@NotNull
	private Long id;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_PASSWORD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String denomination;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_FIELD, max = ConstraintesForm.MAX_LENGTH_DENOMINATION, message = "{message.input.lenght}")
	private String tradename;
	
	@ValidDate
	@NotNull
	private String buildDate;
	
	@ValidActivity
	@NotNull
	private String activity;
	
	private List<String> activities;
	
	private String url;
	private String checkedURL;
	
	private MultipartFile file;
	
	public IdentityForm() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public List<String> getActivities() {
		return activities;
	}

	public void setActivities(List<String> activities) {
		this.activities = activities;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCheckedURL() {
		return checkedURL;
	}

	public void setCheckedURL(String checkedURL) {
		this.checkedURL = checkedURL;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public String getInfoLine(int line) {
		switch(line) {
		case 1: return denomination;
		case 2: return tradename;
		case 3: return buildDate;
		}
		return "";
	}

	@Override
	public String toString() {
		return "IdentityForm [id=" + id + ", denomination=" + denomination + ", tradename=" + tradename + ", buildDate="
				+ buildDate + ", activity=" + activity + ", activities=" + activities + ", url=" + url + ", checkedURL="
				+ checkedURL + "]";
	}

}
