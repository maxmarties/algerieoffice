package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmDashboardInbox implements Serializable {
	private static final long serialVersionUID = -4733659174146232945L;
	
	private final Long[] inboxs;
	private final Long inboxSum;
	
	public AdmDashboardInbox(final Long[] inboxs) {
		this.inboxs = inboxs;
		this.inboxSum = this.sum();
	}
	
	private final long sum() {
		long sum = 0L;
		for (final Long inbox : inboxs) {
			sum += inbox;
		}
		return sum;
	}
	
	public Long[] getInboxs() {
		return inboxs;
	}
	
	public Long getInboxSum() {
		return inboxSum;
	}
	
	public String parseInbox(int index) {
		return ParseUtil.getFormattedCount(inboxs[index - 1]);
	}
	
	public String parseSum() {
		return ParseUtil.getFormattedCount(inboxSum);
	}
	
	public int getPesrsentInbox(int index) {
		if(inboxSum == 0L) {
			return 0;
		}
		final int persent = (int) ((inboxs[index - 1] * 100) / inboxSum);
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmDashboardInbox [inboxs=" + Arrays.toString(inboxs) + ", inboxSum=" + inboxSum + "]";
	}

}
