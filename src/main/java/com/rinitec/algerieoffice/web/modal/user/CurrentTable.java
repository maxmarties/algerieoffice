package com.rinitec.algerieoffice.web.modal.user;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

public class CurrentTable implements Serializable {
	private static final long serialVersionUID = -854462786786628441L;
	
	private boolean[] column = new boolean[14];
	
	@NotNull
	private String cookieName;
	
	public CurrentTable() {
		for (int i = 0; i < column.length; i++) {
			this.column[i] = true;
		}
	}
	
	public CurrentTable(final String builder) {
		this();
		this.initColumn(builder);
	}
	
	public CurrentTable(final String builder, final String cookieName) {
		this();
		this.cookieName = cookieName;
		this.initColumn(builder);
	}
	
	private final void initColumn(final String builder) {
		if(!StringUtils.isEmpty(builder)) {
			try {
				for (int i = 0; i < column.length; i++) {
					this.column[i] = (builder.charAt(i) == '1');
				}
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.out.println("Catched in current table");
			}
		}
	}

	public boolean[] getColumn() {
		return column;
	}

	public void setColumn(boolean[] column) {
		this.column = column;
	}
	
	public String getCookieName() {
		return cookieName;
	}
	
	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}
	
	public String parseCookie() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < column.length; i++) {
			builder.append(column[i] ? "1" : "0");
		}
		return builder.toString();
	}

	@Override
	public String toString() {
		return "CurrentTable [column=" + Arrays.toString(column) + ", cookieName=" + cookieName + "]";
	}

}
