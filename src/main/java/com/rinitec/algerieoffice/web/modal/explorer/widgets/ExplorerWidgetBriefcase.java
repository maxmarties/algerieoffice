package com.rinitec.algerieoffice.web.modal.explorer.widgets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyBriefcase;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.Briefcase;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ExplorerWidgetBriefcase implements Serializable {
	private static final long serialVersionUID = -540578201890864714L;
	
	private final Integer briefcase;
	private final Integer warehouse;
	private final String capital;
	private final Integer type;
	private final String nrc;
	private final String nif;
	private final String nis;
	private final List<String> labels = new ArrayList<String>();
	private final List<String> infos = new ArrayList<String>();
	
	public ExplorerWidgetBriefcase(final CompanyBriefcase companyBriefcase) {
		if(companyBriefcase != null) {
			final List<Briefcase> briefcases = new ArrayList<Briefcase>(companyBriefcase.getBriefcases());
			this.briefcase = companyBriefcase.getBriefcase();
			this.warehouse = companyBriefcase.getWarehouse() ? 2 : 1;
			this.capital = ParseUtil.getFormattedOrder(companyBriefcase.getCapital());
			this.type = companyBriefcase.getType();
			this.nrc = StringUtils.isEmpty(companyBriefcase.getNrc()) ? "-" : companyBriefcase.getNrc();
			this.nif = ParseUtil.getFormattedCapital(companyBriefcase.getNif());
			this.nis = ParseUtil.getFormattedCapital(companyBriefcase.getNis());
			for (final Briefcase briefcase : briefcases) {
				this.labels.add(briefcase.getLabel());
				this.infos.add(briefcase.getInfo());
			}
		} else {
			this.briefcase = this.type = null;
			this.warehouse = 1;
			this.capital = this.nrc = this.nif = this.nis = "-";
		}
	}

	public Integer getBriefcase() {
		return briefcase;
	}

	public Integer getWarehouse() {
		return warehouse;
	}

	public String getCapital() {
		return capital;
	}

	public Integer getType() {
		return type;
	}

	public String getNrc() {
		return nrc;
	}

	public String getNif() {
		return nif;
	}

	public String getNis() {
		return nis;
	}

	public List<String> getLabels() {
		return labels;
	}

	public List<String> getInfos() {
		return infos;
	}
	
	public int stateBriefcase() {
		return briefcase == null || briefcase < 9 ? 1 : briefcase == 9 ? 2 : 3;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetBriefcase [briefcase=" + briefcase + ", warehouse=" + warehouse + ", capital=" + capital
				+ ", type=" + type + ", nrc=" + nrc + ", nif=" + nif + ", nis=" + nis + ", labels=" + labels
				+ ", infos=" + infos + "]";
	}

}
