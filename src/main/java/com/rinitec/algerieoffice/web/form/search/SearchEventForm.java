package com.rinitec.algerieoffice.web.form.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class SearchEventForm extends SearchFilterForm {
	private static final long serialVersionUID = -6725729526864868380L;

	private boolean[] sectors = new boolean[ConstraintesForm.COUNT_SECTOR_ACTIITY];
	private String dateBegin;
	private String dateEnd;
	private int indexOpen;
	private Integer clockOpen;
	private int indexClose;
	private Integer clockClose;
	private boolean digital;
	private Integer state;
	
	public SearchEventForm() {
		super(null);
	}
	
	public SearchEventForm(final Long userId, final String token, final String keyword, final Integer sector, final Integer wilaya, final int row) {
		super(userId, token, keyword, wilaya, row);
		this.digital = false;
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

	public int getIndexOpen() {
		return indexOpen;
	}

	public void setIndexOpen(int indexOpen) {
		this.indexOpen = indexOpen;
	}

	public Integer getClockOpen() {
		return clockOpen;
	}

	public void setClockOpen(Integer clockOpen) {
		this.clockOpen = clockOpen;
	}

	public int getIndexClose() {
		return indexClose;
	}

	public void setIndexClose(int indexClose) {
		this.indexClose = indexClose;
	}

	public Integer getClockClose() {
		return clockClose;
	}

	public void setClockClose(Integer clockClose) {
		this.clockClose = clockClose;
	}

	public boolean isDigital() {
		return digital;
	}

	public void setDigital(boolean digital) {
		this.digital = digital;
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
	
	public DateTime parseDateBegin() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateBegin);
	}
	
	public DateTime parseDateEnd() {
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(dateEnd);
	}
	
	public boolean hasPresentURL() {
		return digital;
	}
	
	public boolean hasPresentCalendar() {
		return hasPresentWilayas() || !StringUtils.isEmpty(dateBegin) || clockOpen != null || clockClose != null || state != 1;
	}
	
	public boolean hasPresentFilter() {
		return hasPresentSectors() || hasPresentCalendar() || hasPresentURL();
	}
	
	public boolean hasBeginReaden() {
		return page == 1 && !hasPresentToken() && !hasPresentKeysword() && !hasPresentFilter();
	}

	@Override
	public String toString() {
		return "SearchEventForm [sectors=" + Arrays.toString(sectors) + ", dateBegin=" + dateBegin + ", dateEnd="
				+ dateEnd + ", indexOpen=" + indexOpen + ", clockOpen=" + clockOpen + ", indexClose=" + indexClose
				+ ", clockClose=" + clockClose + ", digital=" + digital + ", state=" + state + "]";
	}

}
