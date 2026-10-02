package com.rinitec.algerieoffice.web.form.company.overview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class MaincatalogForm implements Serializable {
	private static final long serialVersionUID = -228038673260725056L;

	@NotNull
	private Long id;
	
	@NotNull
	private Boolean style;
	
	private List<Long> idents;
	private List<String> titles;
	private List<String> textsAlt;
	private List<String> photosUUID;
	private List<Long> updated;
	private List<Long> trashed;
	
	private MultipartFile[] files;
	
	private boolean updateCatalog = false;
	
	public MaincatalogForm() {
		this.idents = new ArrayList<Long>();
		this.titles = new ArrayList<String>();
		this.textsAlt = new ArrayList<String>();
		this.photosUUID = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getStyle() {
		return style;
	}

	public void setStyle(Boolean style) {
		this.style = style;
	}

	public List<Long> getIdents() {
		return idents;
	}
	
	public void setIdents(List<Long> idents) {
		this.idents = idents;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getTextsAlt() {
		return textsAlt;
	}

	public void setTextsAlt(List<String> textsAlt) {
		this.textsAlt = textsAlt;
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

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public boolean isUpdateCatalog() {
		return updateCatalog;
	}

	public void setUpdateCatalog(boolean updateCatalog) {
		this.updateCatalog = updateCatalog;
	}
	
	public String photoURL(int index) {
		return index < photosUUID.size() ? "/media/photo?photoId=".concat(photosUUID.get(index)) 
				: "/static/picts/avatars/catalog-min.jpg";
	}
	
	public String title(int index) {
		return index < titles.size() ? titles.get(index) : "";
	}
	
	public String buildTextAlt(int index) {
		return !StringUtils.isEmpty(textsAlt.get(index)) ? textsAlt.get(index) : "-";
	}

	@Override
	public String toString() {
		return "MaincatalogForm [id=" + id + ", style=" + style + ", idents=" + idents + ", titles=" + titles
				+ ", textsAlt=" + textsAlt + ", photosUUID=" + photosUUID + ", updated=" + updated + ", trashed="
				+ trashed + ", updateCatalog=" + updateCatalog + "]";
	}
	
}
