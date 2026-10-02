package com.rinitec.algerieoffice.web.modal.explorer;

import java.io.Serializable;

import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyAppearance;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyFooter;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyHeader;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyMenu;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyMessenger;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyProfile;
import com.rinitec.algerieoffice.web.modal.explorer.profile.ExplorerCompanyShedule;

public class ExplorerCompany implements Serializable {
	private static final long serialVersionUID = 7529692500056619011L;
	
	private final ExplorerCompanyAppearance appearance;
	private final ExplorerCompanyProfile profile;
	private final ExplorerCompanyHeader header;
	private final ExplorerCompanyMenu menu;
	private final ExplorerCompanyShedule shedule;
	private final ExplorerCompanyFooter footer;
	private final ExplorerCompanyMessenger messenger;
	
	public ExplorerCompany(final ExplorerCompanyAppearance appearance, final ExplorerCompanyProfile profile, 
			final ExplorerCompanyHeader header, final ExplorerCompanyMenu menu, final ExplorerCompanyShedule shedule, 
			final ExplorerCompanyFooter footer, final ExplorerCompanyMessenger messenger) {
		this.appearance = appearance;
		this.profile = profile;
		this.header = header;
		this.menu = menu;
		this.shedule = shedule;
		this.footer = footer;
		this.messenger = messenger;
	}
	
	public ExplorerCompanyAppearance getAppearance() {
		return appearance;
	}
	
	public ExplorerCompanyProfile getProfile() {
		return profile;
	}
	
	public ExplorerCompanyHeader getHeader() {
		return header;
	}
	
	public ExplorerCompanyMenu getMenu() {
		return menu;
	}
	
	public ExplorerCompanyShedule getShedule() {
		return shedule;
	}
	
	public ExplorerCompanyFooter getFooter() {
		return footer;
	}
	
	public ExplorerCompanyMessenger getMessenger() {
		return messenger;
	}
	
	public String getLanguage() {
		return profile.getLanguage();
	}

	@Override
	public String toString() {
		return "ExplorerCompany [appearance=" + appearance + ", profile=" + profile + ", header=" + header + ", menu="
				+ menu + ", shedule=" + shedule + ", footer=" + footer + ", messenger=" + messenger + "]";
	}

}
