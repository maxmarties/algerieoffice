package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyShedule;
import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.DayShedule;
import com.rinitec.algerieoffice.utils.ParseUtil;

public class ExplorerCompanyShedule implements Serializable {
	private static final long serialVersionUID = -6329768400357597743L;
	
	private final String fax;
	private final String mobile;
	private final Integer[] stateday = new Integer[7];
	private final Integer[][] timeday = new Integer[7][4];
	
	public ExplorerCompanyShedule(final CompanyShedule companyShedule) {
		if(companyShedule != null) {
			this.fax = !StringUtils.isEmpty(companyShedule.getFax()) ? companyShedule.getFax() : null;
			this.mobile = !StringUtils.isEmpty(companyShedule.getMobile()) ? companyShedule.getMobile() : null;
			final List<DayShedule> days = new ArrayList<DayShedule>(companyShedule.getDays());
			for(int i = 0; i < 7; i++) {
				this.stateday[i] = days.get(i).getStateday() == null ? 0 : days.get(i).getStateday() ? 1 : 2;
				for(int j = 0; j < 4; j++) {
					this.timeday[i][j] = days.get(i).getTimeIndex(j);
				}
			}
		} else {
			this.fax = this.mobile = null;
			for(int i = 0; i < 7; i++) {
				this.stateday[i] = 0;
			}
		}
	}
	
	public String getFax() {
		return fax;
	}

	public String getMobile() {
		return mobile;
	}

	public Integer[] getStateday() {
		return stateday;
	}

	public Integer[][] getTimeday() {
		return timeday;
	}
	
	private final String getFormatClock(final int day, final int index, final String format) {
		return (timeday[day][index] == 24 ? "00" : timeday[day][index] < 10 ? "0".concat(String.valueOf(timeday[day][index])) 
				: String.valueOf(timeday[day][index])).concat(format);
	}
	
	private final String getFormatDay(final int day, final int index) {
		return getFormatClock(day, index, ":00").concat(" - ").concat(getFormatClock(day, index + 1, ":00"));
	}
	
	public String getFormattedTime(int day) {
		switch(stateday[day]) {
		case 1: return getFormatDay(day, 0);
		case 2: return getFormatDay(day, 0).concat(" & ").concat(getFormatDay(day, 2));
		}
		return "";
	}
	
	public String getFormattedDay(int day) {
		switch(stateday[day]) {
		case 1: return getFormatDay(day, 0);
		case 2: return getFormatClock(day, 0, ":00").concat(" - ").concat(getFormatClock(day, 3, ":00"));
		}
		return "";
	}
	
	public boolean isEmptyShedule() {
		for(int i = 0; i < 7; i++) {
			if(stateday[i] != 0) return false;
		}
		return true;
	}
	
	public String getFormattedMobile() {
		return "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(mobile));
	}
	
	public String getFormattedFax() {
		return "+213".concat(" (0) ").concat(ParseUtil.getFormattedCapital(fax));
	}
	
	public String closedClock() {
		final int curr = ParseUtil.getCurrDay() - 1;
		if(stateday[curr] != 0) {
			return getFormatClock(curr, stateday[curr] == 1 ? 1 : 3, "h00");
		}
		return null;
	}
	
	public boolean openedState() {
		final int curr = ParseUtil.getCurrDay() - 1;
		if(stateday[curr] != 0) {
			final int clock = ParseUtil.getCurrClock();
			return (clock >= timeday[curr][0] && clock <= timeday[curr][1]) 
					|| (stateday[curr] == 2 && clock >= timeday[curr][2] && clock <= timeday[curr][3]);
		}
		return false;
	}
	
	public String currdayClock() {
		final int curr = ParseUtil.getCurrDay() - 1;
		if(stateday[curr] != 0) {
			switch(stateday[curr]) {
			case 1: return getFormatDay(curr, 0);
			case 2: return getFormatClock(curr, 0, ":00").concat(" - ").concat(getFormatClock(curr, 3, ":00"));
			}
		}
		return null;
	}
	
	public boolean hasPresentShedule() {
		for(int i = 0; i < 7; i++) {
			if(stateday[i] != 0) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ExplorerWidgetSedule [fax=" + fax + ", mobile=" + mobile + ", stateday=" + Arrays.toString(stateday)
				+ ", timeday=" + Arrays.toString(timeday) + "]";
	}

}
