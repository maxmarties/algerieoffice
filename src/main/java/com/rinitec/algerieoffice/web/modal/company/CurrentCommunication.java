package com.rinitec.algerieoffice.web.modal.company;

import java.io.Serializable;
import java.util.Arrays;

public class CurrentCommunication implements Serializable {
	private static final long serialVersionUID = 4725878658032880708L;
	
	private final Integer[] indicators = new Integer[6];
	
	public CurrentCommunication(final Long notice, final Long contact, final Long appoint, final Long collaborator, final Long partner, final Long chatbot) {
		this.indicators[0] = notice != 0L ? notice > 99L ? 99 : notice.intValue() : null;
		this.indicators[1] = contact != 0L ? contact > 99L ? 99 : contact.intValue() : null;
		this.indicators[2] = appoint != 0L ? appoint > 99L ? 99 : appoint.intValue() : null;
		this.indicators[3] = collaborator != 0L ? collaborator > 99L ? 99 : collaborator.intValue() : null;
		this.indicators[4] = partner != 0L ? partner > 99L ? 99 : partner.intValue() : null;
		this.indicators[5] = chatbot != 0L ? chatbot > 99L ? 99 : chatbot.intValue() : null;
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
	
	public Integer getCommunication(int index) {
		switch(index) {
		case 1: return indicators[0];
		case 2: return indicators[1];
		case 4: return indicators[2];
		case 5: return indicators[3];
		case 6: return indicators[4];
		case 7: return indicators[5];
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
		return "CurrentCommunication [indicators=" + Arrays.toString(indicators) + "]";
	}

}
