package com.rinitec.algerieoffice.web.modal.publics.marketplace;

import java.io.Serializable;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class MetadataSkills implements Serializable {
	private static final long serialVersionUID = -7822682655387574841L;
	
	private final Long countAll;
	private final Long countSkill;
	
	public MetadataSkills(final Long countAll, final Long countSkill) {
		this.countAll = countAll;
		this.countSkill = countSkill;
	}

	public Long getCountAll() {
		return countAll;
	}

	public Long getCountSkill() {
		return countSkill;
	}
	
	public String getFormattedAll() {
		return countAll == 0 ? "0" : ParseUtil.getFormattedValue(countAll);
	}
	
	public int getPesrsentSkill() {
		return countAll == 0 ? 0 : (int) ((countSkill * 100) / countAll);
	}

	@Override
	public String toString() {
		return "MetadataSkills [countAll=" + countAll + ", countSkill=" + countSkill + "]";
	}

}
