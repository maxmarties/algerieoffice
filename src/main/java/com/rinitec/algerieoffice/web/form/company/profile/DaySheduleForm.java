package com.rinitec.algerieoffice.web.form.company.profile;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import com.rinitec.algerieoffice.persistence.modal.companymaps.profile.DayShedule;

public class DaySheduleForm implements Serializable {
	private static final long serialVersionUID = 3503676559338322981L;

	@NotNull(message = "{message.input.required}")
	private Integer stateday;
	
	private Integer[] timeday = new Integer[4];
	
	public DaySheduleForm() {
	}

	public Integer getStateday() {
		return stateday;
	}

	public void setStateday(Integer stateday) {
		this.stateday = stateday;
	}
	
	public Integer[] getTimeday() {
		return timeday;
	}
	
	public void setTimeday(Integer[] timeday) {
		this.timeday = timeday;
	}
	
	public void initDayShedule() {
		this.stateday = 0;
		this.timeday[0] = 8;
		this.timeday[1] = 12;
		this.timeday[2] = 14;
		this.timeday[3] = 18;
	}
	
	public void parseDayShedule(final DayShedule dayShedule) {
		this.stateday = dayShedule.getStateday() == null ? 0 : dayShedule.getStateday() ? 1 : 2;
		this.timeday[0] = dayShedule.getTimeone() == null ? 8 : dayShedule.getTimeone();
		this.timeday[1] = dayShedule.getTimetho() == null ? 12 : dayShedule.getTimetho();
		this.timeday[2] = dayShedule.getTimetree() == null ? 14 : dayShedule.getTimetree();
		this.timeday[3] = dayShedule.getTimefour() == null ? 18 : dayShedule.getTimefour();
	}
	
	private String getFormatDay(int day) {
		return (timeday[day] == 24 ? "00" : timeday[day] < 10 ? "0".concat(String.valueOf(timeday[day])) 
				: String.valueOf(timeday[day])).concat(":00 - ").concat(timeday[day + 1] == 24 ? "00" 
						: timeday[day + 1] < 10 ? "0".concat(String.valueOf(timeday[day + 1])) : String.valueOf(timeday[day + 1])).concat(":00");
	}
	
	public String getFormattedTime() {
		switch(stateday) {
		case 1: return getFormatDay(0);
		case 2: return getFormatDay(0).concat(" & ").concat(getFormatDay(2));
		}
		return "";
	}

	@Override
	public String toString() {
		return "DaySheduleForm [stateday=" + stateday + ", timeday=" + Arrays.toString(timeday) + "]";
	}
	
}
