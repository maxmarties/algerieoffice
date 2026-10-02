package com.rinitec.algerieoffice.web.form.user.setting;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class CookiesForm implements Serializable {
	private static final long serialVersionUID = -7121609968421461670L;
	
	private boolean[] cookies = new boolean[8];
	
	public CookiesForm() {
		for (int i = 0; i < 8; i++) {
			this.cookies[i] = true;
		}
	}
	
	public CookiesForm(final String builder) {
		this();
		if(!StringUtils.isEmpty(builder)) {
			try {
				for (int i = 0; i < 8; i++) {
					this.cookies[i] = (builder.charAt(i) == '1');
				}
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				System.out.println("Catched in cookies config");
			} 
		}
	}
	
	public boolean[] getCookies() {
		return cookies;
	}
	
	public void setCookies(boolean[] cookies) {
		this.cookies = cookies;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			builder.append(cookies[i] ? "1" : "0");
		}
		return builder.toString();
	}

}
