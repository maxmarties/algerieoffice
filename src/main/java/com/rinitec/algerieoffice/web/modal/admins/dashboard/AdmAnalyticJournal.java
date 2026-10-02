package com.rinitec.algerieoffice.web.modal.admins.dashboard;

import java.io.Serializable;
import java.util.Arrays;

import com.rinitec.algerieoffice.utils.ParseUtil;

public class AdmAnalyticJournal implements Serializable {
	private static final long serialVersionUID = 5336566727110836068L;
	
	private final Long[] countUser;
	private final Long[] countCompany;
	private final Long[] countAdmin;
	private final Long[] countGuest;
	
	public AdmAnalyticJournal(final Long[] countUser, final Long[] countCompany, final Long[] countAdmin, final Long[] countGuest) {
		this.countUser = countUser;
		this.countCompany = countCompany;
		this.countAdmin = countAdmin;
		this.countGuest = countGuest;
	}

	public Long[] getCountUser() {
		return countUser;
	}

	public Long[] getCountCompany() {
		return countCompany;
	}

	public Long[] getCountAdmin() {
		return countAdmin;
	}
	
	public Long[] getCountGuest() {
		return countGuest;
	}
	
	public String countUserToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countUser.length; i++) {
			builder.append(String.valueOf(countUser[i]));
			if(i < countUser.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countCompanyToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countCompany.length; i++) {
			builder.append(String.valueOf(countCompany[i]));
			if(i < countCompany.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countAdminToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countAdmin.length; i++) {
			builder.append(String.valueOf(countAdmin[i]));
			if(i < countAdmin.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String countGuestToString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < countGuest.length; i++) {
			builder.append(String.valueOf(countGuest[i]));
			if(i < countGuest.length - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public String getFormattedSumUser() {
		long sum = 0L;
		for (int i = 0; i < countUser.length; i++) {
			sum += countUser[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumCompany() {
		long sum = 0L;
		for (int i = 0; i < countCompany.length; i++) {
			sum += countCompany[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumAdmin() {
		long sum = 0L;
		for (int i = 0; i < countAdmin.length; i++) {
			sum += countAdmin[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}
	
	public String getFormattedSumGuest() {
		long sum = 0L;
		for (int i = 0; i < countGuest.length; i++) {
			sum += countGuest[i];
		}
		return ParseUtil.getFormattedValue(sum);
	}

	@Override
	public String toString() {
		return "AdmAnalyticJournal [countUser=" + Arrays.toString(countUser) + ", countCompany="
				+ Arrays.toString(countCompany) + ", countAdmin=" + Arrays.toString(countAdmin) + ", countGuest="
				+ Arrays.toString(countGuest) + "]";
	}

}
