package com.rinitec.algerieoffice.web.modal.explorer.profile;

import java.io.Serializable;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Appearance;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;

public class ExplorerCompanyAppearance implements Serializable {
	private static final long serialVersionUID = 8781726536609758619L;
	
	private final String primaryColor;
	private final String segondColor;
	private final String treenColor;
	private final String menuBack;
	private final String menuColor;
	private final String menuHover;
	private final String popupBack;
	private final String popupColor;
	private final String popupHover;
	private final String sharedBack;
	private final String sharedColor;
	private final String sharedHover;
	private final String sharedFocus;
	private final String titleColor;
	private final String titleAfter;
	private final String titleProduct;
	private final String titleHover;
	private final String titleEditor;
	private final String pageColor;
	private final String topColor;
	private final String textColor;
	private final String selectColor;
	private final String buttonColor;
	private final String helpColor;
	private final String inputColor;
	private final String iconColor;
	private final String borderColor;
	private final String alertColor;
	private final String footerColor;
	private final String favoriteColor;
	private final String redColor;
	private final String blueColor;
	private final String yellowColor;
	private final String shadow1Color;
	private final String shadow2Color;
	private final String shadow3Color;
	private final String separtor1Color;
	private final String separtor2Color;
	private final String separtor3Color;
	private final String separtor4Color;
	private final String overlayColor;
	private final String overlay9Color;
	private final String darkColor;
	private final String light1Color;
	private final String light2Color;
	private final String light3Color;
	private final String primary1Color;
	private final String primary2Color;
	private final String segond1Color;
	private final String segond2Color;
	
	public ExplorerCompanyAppearance(final Appearance appearance) {
		final int theme = appearance != null ? appearance.getTheme() : 1;
		if(theme == 17) {
			this.primaryColor = appearance.getPrimaryColor();
			this.segondColor = appearance.getSegondColor();
			this.treenColor = appearance.getTreenColor();
			this.menuBack = appearance.getMenuBack();
			this.menuColor = appearance.getMenuColor();
			this.menuHover = appearance.getMenuHover();
			this.popupBack = appearance.getPopupBack();
			this.popupColor = appearance.getPopupColor();
			this.popupHover = appearance.getPopupHover();
			this.sharedBack = appearance.getSharedBack();
			this.sharedColor = appearance.getSharedColor();
			this.sharedHover = appearance.getSharedHover();
			this.sharedFocus = appearance.getSharedFocus();
			this.titleColor = appearance.getTitleColor();
			this.titleAfter = appearance.getTitleAfter();
			this.titleHover = appearance.getTitleHover();
			this.titleProduct = appearance.getTitleProduct();
			this.titleEditor = appearance.getTitleEditor();
			this.pageColor = appearance.getPageColor();
			this.topColor = ConstraintesForm.EXPLORER_TOP_COLORS[0];
			this.textColor = appearance.getTextColor();
			this.selectColor = ConstraintesForm.EXPLORER_SELECT_COLORS[0];
			this.buttonColor = appearance.getButtonColor();
			this.helpColor = ConstraintesForm.EXPLORER_HELP_COLORS[0];
			this.inputColor = ConstraintesForm.EXPLORER_INPUT_COLORS[0];
			this.iconColor = ConstraintesForm.EXPLORER_ICON_COLORS[0];
			this.borderColor = ConstraintesForm.EXPLORER_BORDER_COLORS[0];
			this.alertColor = ConstraintesForm.EXPLORER_ALERTE_COLORS[0];
			this.footerColor = appearance.getFooterColor();
			this.favoriteColor = ConstraintesForm.EXPLORER_FAVORITE_COLORS[0];
			this.redColor = ConstraintesForm.EXPLORER_RED_COLORS[0];
			this.blueColor = ConstraintesForm.EXPLORER_BLUE_COLORS[0];
			this.yellowColor = ConstraintesForm.EXPLORER_YELLOW_COLORS[0];
			this.shadow1Color = ConstraintesForm.EXPLORER_SHADOW1_COLORS[0];
			this.shadow2Color = ConstraintesForm.EXPLORER_SHADOW2_COLORS[0];
			this.shadow3Color = ConstraintesForm.EXPLORER_SHADOW3_COLORS[0];
			this.separtor1Color = ConstraintesForm.EXPLORER_SEPARATOR1_COLORS[0];
			this.separtor2Color = ConstraintesForm.EXPLORER_SEPARATOR2_COLORS[0];
			this.separtor3Color = ConstraintesForm.EXPLORER_SEPARATOR3_COLORS[0];
			this.separtor4Color = ConstraintesForm.EXPLORER_SEPARATOR4_COLORS[0];
			this.overlayColor = ConstraintesForm.EXPLORER_OVERLAY_COLORS[0];
			this.overlay9Color = ConstraintesForm.EXPLORER_OVERLAY9_COLORS[0];
			this.darkColor = ConstraintesForm.EXPLORER_DARK_COLORS[0];
			this.light1Color = ConstraintesForm.EXPLORER_LIGHT1_COLORS[0];
			this.light2Color = ConstraintesForm.EXPLORER_LIGHT2_COLORS[0];
			this.light3Color = ConstraintesForm.EXPLORER_LIGHT3_COLORS[0];
			this.primary1Color = appearance.getPrimaryTop();
			this.primary2Color = appearance.getPrimaryBottom();
			this.segond1Color = appearance.getSegondTop();
			this.segond2Color = appearance.getSegondBottom();
		} else {
			final int index = theme - 1;
			this.primaryColor = ConstraintesForm.EXPLORER_PRIMARY_COLORS[index];
			this.segondColor = ConstraintesForm.EXPLORER_SEGOND_COLORS[index];
			this.treenColor = ConstraintesForm.EXPLORER_TREEN_COLORS[index];
			this.menuBack = ConstraintesForm.EXPLORER_MENUBACK_COLORS[index];
			this.menuColor = ConstraintesForm.EXPLORER_MENU_COLORS[index];
			this.menuHover = ConstraintesForm.EXPLORER_MENUHOVER_COLORS[index];
			this.popupBack = ConstraintesForm.EXPLORER_POPUPBACK_COLORS[index];
			this.popupColor = ConstraintesForm.EXPLORER_POPUP_COLORS[index];
			this.popupHover = ConstraintesForm.EXPLORER_POPUPHOVER_COLORS[index];
			this.sharedBack = ConstraintesForm.EXPLORER_SHAREDBACK_COLORS[index];
			this.sharedColor = ConstraintesForm.EXPLORER_SHARED_COLORS[index];
			this.sharedHover = ConstraintesForm.EXPLORER_SHAREDHOVER_COLORS[index];
			this.sharedFocus = ConstraintesForm.EXPLORER_SHAREDFOCUS_COLORS[index];
			this.titleColor = ConstraintesForm.EXPLORER_TITLE_COLORS[index];
			this.titleAfter = ConstraintesForm.EXPLORER_TITLEAFTER_COLORS[index];
			this.titleHover = ConstraintesForm.EXPLORER_TITLEHOVER_COLORS[index];
			this.titleProduct = ConstraintesForm.EXPLORER_TITLEPRODUCT_COLORS[index];
			this.titleEditor = ConstraintesForm.EXPLORER_TITLEFROALA_COLORS[index];
			this.pageColor = ConstraintesForm.EXPLORER_PAGE_COLORS[index];
			this.topColor = ConstraintesForm.EXPLORER_TOP_COLORS[index];
			this.textColor = ConstraintesForm.EXPLORER_TEXTE_COLORS[index];
			this.selectColor = ConstraintesForm.EXPLORER_SELECT_COLORS[index];
			this.buttonColor = ConstraintesForm.EXPLORER_BUTTON_COLORS[index];
			this.helpColor = ConstraintesForm.EXPLORER_HELP_COLORS[index];
			this.inputColor = ConstraintesForm.EXPLORER_INPUT_COLORS[index];
			this.iconColor = ConstraintesForm.EXPLORER_ICON_COLORS[index];
			this.borderColor = ConstraintesForm.EXPLORER_BORDER_COLORS[index];
			this.alertColor = ConstraintesForm.EXPLORER_ALERTE_COLORS[index];
			this.footerColor = ConstraintesForm.EXPLORER_FOOTER_COLORS[index];
			this.favoriteColor = ConstraintesForm.EXPLORER_FAVORITE_COLORS[index];
			this.redColor = ConstraintesForm.EXPLORER_RED_COLORS[index];
			this.blueColor = ConstraintesForm.EXPLORER_BLUE_COLORS[index];
			this.yellowColor = ConstraintesForm.EXPLORER_YELLOW_COLORS[index];
			this.shadow1Color = ConstraintesForm.EXPLORER_SHADOW1_COLORS[index];
			this.shadow2Color = ConstraintesForm.EXPLORER_SHADOW2_COLORS[index];
			this.shadow3Color = ConstraintesForm.EXPLORER_SHADOW3_COLORS[index];
			this.separtor1Color = ConstraintesForm.EXPLORER_SEPARATOR1_COLORS[index];
			this.separtor2Color = ConstraintesForm.EXPLORER_SEPARATOR2_COLORS[index];
			this.separtor3Color = ConstraintesForm.EXPLORER_SEPARATOR3_COLORS[index];
			this.separtor4Color = ConstraintesForm.EXPLORER_SEPARATOR4_COLORS[index];
			this.overlayColor = ConstraintesForm.EXPLORER_OVERLAY_COLORS[index];
			this.overlay9Color = ConstraintesForm.EXPLORER_OVERLAY9_COLORS[index];
			this.darkColor = ConstraintesForm.EXPLORER_DARK_COLORS[index];
			this.light1Color = ConstraintesForm.EXPLORER_LIGHT1_COLORS[index];
			this.light2Color = ConstraintesForm.EXPLORER_LIGHT2_COLORS[index];
			this.light3Color = ConstraintesForm.EXPLORER_LIGHT3_COLORS[index];
			this.primary1Color = ConstraintesForm.EXPLORER_PRIMARY1_COLORS[index];
			this.primary2Color = ConstraintesForm.EXPLORER_PRIMARY2_COLORS[index];
			this.segond1Color = ConstraintesForm.EXPLORER_SEGOND1_COLORS[index];
			this.segond2Color = ConstraintesForm.EXPLORER_SEGOND2_COLORS[index];
		}
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public String getSegondColor() {
		return segondColor;
	}

	public String getTreenColor() {
		return treenColor;
	}

	public String getMenuBack() {
		return menuBack;
	}

	public String getMenuColor() {
		return menuColor;
	}

	public String getMenuHover() {
		return menuHover;
	}

	public String getPopupBack() {
		return popupBack;
	}

	public String getPopupColor() {
		return popupColor;
	}

	public String getPopupHover() {
		return popupHover;
	}

	public String getSharedBack() {
		return sharedBack;
	}

	public String getSharedColor() {
		return sharedColor;
	}

	public String getSharedHover() {
		return sharedHover;
	}

	public String getSharedFocus() {
		return sharedFocus;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public String getTitleAfter() {
		return titleAfter;
	}

	public String getTitleProduct() {
		return titleProduct;
	}

	public String getTitleHover() {
		return titleHover;
	}

	public String getTitleEditor() {
		return titleEditor;
	}

	public String getPageColor() {
		return pageColor;
	}

	public String getTopColor() {
		return topColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public String getSelectColor() {
		return selectColor;
	}

	public String getButtonColor() {
		return buttonColor;
	}

	public String getHelpColor() {
		return helpColor;
	}

	public String getInputColor() {
		return inputColor;
	}

	public String getIconColor() {
		return iconColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public String getAlertColor() {
		return alertColor;
	}

	public String getFooterColor() {
		return footerColor;
	}

	public String getFavoriteColor() {
		return favoriteColor;
	}

	public String getRedColor() {
		return redColor;
	}

	public String getBlueColor() {
		return blueColor;
	}

	public String getYellowColor() {
		return yellowColor;
	}

	public String getShadow1Color() {
		return shadow1Color;
	}

	public String getShadow2Color() {
		return shadow2Color;
	}

	public String getShadow3Color() {
		return shadow3Color;
	}

	public String getSepartor1Color() {
		return separtor1Color;
	}

	public String getSepartor2Color() {
		return separtor2Color;
	}

	public String getSepartor3Color() {
		return separtor3Color;
	}

	public String getSepartor4Color() {
		return separtor4Color;
	}

	public String getOverlayColor() {
		return overlayColor;
	}

	public String getOverlay9Color() {
		return overlay9Color;
	}

	public String getDarkColor() {
		return darkColor;
	}

	public String getLight1Color() {
		return light1Color;
	}

	public String getLight2Color() {
		return light2Color;
	}

	public String getLight3Color() {
		return light3Color;
	}

	public String getPrimary1Color() {
		return primary1Color;
	}

	public String getPrimary2Color() {
		return primary2Color;
	}

	public String getSegond1Color() {
		return segond1Color;
	}

	public String getSegond2Color() {
		return segond2Color;
	}

	@Override
	public String toString() {
		return "ExplorerCompanyAppearance [primaryColor=" + primaryColor + ", segondColor=" + segondColor
				+ ", treenColor=" + treenColor + ", menuBack=" + menuBack + ", menuColor=" + menuColor + ", menuHover="
				+ menuHover + ", popupBack=" + popupBack + ", popupColor=" + popupColor + ", popupHover=" + popupHover
				+ ", sharedBack=" + sharedBack + ", sharedColor=" + sharedColor + ", sharedHover=" + sharedHover
				+ ", sharedFocus=" + sharedFocus + ", titleColor=" + titleColor + ", titleAfter=" + titleAfter
				+ ", titleProduct=" + titleProduct + ", titleHover=" + titleHover + ", titleEditor=" + titleEditor
				+ ", pageColor=" + pageColor + ", topColor=" + topColor + ", textColor=" + textColor + ", selectColor="
				+ selectColor + ", buttonColor=" + buttonColor + ", helpColor=" + helpColor + ", inputColor="
				+ inputColor + ", iconColor=" + iconColor + ", borderColor=" + borderColor + ", alertColor="
				+ alertColor + ", footerColor=" + footerColor + ", favoriteColor=" + favoriteColor + ", redColor="
				+ redColor + ", blueColor=" + blueColor + ", yellowColor=" + yellowColor + ", shadow1Color="
				+ shadow1Color + ", shadow2Color=" + shadow2Color + ", shadow3Color=" + shadow3Color
				+ ", separtor1Color=" + separtor1Color + ", separtor2Color=" + separtor2Color + ", separtor3Color="
				+ separtor3Color + ", separtor4Color=" + separtor4Color + ", overlayColor=" + overlayColor
				+ ", overlay9Color=" + overlay9Color + ", darkColor=" + darkColor + ", light1Color=" + light1Color
				+ ", light2Color=" + light2Color + ", light3Color=" + light3Color + ", primary1Color=" + primary1Color
				+ ", primary2Color=" + primary2Color + ", segond1Color=" + segond1Color + ", segond2Color="
				+ segond2Color + "]";
	}

}
