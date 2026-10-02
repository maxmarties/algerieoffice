package com.rinitec.algerieoffice.web.form.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class SearchAnnonceForm extends SearchFilterForm {
	private static final long serialVersionUID = 7723280328280788513L;
	
	private boolean[] sectors = new boolean[ConstraintesForm.COUNT_SECTOR_ACTIITY];
	private String dateOpenBegin;
	private String dateOpenEnd;
	private String dateCloseBegin;
	private String dateCloseEnd;
	private boolean[] types = new boolean[5];
	private boolean[] visibilities = new boolean[5];
	private boolean[] digitals = new boolean[2];
	private Integer state;
	
	public SearchAnnonceForm() {
		super(null);
	}
	
	public SearchAnnonceForm(final Long userId, final String token, final String keyword, final Integer sector, final Integer wilaya, final int row) {
		super(userId, token, keyword, wilaya, row);
		this.state = 1;
		if(sector != null && sector != 0) {
			this.sectors[sector - 1] = true;
		}
	}

	public boolean[] getSectors() {
		return sectors;
	}

	public void setSectors(boolean[] sectors) {
		this.sectors = sectors;
	}

	public String getDateOpenBegin() {
		return dateOpenBegin;
	}

	public void setDateOpenBegin(String dateOpenBegin) {
		this.dateOpenBegin = dateOpenBegin;
	}

	public String getDateOpenEnd() {
		return dateOpenEnd;
	}

	public void setDateOpenEnd(String dateOpenEnd) {
		this.dateOpenEnd = dateOpenEnd;
	}

	public String getDateCloseBegin() {
		return dateCloseBegin;
	}

	public void setDateCloseBegin(String dateCloseBegin) {
		this.dateCloseBegin = dateCloseBegin;
	}

	public String getDateCloseEnd() {
		return dateCloseEnd;
	}

	public void setDateCloseEnd(String dateCloseEnd) {
		this.dateCloseEnd = dateCloseEnd;
	}

	public boolean[] getTypes() {
		return types;
	}

	public void setTypes(boolean[] types) {
		this.types = types;
	}

	public boolean[] getVisibilities() {
		return visibilities;
	}

	public void setVisibilities(boolean[] visibilities) {
		this.visibilities = visibilities;
	}

	public boolean[] getDigitals() {
		return digitals;
	}

	public void setDigitals(boolean[] digitals) {
		this.digitals = digitals;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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
	
	public DateTime parseDateOpenBegin() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateOpenBegin);
	}
	
	public DateTime parseDateOpenEnd() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateOpenEnd);
	}
	
	public DateTime parseDateCloseBegin() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateCloseBegin);
	}
	
	public DateTime parseDateCloseEnd() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateCloseEnd);
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
	
	public boolean hasPresentVisibilities() {
		for (final boolean visibility : visibilities) {
			if(visibility) return true;
		}
		return false;
	}
	
	public List<Integer> parseVisibilities() {
		final List<Integer> lines = new ArrayList<Integer>();
		for(int i = 0; i < visibilities.length; i++) {
			if(visibilities[i]) lines.add(i + 1);
		}
		return lines;
	}
	
	public boolean hasPresentURL() {
		return digitals[0];
	}
	
	public boolean hasPresentFile() {
		return digitals[1];
	}
	
	public boolean hasPresentDetail() {
		return hasPresentVisibilities() || hasPresentURL() || hasPresentFile();
	}
	
	public boolean hasPresentFilter() {
		return hasPresentSectors() || hasPresentWilayas() || !StringUtils.isEmpty(dateOpenBegin) || !StringUtils.isEmpty(dateCloseBegin) 
				|| hasPresentTypes() || hasPresentDetail() || state != 1;
	}
	
	public boolean hasBeginReaden() {
		return page == 1 && !hasPresentToken() && !hasPresentKeysword() && !hasPresentFilter();
	}

	@Override
	public String toString() {
		return "SearchAnnonceForm [sectors=" + Arrays.toString(sectors) + ", dateOpenBegin=" + dateOpenBegin
				+ ", dateOpenEnd=" + dateOpenEnd + ", dateCloseBegin=" + dateCloseBegin + ", dateCloseEnd="
				+ dateCloseEnd + ", types=" + Arrays.toString(types) + ", visibilities=" + Arrays.toString(visibilities)
				+ ", digitals=" + Arrays.toString(digitals) + ", state=" + state + "]";
	}

}
