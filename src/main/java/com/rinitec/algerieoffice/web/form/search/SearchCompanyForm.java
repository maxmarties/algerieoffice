package com.rinitec.algerieoffice.web.form.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class SearchCompanyForm extends SearchFilterForm {
	private static final long serialVersionUID = -7744296230991784458L;
	
	private boolean[] sectors = new boolean[ConstraintesForm.COUNT_SECTOR_ACTIITY];
	private String postal;
	private boolean equalPostal;
	private String dateBegin;
	private String dateEnd;
	private int indexDate;
	private boolean[] types = new boolean[4];
	private boolean[] briefcases = new boolean[10];
	private Integer warehouse;
	private String capital;
	private int indexCapital;
	private boolean[] credits = new boolean[5];
	private boolean[] contacts = new boolean[4];
	private boolean[] digitals = new boolean[3];
	private boolean[] languages = new boolean[3];
	
	public SearchCompanyForm() {
		super(null);
	}
	
	public SearchCompanyForm(final Long userId, final String token, final String keyword, final Integer wilaya, final int row) {
		super(userId, token, keyword, wilaya, row);
	}

	public boolean[] getSectors() {
		return sectors;
	}

	public void setSectors(boolean[] sectors) {
		this.sectors = sectors;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public boolean isEqualPostal() {
		return equalPostal;
	}

	public void setEqualPostal(boolean equalPostal) {
		this.equalPostal = equalPostal;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getIndexDate() {
		return indexDate;
	}

	public void setIndexDate(int indexDate) {
		this.indexDate = indexDate;
	}

	public boolean[] getTypes() {
		return types;
	}

	public void setTypes(boolean[] types) {
		this.types = types;
	}

	public boolean[] getBriefcases() {
		return briefcases;
	}

	public void setBriefcases(boolean[] briefcases) {
		this.briefcases = briefcases;
	}

	public Integer getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Integer warehouse) {
		this.warehouse = warehouse;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public int getIndexCapital() {
		return indexCapital;
	}

	public void setIndexCapital(int indexCapital) {
		this.indexCapital = indexCapital;
	}

	public boolean[] getCredits() {
		return credits;
	}

	public void setCredits(boolean[] credits) {
		this.credits = credits;
	}

	public boolean[] getContacts() {
		return contacts;
	}

	public void setContacts(boolean[] contacts) {
		this.contacts = contacts;
	}

	public boolean[] getDigitals() {
		return digitals;
	}

	public void setDigitals(boolean[] digitals) {
		this.digitals = digitals;
	}

	public boolean[] getLanguages() {
		return languages;
	}

	public void setLanguages(boolean[] languages) {
		this.languages = languages;
	}
	
	public boolean hasPresentSectors() {
		for (final boolean sector : sectors) {
			if(sector) return true;
		}
		return false;
	}
	
	public List<Integer> parseSectors() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < sectors.length; i++) {
			if(sectors[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public DateTime parseDateBegin() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateBegin);
	}
	
	public DateTime parseDateEnd() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateEnd);
	}
	
	public boolean hasPresentTypes() {
		for (final boolean type : types) {
			if(type) return true;
		}
		return false;
	}
	
	public List<Integer> parseTypes() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < types.length; i++) {
			if(types[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public boolean hasPresentBriefcases() {
		for (final boolean briefcase : briefcases) {
			if(briefcase) return true;
		}
		return false;
	}
	
	public List<Integer> parseBriefcases() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < briefcases.length; i++) {
			if(briefcases[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public Integer parseCapital() {
		return Integer.valueOf(capital);
	}
	
	public boolean hasPresentCredits() {
		for (final boolean credit : credits) {
			if(credit) return true;
		}
		return false;
	}
	
	public boolean hasPresentContacts() {
		return contacts[2] || contacts[3];
	}
	
	public boolean hasPresentDigitals() {
		for (final boolean digital : digitals) {
			if(digital) return true;
		}
		return false;
	}
	
	public boolean hasPresentWebsites() {
		return digitals[0];
	}
	
	public boolean hasPresentSocials() {
		return digitals[1];
	}
	
	public boolean hasPresentCarte() {
		return digitals[2];
	}
	
	public boolean hasPresentLanguages() {
		for (final boolean language : languages) {
			if(language) return true;
		}
		return false;
	}
	
	public boolean hasPresentCompanyAddress() {
		return hasPresentWilayas() || !StringUtils.isEmpty(postal);
	}
	
	public boolean hasPresentCompanyBriefcase() {
		return hasPresentTypes() || hasPresentBriefcases() || (warehouse != null && warehouse < 3) || !StringUtils.isEmpty(capital);
	}
	
	public boolean hasPresentFilter() {
		return hasPresentSectors() || hasPresentCompanyAddress() || !StringUtils.isEmpty(dateBegin) || hasPresentCompanyBriefcase() 
				|| hasPresentCredits() || hasPresentContacts() || hasPresentDigitals() || hasPresentLanguages() 
				|| (warehouse != null && warehouse == 3);
	}
	
	public boolean hasBeginReaden() {
		return userId == null && page == 1 && !hasPresentToken() && !hasPresentKeysword() && !hasPresentWilayas() && !hasPresentFilter();
	}

	@Override
	public String toString() {
		return "SearchCompanyForm [sectors=" + Arrays.toString(sectors) + ", postal=" + postal + ", equalPostal="
				+ equalPostal + ", dateBegin=" + dateBegin + ", dateEnd=" + dateEnd + ", indexDate=" + indexDate
				+ ", types=" + Arrays.toString(types) + ", briefcases=" + Arrays.toString(briefcases) + ", warehouse="
				+ warehouse + ", capital=" + capital + ", indexCapital=" + indexCapital + ", credits="
				+ Arrays.toString(credits) + ", contacts=" + Arrays.toString(contacts) + ", digitals="
				+ Arrays.toString(digitals) + ", languages=" + Arrays.toString(languages) + "]";
	}

}
