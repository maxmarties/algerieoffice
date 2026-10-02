package com.rinitec.algerieoffice.web.modal;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.form.user.setting.GenralForm;

public class CurrentConfig implements Serializable {
	private static final long serialVersionUID = -8314769633506952842L;
	
	private static final int[] ROWS_RESULT = {10, 20, 50, 100};
	private static final int[] MEMBERS_RESULT = {15, 30, 60, 120};

	private int defaultStyle;
	private int defaultRow;
	private int defaultToken;
	private int defaultResult;
	private int defaultBlog;
	private boolean menuCollapse;
	private boolean panelCollapse;
	private boolean chaterCollapse;
	private boolean simultude;
	private boolean scroll;
	private boolean welcome;
	private boolean[] sounds = new boolean[4];
	private boolean started;
	private boolean newsletterCollapse;
	private boolean cookieCollapse;
	private boolean begginerCollapse;
	private boolean offerCollapse;
	private boolean identityCollapse;
	private boolean chatbotCollapse;
	private boolean chatboterCollapse;
	private boolean removeCookies;
	
	private boolean ignoreStyle = false;
	
	private void init() {
		this.defaultStyle = 1;
		this.defaultRow = 2;
		this.defaultToken = 0;
		this.defaultResult = 1;
		this.defaultBlog = 1;
		this.menuCollapse = false;
		this.panelCollapse = false;
		this.chaterCollapse = true;
		this.simultude = true;
		this.scroll = false;
		this.welcome = true;
		this.started = true;
		this.newsletterCollapse = false;
		this.cookieCollapse = false;
		this.begginerCollapse = false;
		this.offerCollapse = false;
		this.identityCollapse = false;
		this.chatbotCollapse = false;
		this.chatboterCollapse = false;
		this.removeCookies = true;
		for (int i = 0; i < 4; i++) {
			this.sounds[i] = true;
		}
	}
	
	public CurrentConfig() {
		this.init();
	}
	
	public CurrentConfig(final String builder) {
		if(!StringUtils.isEmpty(builder)) {
			try {
				final int buildStyle = Integer.valueOf(String.valueOf(builder.charAt(0)));
				final int buildRow = Integer.valueOf(String.valueOf(builder.charAt(1)));
				final int buildToken = Integer.valueOf(String.valueOf(builder.charAt(2)));
				final int buildResult = Integer.valueOf(String.valueOf(builder.charAt(3)));
				final int buildBlog = Integer.valueOf(String.valueOf(builder.charAt(4)));
				this.defaultStyle = buildStyle >= 1 && buildStyle <= 3 ? buildStyle : 1;
				this.defaultRow = buildRow >= 0 && buildRow <= 4 ? buildRow : 2;
				this.defaultToken = buildToken >= 0 && buildToken <= 3 ? buildToken : 0;
				this.defaultResult = buildResult >= 0 && buildResult <= 3 ? buildResult : 1;
				this.defaultBlog = buildBlog >= 0 && buildBlog <= 3 ? buildBlog : 1;
				this.menuCollapse = (builder.charAt(5) == '1');
				this.panelCollapse = (builder.charAt(6) == '1');
				this.chaterCollapse = (builder.charAt(7) == '1');
				this.simultude = (builder.charAt(8) == '1');
				this.scroll = (builder.charAt(9) == '1');
				this.welcome = (builder.charAt(10) == '1');
				for (int i = 0; i < 4; i++) {
					this.sounds[i] = (builder.charAt(11 + i) == '1');
				}
				this.started = (builder.charAt(15) == '1');
				this.newsletterCollapse = (builder.charAt(16) == '1');
				this.cookieCollapse = (builder.charAt(17) == '1');
				this.begginerCollapse = (builder.charAt(18) == '1');
				this.offerCollapse = (builder.charAt(19) == '1');
				this.identityCollapse = (builder.charAt(20) == '1');
				this.chatbotCollapse = (builder.charAt(21) == '1');
				this.chatboterCollapse = (builder.charAt(22) == '1');
				this.removeCookies = (builder.charAt(23) == '1');
			} catch (Exception e) {
				this.init();
				System.out.println("Catched in current config");
			} 
		} else this.init();
	}
	
	public CurrentConfig(final String builder, final boolean ignoreStyle) {
		this(builder);
		this.ignoreStyle = ignoreStyle;
	}
	
	public int getDefaultStyle() {
		return defaultStyle;
	}
	
	public void setDefaultStyle(int defaultStyle) {
		this.defaultStyle = defaultStyle;
	}
	
	public int getDefaultRow() {
		return defaultRow;
	}
	
	public void setDefaultRow(int defaultRow) {
		this.defaultRow = defaultRow;
	}
	
	public int getDefaultToken() {
		return defaultToken;
	}
	
	public void setDefaultToken(int defaultToken) {
		this.defaultToken = defaultToken;
	}
	
	public int getDefaultResult() {
		return defaultResult;
	}
	
	public void setDefaultResult(int defaultResult) {
		this.defaultResult = defaultResult;
	}
	
	public int getDefaultBlog() {
		return defaultBlog;
	}
	
	public void setDefaultBlog(int defaultBlog) {
		this.defaultBlog = defaultBlog;
	}

	public boolean isMenuCollapse() {
		return menuCollapse;
	}
	
	public void setMenuCollapse(boolean menuCollapse) {
		this.menuCollapse = menuCollapse;
	}

	public boolean isPanelCollapse() {
		return panelCollapse;
	}
	
	public void setPanelCollapse(boolean panelCollapse) {
		this.panelCollapse = panelCollapse;
	}
	
	public boolean isChaterCollapse() {
		return chaterCollapse;
	}
	
	public void setChaterCollapse(boolean chaterCollapse) {
		this.chaterCollapse = chaterCollapse;
	}
	
	public boolean isSimultude() {
		return simultude;
	}
	
	public void setSimultude(boolean simultude) {
		this.simultude = simultude;
	}
	
	public boolean isScroll() {
		return scroll;
	}
	
	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}
	
	public boolean isWelcome() {
		return welcome;
	}
	
	public void setWelcome(boolean welcome) {
		this.welcome = welcome;
	}
	
	public boolean[] getSounds() {
		return sounds;
	}
	
	public void setSounds(boolean[] sounds) {
		this.sounds = sounds;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public boolean isIgnoreStyle() {
		return ignoreStyle;
	}
	
	public void setIgnoreStyle(boolean ignoreStyle) {
		this.ignoreStyle = ignoreStyle;
	}
	
	public boolean isNewsletterCollapse() {
		return newsletterCollapse;
	}
	
	public void setNewsletterCollapse(boolean newsletterCollapse) {
		this.newsletterCollapse = newsletterCollapse;
	}
	
	public boolean isCookieCollapse() {
		return cookieCollapse;
	}
	
	public void setCookieCollapse(boolean cookieCollapse) {
		this.cookieCollapse = cookieCollapse;
	}
	
	public boolean isBegginerCollapse() {
		return begginerCollapse;
	}
	
	public void setBegginerCollapse(boolean begginerCollapse) {
		this.begginerCollapse = begginerCollapse;
	}
	
	public boolean isOfferCollapse() {
		return offerCollapse;
	}
	
	public void setOfferCollapse(boolean offerCollapse) {
		this.offerCollapse = offerCollapse;
	}
	
	public boolean isIdentityCollapse() {
		return identityCollapse;
	}
	
	public void setIdentityCollapse(boolean identityCollapse) {
		this.identityCollapse = identityCollapse;
	}
	
	public boolean isChatbotCollapse() {
		return chatbotCollapse;
	}
	
	public void setChatbotCollapse(boolean chatbotCollapse) {
		this.chatbotCollapse = chatbotCollapse;
	}
	
	public boolean isChatboterCollapse() {
		return chatboterCollapse;
	}
	
	public void setChatboterCollapse(boolean chatboterCollapse) {
		this.chatboterCollapse = chatboterCollapse;
	}
	
	public boolean isRemoveCookies() {
		return removeCookies;
	}
	
	public void setRemoveCookies(boolean removeCookies) {
		this.removeCookies = removeCookies;
	}
	
	public int parseDefaultResult() {
		return ROWS_RESULT[defaultResult];
	}
	
	public int parseDefaultBlog() {
		return ROWS_RESULT[defaultBlog];
	}
	
	public int parseDefaultMembers() {
		return MEMBERS_RESULT[defaultResult];
	}
	
	public void parseSetting(final GenralForm genralForm) {
		this.welcome = genralForm.isWelcome();
		this.defaultStyle = genralForm.getStyle();
		this.defaultToken = genralForm.getToken() - 1;
		this.defaultRow = genralForm.getRow() - 1;
		this.defaultResult = genralForm.getResult() - 1;
		this.defaultBlog = genralForm.getBlog() - 1;
		this.simultude = genralForm.isSimultude();
		this.scroll = false;
	}
	
	public void parseSounds(final boolean[] sounds) {
		for (int i = 0; i < 4; i++) {
			this.sounds[i] = sounds[i];
		}
	}
	
	public String aocolor(final int color) {
		final int index = ignoreStyle ? 0 : defaultStyle - 1;
		return ConstraintesForm.getDashboardColor(color, index);
	}

	@Override
	public String toString() {
		try {
			final StringBuilder builder = new StringBuilder();
			builder.append(String.valueOf(defaultStyle));
			builder.append(String.valueOf(defaultRow));
			builder.append(String.valueOf(defaultToken));
			builder.append(String.valueOf(defaultResult));
			builder.append(String.valueOf(defaultBlog));
			builder.append(menuCollapse ? "1" : "0");
			builder.append(panelCollapse ? "1" : "0");
			builder.append(chaterCollapse ? "1" : "0");
			builder.append(simultude ? "1" : "0");
			builder.append(scroll ? "1" : "0");
			builder.append(welcome ? "1" : "0");
			for (int i = 0; i < 4; i++) {
				builder.append(sounds[i] ? "1" : "0");
			}
			builder.append(started ? "1" : "0");
			builder.append(newsletterCollapse ? "1" : "0");
			builder.append(cookieCollapse ? "1" : "0");
			builder.append(begginerCollapse ? "1" : "0");
			builder.append(offerCollapse ? "1" : "0");
			builder.append(identityCollapse ? "1" : "0");
			builder.append(chatbotCollapse ? "1" : "0");
			builder.append(chatboterCollapse ? "1" : "0");
			builder.append(removeCookies ? "1" : "0");
			return builder.toString();
		} catch (Exception e) {return "";}
	}
	
}
