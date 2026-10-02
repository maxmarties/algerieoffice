package com.rinitec.algerieoffice.web.modal.company;

import java.io.Serializable;
import java.util.Arrays;

public class CurrentProspect implements Serializable {
	private static final long serialVersionUID = 7970688588782719663L;
	
	private final Integer[] indicators = new Integer[4];
	
	public CurrentProspect(final Long post, final Long annonce, final Long event, final Long employe) {
		this.indicators[0] = post != 0L ? post > 99L ? 99 : post.intValue() : null;
		this.indicators[1] = annonce != 0L ? annonce > 99L ? 99 : annonce.intValue() : null;
		this.indicators[2] = event != 0L ? event > 99L ? 99 : event.intValue() : null;
		this.indicators[3] = employe != 0L ? employe > 99L ? 99 : employe.intValue() : null;
	}
	
	public Integer[] getIndicators() {
		return indicators;
	}

	public boolean hasPresent() {
		for (final Integer indicator : indicators) {
			if(indicator != null && indicator > 0) {
				return true; 
			}
		}
		return false;
	}
	
	public Integer getProspect(int index) {
		switch(index) {
		case 1: return indicators[0];
		case 2: return indicators[1];
		case 3: return indicators[2];
		case 4: return indicators[3];
		}
		return null;
	}
	
	public int parseSum() {
		int sum = 0;
		for (final Integer indicator : indicators) {
			if(indicator != null && indicator > 0) {
				sum += indicator;
			}
		}
		return sum;
	}

	@Override
	public String toString() {
		return "CurrentProspect [indicators=" + Arrays.toString(indicators) + "]";
	}

}
