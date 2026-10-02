package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmDashboardLinked implements Serializable {
	private static final long serialVersionUID = 8263349701171131939L;
	
	private final Long[] linkeds;
	private final Long all;
	
	public AdmDashboardLinked(final Long[] linkeds, final Long all) {
		this.linkeds = linkeds;
		this.all = all;
	}
	
	public Long[] getLinkeds() {
		return linkeds;
	}
	
	public Long getAll() {
		return all;
	}
	
	private final long sumLinked() {
		long sum = 0L;
		for (final Long linked : linkeds) {
			sum += linked;
		}
		return sum;
	}
	
	public String parseLinked(int index) {
		return ParseUtil.getFormattedCount(linkeds[index - 1]);
	}
	
	public String parseSum() {
		return ParseUtil.getFormattedCount(this.sumLinked());
	}
	
	public int getPesrsentLinked(int index) {
		final long sum = this.sumLinked();
		if(sum == 0L) {
			return 0;
		}
		final int persent = (int) ((linkeds[index - 1] * 100) / sum);
		return persent > 100 ? 100 : persent;
	}
	
	public int getPesrsentAll() {
		if(all == 0L) {
			return 0;
		}
		final int persent = (int) ((this.sumLinked() * 100) / all);
		return persent > 100 ? 100 : persent;
	}

	@Override
	public String toString() {
		return "AdmDashboardLinked [linkeds=" + Arrays.toString(linkeds) + ", all=" + all + "]";
	}

}
