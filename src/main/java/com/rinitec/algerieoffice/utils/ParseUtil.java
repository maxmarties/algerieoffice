package com.rinitec.algerieoffice.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.enums.AccessType;
import com.rinitec.algerieoffice.enums.DocumentType;
import com.rinitec.algerieoffice.enums.EmailType;
import com.rinitec.algerieoffice.persistence.modal.users.Role;

public class ParseUtil {
	public static final int TODAY = 1;
	private static final int HIER = 2;
	private static final int CETTE_SEMAINE = 3;
	private static final int HIER_SEMAINE = 4;
	private static final int CE_MOIS = 5;
	private static final int HIER_MOIS = 6;
	
	public static final int REGION_NORD = 1;
	public static final int REGION_OUEST = 2;
	public static final int REGION_EST = 3;
	public static final int REGION_SUD = 4;
	
	private static final Integer[] DAYS_OFWEEK = {1, 2, 3, 4, 5, 6, 7};
	
	private static final Integer[] REGIONS_NORD = {9, 10, 16, 17, 26, 28, 35, 38, 42, 44};
	private static final Integer[] REGIONS_OUEST = {2, 13, 14, 20, 22, 27, 29, 31, 46, 48};
	private static final Integer[] REGIONS_EST = {4, 5, 6, 7, 12, 15, 18, 19, 21, 23, 24, 25, 34, 36, 40, 41, 43};
	private static final Integer[] REGIONS_SUD = {1, 3, 8, 11, 30, 32, 33, 37, 39, 45, 47};

	public static final List<UUID> parseLinesUUID(final List<String> lines) throws IllegalArgumentException {
		final List<UUID> linesUUID = new ArrayList<UUID>();
		for (final String line : lines) {
			linesUUID.add(UUID.fromString(line));
		}
		return linesUUID;
	}
	
	public static final String getFormattedOrder(final Integer order) {
		if(order != null) {
			return String.format(Locale.FRENCH, "%,d", order);
		}
		return "-";
	}
	
	public static final String getFormattedCount(final Long count) {
		if(count != null) {
			return String.format(Locale.FRENCH, "%,d", count);
		}
		return "-";
	}
	
	public static final String getFormattedAvg(final Double avg) {
		if(avg != null) {
			return String.format(Locale.FRENCH, "%,.0f", avg);
		}
		return "-";
	}
	
	public static final String getFormattedFloat(final Double value) {
		if(value != null) {
			return String.format(Locale.FRENCH, "%,.2f", value);
		}
		return "-";
	}
	
	private static final String getFormattedValue(final String format) {
		final String[] _value = format.split(String.valueOf((char) 160));
		switch(_value.length) {
		case 1: case 2: return format;
		case 3: return _value[0].concat(" ").concat(_value[1]).concat(" K");
		case 4: return _value[0].concat(" ").concat(_value[1]).concat(" M");
		default: 
			final StringBuilder builder = new StringBuilder();
			for (int i = 0; i < _value.length - 2; i++) {
				builder.append( _value[i]);
				builder.append(" ");
			}
			builder.append("M");
			return builder.toString();
		}
	}
	
	public static final String getFormattedValue(final Double avg) {
		if(avg != null) {
			return getFormattedValue(String.format(Locale.FRENCH, "%,.0f", avg));
		}
		return "0";
	}
	
	public static final String getFormattedValue(final Long count) {
		if(count != null) {
			return getFormattedValue(String.format(Locale.FRENCH, "%,d", count));
		}
		return "0";
	}
	
	private static final String parseTopicValue(final int index) {
		switch(index) {
		case 1: return "";
		case 2: return "K";
		case 3: return "M";
		case 4: return "Md";
		default: return "xx";
		}
	}
	
	private static final String getFormattedTopicValue(final String format) {
		final String[] _value = format.split(String.valueOf((char) 160));
		switch(_value.length) {
		case 1: return format;
		default: 
			final String _parser = _value[1].substring(0, 1);
			return _value[0].concat(_parser.equals("0") ? "": ".".concat(_parser)).concat(parseTopicValue(_value.length));
		}
	}
	
	public static final String getFormattedTopicValue(final Long count) {
		if(count != null) {
			return getFormattedTopicValue(String.format(Locale.FRENCH, "%,d", count));
		}
		return "0";
	}
	
	public static final String getFormattedTopicValue(final Integer count) {
		if(count != null) {
			return getFormattedTopicValue(String.format(Locale.FRENCH, "%,d", count));
		}
		return "-";
	}
	
	public static final String getFormattedCapital(final String capital) {
		if(!StringUtils.isEmpty(capital)) {
			try {
				return String.format(Locale.FRENCH, "%,d", Long.valueOf(capital));
			} catch (IllegalFormatException e) {}
		}
		return "-";
	}
	
	public static final String getFormattedPhone(final String phone) {
		if(StringUtils.isEmpty(phone)) {
			return "-";
		}
		String format = phone.length() == 9 ? phone.substring(0, 3) : phone.substring(0, 2);
		for(int i = phone.length() == 9 ? 3 : 2; i < phone.length(); i += 2) {
			format += " ".concat(phone.substring(i, i + 2));
		}
		return format;
	}
	
	public static final String getRoleName(final Integer role) {
		switch(role) {
		case 1: return "ROLE_COMPANY_VISIT";
		case 2: return "ROLE_COMPANY_AUTOR";
		case 3: return "ROLE_COMPANY_EDIT";
		case 4: return "ROLE_COMPANY_MANAGER";
		}
		return "ROLE_COMPANY_ADMIN";
	}
	
	public static final String getRoleAdmin(final Integer role) {
		switch(role) {
		case 1: return "ROLE_SUPPORT_AUTOR";
		case 2: return "ROLE_SUPPORT_MANAGER";
		}
		return "ROLE_SUPPORT_ADMIN";
	}
	
	public static final boolean hasRoleAdmin(final Collection<Role> roles) {
		for (final Role role : roles) {
			if(role.getName().equals("ROLE_SUPPORT_ADMIN")) {
				return true;
			}
		}
		return false;
	}
	
	public static final Integer getRoleAdminIndex(final Collection<Role> roles) {
		for (final Role role : roles) {
			if(!role.getName().equals("ROLE_ACCOUNT")) {
				switch(role.getName().split("_")[2]) {
				case "AUTOR": return 1;
				case "MANAGER": return 2;
				}
			}
		}
		return 3;
	}
	
	public static final String getRoleMessage(final Collection<Role> roles) {
		for (final Role role : roles) {
			if(!role.getName().equals("ROLE_ACCOUNT")) {
				return role.getName().split("_")[2].toLowerCase();
			}
		}
		return "admin";
	}
	
	public static final Integer getRoleIndex(final Collection<Role> roles) {
		for (final Role role : roles) {
			if(!role.getName().equals("ROLE_ACCOUNT")) {
				switch(role.getName().split("_")[2]) {
				case "VISIT": return 1;
				case "AUTOR": return 2;
				case "EDIT": return 3;
				case "MANAGER": return 4;
				}
			}
		}
		return 5;
	}
	
	public static List<Integer> getRegionList(final int region) {
		switch(region) {
		case REGION_NORD : return Arrays.asList(REGIONS_NORD);
		case REGION_OUEST : return Arrays.asList(REGIONS_OUEST);
		case REGION_EST : return Arrays.asList(REGIONS_EST);
		case REGION_SUD : return Arrays.asList(REGIONS_SUD);
		}
		return null;
	}
	
	public static DocumentType parseDocumentType(final int type) {
		switch(type) {
		case 1: return DocumentType.post;
		case 2: return DocumentType.annonce;
		case 3: return DocumentType.event;
		default: return DocumentType.employe;
		}
	}
	
	public static int parseTypeDocument(final DocumentType type) {
		switch(type) {
		case post: return 1;
		case annonce: return 2;
		case event: return 3;
		default: return 4;
		}
	}
	
	public static EmailType parseEmailType(final DocumentType type) {
		switch(type) {
		case post: return EmailType.quotes;
		case annonce: return EmailType.ads;
		case event: return EmailType.infos;
		default: return EmailType.jobs;
		}
	}
	
	public static AccessType parseAccessType(int type) {
		switch(type) {
		case 1: return AccessType.home;
		case 2: return AccessType.society;
		case 3: return AccessType.history;
		case 4: return AccessType.actus;
		case 5: return AccessType.events;
		case 6: return AccessType.works;
		case 7: return AccessType.faqs;
		case 8: return AccessType.partners;
		case 9: return AccessType.posts;
		case 10: return AccessType.market;
		case 11: return AccessType.employe;
		default: return AccessType.contact;
		}
	}
	
	public static final String getLastYearsToString(int sub) {
		final StringBuilder builder = new StringBuilder();
		int year = getCurrYear() - (sub - 1);
		for(int i = 0; i < sub; i++) {
			builder.append(String.valueOf(year++));
			if(i < sub - 1) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public static final int getCurrClock() {
		return Integer.valueOf(DateTimeFormat.forPattern("HH").print(new DateTime(Date.from(Instant.now()))));
	}
	
	public static final int getCurrDay() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public static final int getCurrYear() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		return calendar.get(Calendar.YEAR);
	}
	
	public static final int[] parseWeeksubDayForNow() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
		int[] days = new int[7];
		for(int i = 0; i < 7; i++) {
			days[i] = dayWeek > 6 ? DAYS_OFWEEK[dayWeek - 7] : DAYS_OFWEEK[dayWeek];
			dayWeek++;
		}
		return days;
	}
	
	public static final int[] parseSixsubMonthForNow() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		int subsixMonth = calendar.get(Calendar.MONTH) - 5;
		int[] months = new int[6];
		for(int i = 0; i < 6; i++) {
			calendar.set(Calendar.MONTH, subsixMonth++);
			months[i] = calendar.get(Calendar.MONTH) + 1;
		}
		return months;
	}
	
	private static final DateTime getStartDate(final DateTime dateTime) {
		final String _format = DateTimeFormat.forPattern("dd/MM/yyyy").print(dateTime);
		return DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(_format.concat(" 00:00:00"));
	}
	
	private static final DateTime getAddDay(final int day) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.add(Calendar.DATE, day);
		return new DateTime(calendar.getTime());
	}
	
	private static final DateTime getAddMonth(final int month) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.add(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return new DateTime(calendar.getTime());
	}
	
	private static final DateTime getBeginDateCurrMonth() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.set(Calendar.DATE, 1);
		return new DateTime(calendar.getTime());
	}
	
	public static final DateTime getBeginDate(final int filterDate) {
		final DateTime now = new DateTime(Date.from(Instant.now()));
		switch(filterDate) {
		case HIER: return getStartDate(getAddDay(-1));
		case CETTE_SEMAINE: return getStartDate(getAddDay(-7));
		case HIER_SEMAINE: return getStartDate(getAddDay(-14));
		case CE_MOIS: return getStartDate(getBeginDateCurrMonth());
		case HIER_MOIS: return getStartDate(getAddMonth(-1));
		}
		return getStartDate(now);
	}
	
	public static final DateTime getEndDate(final int filterDate) {
		final DateTime now = new DateTime(Date.from(Instant.now()));
		switch(filterDate) {
		case HIER: return getStartDate(now);
		case CETTE_SEMAINE: return getStartDate(getAddDay(1));
		case HIER_SEMAINE: return getStartDate(getAddDay(-7));
		case CE_MOIS: return getStartDate(getAddDay(1));
		case HIER_MOIS: return getStartDate(getBeginDateCurrMonth());
		}
		return getStartDate(getAddDay(1));
	}
	
	public static final DateTime getYesterdayFromNow() {
		return getBeginDate(HIER);
	}
	
	public static final boolean hasToday(final DateTime postedDate) {
		return postedDate.isBefore(getEndDate(TODAY)) && postedDate.isAfter(getBeginDate(TODAY));
	}
	
	public static final DateTime getFourthineFromNow() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.add(Calendar.MONTH, -1);
		calendar.add(Calendar.DATE, -10);
		return new DateTime(calendar.getTime());
	}
	
	public static final String parseFormatDateMessage(final DateTime postedDate) {
		if(postedDate.isBefore(getEndDate(TODAY)) && postedDate.isAfter(getBeginDate(TODAY))) {
			return "HH:mm";
		}
		return "dd/MM/yyyy - HH:mm";
	}
	
	public static final DateTime getBeginDateFromDay(final int day) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		if(day != 0) {
			calendar.add(Calendar.DATE, -day);
		}
		return getStartDate(new DateTime(calendar.getTime()));
	}
	
	public static final DateTime getEndDateFromDay(final int day) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		if(day != 0) {
			calendar.add(Calendar.DATE, -day);
		}
		calendar.add(Calendar.DATE, 1);
		return getStartDate(new DateTime(calendar.getTime()));
	}
	
	public static final DateTime getBeginDateFromMonth(final int month) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		if(month != 0) {
			calendar.add(Calendar.MONTH, -month);
		}
		calendar.set(Calendar.DATE, 1);
		return new DateTime(calendar.getTime());
	}
	
	public static final DateTime getEndDateFromMonth(final int month) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		if(month != 0) {
			calendar.add(Calendar.MONTH, -month);
		}
		calendar.set(Calendar.DATE, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return new DateTime(calendar.getTime());
	}
	
	public static final DateTime getBeginYearDate(final int year) {
		final String _format = "01/01/".concat(String.valueOf(year));
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(_format);
	}
	
	public static final DateTime getEndYearDate(final int year) {
		final String _format = "31/12/".concat(String.valueOf(year));
		return DateTimeFormat.forPattern("dd/MM/yyyy").parseDateTime(_format);
	}
	
	public static final DateTime getSubSixmonthForNow() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.add(Calendar.MONTH, -6);
		calendar.set(Calendar.DATE, 1);
		return new DateTime(calendar.getTime()); //-6Mois
	}
	
	public static final DateTime getSubYearForNow() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		calendar.add(Calendar.YEAR, -1);
		calendar.set(Calendar.DATE, 1);
		return new DateTime(calendar.getTime()); //-12Mois
	}
	
	private static final String generatePseudoName(final int lenght) {
		final String token = "abcdefghijklmnopqrstuvwxyz";
		final Random random = new Random();
		final StringBuilder builder = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {
			builder.append(token.charAt(random.nextInt(token.length())));
		}
		return builder.toString();
	}
	
	public static final String generatePseudo(final int lenght, final String username) {
		final String pseudo = username.replaceAll(" ", "\\-").toLowerCase();
		return pseudo.concat("-").concat(generatePseudoName(lenght));
	}
	
	public static final String generatePassword(final int lenght) {
		final String token = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final Random random = new Random();
		final StringBuilder builder = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {
			builder.append(token.charAt(random.nextInt(token.length())));
		}
		return builder.toString();
	}
	
	public static final String generateVerificationSMS(final int lenght) {
		final String token = "0123456789";
		final Random random = new Random();
		final StringBuilder builder = new StringBuilder(lenght);
		for (int i = 0; i < lenght; i++) {
			builder.append(token.charAt(random.nextInt(token.length())));
		}
		return builder.toString();
	}
	
	public static final String getFormattedClock(int clock) {
		switch(clock) {
		case 24: return "00:00";
		default: return (clock < 10 ? "0".concat(String.valueOf(clock)) : String.valueOf(clock)).concat(":00");
		}
	}
	
	public static final int getRandomValue(final int lenght) {
		if(lenght == 1) {
			return 1;
		}
		final Random random = new Random();
		final int value = random.nextInt(lenght + 1);
		return value <= 1 ? 2 : value == lenght ? value - 1 : value;
	}
	
	public static final int getRandomValueMod(final int lenght) {
		final int value = getRandomValue(lenght);
		return value % 2 == 0 ? value : value + 1;
	}
	
}
