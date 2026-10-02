package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.utils.ParseUtil;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class BriefcaseForm implements Serializable {
	private static final long serialVersionUID = -7314214261417682628L;

	@NotNull
	private Long id;
	
	@ValidChose
	private Integer briefcase;
	
	private boolean warehouse;
	private String capital;
	
	@ValidChose
	private Integer type;
	
	private String nrc;
	private String nif;
	private String nis;
	
	private List<Long> idents;
	private List<String> labels;
	private List<String> infos;
	private List<Long> updated;
	private List<Long> trashed;
	
	private boolean updateInfo = false;
	
	public BriefcaseForm() {
		this.idents = new ArrayList<Long>();
		this.labels = new ArrayList<String>();
		this.infos = new ArrayList<String>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBriefcase() {
		return briefcase;
	}

	public void setBriefcase(Integer briefcase) {
		this.briefcase = briefcase;
	}

	public boolean isWarehouse() {
		return warehouse;
	}

	public void setWarehouse(boolean warehouse) {
		this.warehouse = warehouse;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getInfos() {
		return infos;
	}

	public void setInfos(List<String> infos) {
		this.infos = infos;
	}
	
	public List<Long> getIdents() {
		return idents;
	}
	
	public void setIdents(List<Long> idents) {
		this.idents = idents;
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
	
	public boolean isUpdateInfo() {
		return updateInfo;
	}
	
	public void setUpdateInfo(boolean updateInfo) {
		this.updateInfo = updateInfo;
	}
	
	public String getFormattedCapital() {
		return ParseUtil.getFormattedCapital(capital);
	}

	@Override
	public String toString() {
		return "BriefcaseForm [id=" + id + ", briefcase=" + briefcase + ", warehouse=" + warehouse + ", capital="
				+ capital + ", type=" + type + ", nrc=" + nrc + ", nif=" + nif + ", nis=" + nis + ", idents=" + idents
				+ ", labels=" + labels + ", infos=" + infos + ", updated=" + updated + ", trashed=" + trashed
				+ ", updateInfo=" + updateInfo + "]";
	}
	
}
