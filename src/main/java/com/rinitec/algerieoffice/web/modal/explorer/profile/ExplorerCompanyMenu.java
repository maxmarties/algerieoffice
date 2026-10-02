package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;
import java.util.List;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Maindisplay;
import com.rinitec.algerieoffice.persistence.result.PostMini;
import com.rinitec.algerieoffice.web.form.ConstraintesURL;

public class ExplorerCompanyMenu implements Serializable {
	private static final long serialVersionUID = 6750703981303946829L;
	
	private final String mainmenu;
	private final String society;
	private final String mainsidbar;
	private final String mainfooter;
	private final String display;
	private final List<PostMini> categories;
	private final String categoryURL;
	
	public ExplorerCompanyMenu(final Maindisplay maindisplay, final List<PostMini> categories) {
		if(maindisplay != null) {
			this.mainmenu = maindisplay.getMainmenu();
			this.society = maindisplay.getSociety();
			this.mainsidbar = maindisplay.getMainsidbar();
			this.mainfooter = maindisplay.getMainfooter();
			this.display = maindisplay.getDisplay();
		} else {
			this.mainmenu = "1111";
			this.society = "000000";
			this.mainsidbar = "111111";
			this.mainfooter = "1110011";
			this.display = "111111101111100000000000";
		}
		this.categories = categories;
		this.categoryURL = ConstraintesURL.URL_CATEGORIES;
	}
	
	public boolean[] mainmenu() {
		final boolean[] menu = new boolean[4];
		for (int i = 0; i < 4; i++) {
			menu[i] = (mainmenu.charAt(i) == '1');
		}
		return menu;
	}
	
	public boolean[] society() {
		final boolean[] menu = new boolean[7];
		for (int i = 0; i < 6; i++) {
			menu[i] = (society.charAt(i) == '1');
		}
		return menu;
	}
	
	public boolean hasSociety() {
		for (int i = 0; i < 6; i++) {
			if(society.charAt(i) == '1') return true;
		}
		return false;
	}
	
	public boolean[] sidebar() {
		final boolean[] menu = new boolean[7];
		for (int i = 0; i < 6; i++) {
			menu[i] = (mainsidbar.charAt(i) == '1');
		}
		return menu;
	}
	
	public boolean[] mainfooter() {
		final boolean[] menu = new boolean[7];
		for (int i = 0; i < 7; i++) {
			menu[i] = (mainfooter.charAt(i) == '1');
		}
		return menu;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public String pageStyle() {
		return String.valueOf(display.charAt(0));
	}
	
	public String postStyle() {
		return String.valueOf(display.charAt(1));
	}
	
	public boolean hasSlider() {
		return display.charAt(2) == '1';
	}
	
	public boolean hasPost() {
		return display.charAt(3) == '1';
	}
	
	public boolean hasTimeline() {
		return display.charAt(4) == '1';
	}
	
	public boolean hasWork() {
		return display.charAt(5) == '1';
	}
	
	public boolean hasActusFooter() {
		return display.charAt(6) == '1';
	}
	
	public boolean hasMenuFixed() {
		return display.charAt(7) == '1';
	}
	
	public boolean hasSticky() {
		return display.charAt(8) == '1';
	}
	
	public boolean hasAbout() {
		return display.charAt(9) == '1';
	}
	
	public boolean hasCatalog() {
		return display.charAt(10) == '1';
	}
	
	public boolean hasThink() {
		return display.charAt(11) == '1';
	}
	
	public boolean hasLateral() {
		return display.charAt(12) == '1';
	}

	public List<PostMini> getCategories() {
		return categories;
	}
	
	public String getCategoryURL() {
		return categoryURL;
	}

	@Override
	public String toString() {
		return "ExplorerCompanyMenu [mainmenu=" + mainmenu + ", society=" + society + ", mainsidbar=" + mainsidbar
				+ ", mainfooter=" + mainfooter + ", display=" + display + ", categories=" + categories
				+ ", categoryURL=" + categoryURL + "]";
	}

}
