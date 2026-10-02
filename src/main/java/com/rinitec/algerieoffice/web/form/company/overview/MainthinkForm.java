package com.rinitec.algerieoffice.web.form.company.overview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class MainthinkForm implements Serializable {
	private static final long serialVersionUID = -6565504075768614280L;
	
	@NotNull
	private Long id;
	
	private List<Long> idents;
	private List<String> thinks;
	private List<String> titles;
	private List<String> descriptions;
	private List<String> photosUUID;
	private List<Long> updated;
	private List<Long> trashed;
	
	private MultipartFile[] files;
	
	private boolean updateThink = false;
	
	public MainthinkForm() {
		this.idents = new ArrayList<Long>();
		this.thinks = new ArrayList<String>();
		this.titles = new ArrayList<String>();
		this.descriptions = new ArrayList<String>();
		this.photosUUID = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getIdents() {
		return idents;
	}

	public void setIdents(List<Long> idents) {
		this.idents = idents;
	}

	public List<String> getThinks() {
		return thinks;
	}

	public void setThinks(List<String> thinks) {
		this.thinks = thinks;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
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

	public boolean isUpdateThink() {
		return updateThink;
	}

	public void setUpdateThink(boolean updateThink) {
		this.updateThink = updateThink;
	}

	@Override
	public String toString() {
		return "MainthinkForm [id=" + id + ", idents=" + idents + ", thinks=" + thinks + ", titles=" + titles
				+ ", descriptions=" + descriptions + ", photosUUID=" + photosUUID + ", updated=" + updated
				+ ", trashed=" + trashed + ", updateThink=" + updateThink + "]";
	}

}
