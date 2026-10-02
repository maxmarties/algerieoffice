package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class LinkedForm implements Serializable {
	private static final long serialVersionUID = 6812860604064894942L;

	@NotNull
	private Long id;
	
	private String url;
	private String checkedURL;
	private String tageline;
	private String keysword;
	
	@NotNull
	@Length(min = ConstraintesForm.MIN_LENGTH_DESCRIPTION, max = ConstraintesForm.MAX_LENGTH_DESCRIPTION, message = "{message.input.lenght}")
	private String description;
	
	private List<Long> idents;
	private List<String> websitesName;
	private List<String> websitesUrl;
	private List<Integer> websitesType;
	private List<String> photosUUID;
	private List<Long> updated;
	private List<Long> trashed;
	
	private String facebook;
	private String twitter;
	private String linkedin;
	private String youtube;
	private String google;
	private String instagram;
	
	private MultipartFile[] files;
	
	private boolean updateWebsite = false;
	
	public LinkedForm() {
		this.idents = new ArrayList<Long>();
		this.websitesName = new ArrayList<String>();
		this.websitesUrl = new ArrayList<String>();
		this.websitesType = new ArrayList<Integer>();
		this.photosUUID = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTageline() {
		return tageline;
	}

	public void setTageline(String tageline) {
		this.tageline = tageline;
	}

	public String getKeysword() {
		return keysword;
	}

	public void setKeysword(String keysword) {
		this.keysword = keysword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Long> getIdents() {
		return idents;
	}
	
	public void setIdents(List<Long> idents) {
		this.idents = idents;
	}

	public List<String> getWebsitesName() {
		return websitesName;
	}

	public void setWebsitesName(List<String> websitesName) {
		this.websitesName = websitesName;
	}

	public List<String> getWebsitesUrl() {
		return websitesUrl;
	}

	public void setWebsitesUrl(List<String> websitesUrl) {
		this.websitesUrl = websitesUrl;
	}

	public List<Integer> getWebsitesType() {
		return websitesType;
	}

	public void setWebsitesType(List<Integer> websitesType) {
		this.websitesType = websitesType;
	}

	public List<String> getPhotosUUID() {
		return photosUUID;
	}
	
	public void setPhotosUUID(List<String> photosUUID) {
		this.photosUUID = photosUUID;
	}
	
	public List<Long> getUpdated() {
		return updated;
	}
	
	public void setUpdated(List<Long> updated) {
		this.updated = updated;
	}
	
	public List<Long> getTrashed() {
		return trashed;
	}
	
	public void setTrashed(List<Long> trashed) {
		this.trashed = trashed;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getYoutube() {
		return youtube;
	}

	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public boolean isUpdateWebsite() {
		return updateWebsite;
	}
	
	public void setUpdateWebsite(boolean updateWebsite) {
		this.updateWebsite = updateWebsite;
	}
	
	public String[] buildKeysword() {
		return !StringUtils.isEmpty(keysword) ? keysword.split(",") : null;
	}
	
	public String buildSocial(final String social) {
		switch(social) {
		case "facebook": return facebook;
		case "twitter": return twitter;
		case "google": return google;
		case "linkedin": return linkedin;
		case "youtube": return youtube;
		case "instagram": return instagram;
		}
		return null;
	}
	
	public boolean hasSocial() {
		return !StringUtils.isEmpty(facebook) || !StringUtils.isEmpty(twitter) || !StringUtils.isEmpty(google)
				|| !StringUtils.isEmpty(linkedin) || !StringUtils.isEmpty(youtube) || !StringUtils.isEmpty(instagram);
	}

	@Override
	public String toString() {
		return "LinkedForm [id=" + id + ", url=" + url + ", checkedURL=" + checkedURL + ", tageline=" + tageline
				+ ", keysword=" + keysword + ", description=" + description + ", idents=" + idents + ", websitesName="
				+ websitesName + ", websitesUrl=" + websitesUrl + ", websitesType=" + websitesType + ", photosUUID="
				+ photosUUID + ", updated=" + updated + ", trashed=" + trashed + ", facebook=" + facebook + ", twitter="
				+ twitter + ", linkedin=" + linkedin + ", youtube=" + youtube + ", google=" + google + ", instagram="
				+ instagram + ", updateWebsite=" + updateWebsite + "]";
	}
	
}
