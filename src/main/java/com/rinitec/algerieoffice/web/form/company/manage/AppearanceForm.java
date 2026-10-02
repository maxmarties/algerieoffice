package com.rinitec.algerieoffice.web.form.company.manage;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.rinitec.algerieoffice.persistence.modal.company.manage.Appearance;
import com.rinitec.algerieoffice.web.form.ConstraintesForm;
import com.rinitec.algerieoffice.web.validator.ValidChose;

public class AppearanceForm implements Serializable {
	private static final long serialVersionUID = -7713761216068189939L;
	
	@NotNull
	private Long id;
	
	@ValidChose
	private Integer theme;
	
	@ValidChose
	private Integer style;
	
	private String primaryColor;
	private String segondColor;
	private String treenColor;
	private String menuBack;
	private String menuColor;
	private String menuHover;
	private String popupBack;
	private String popupColor;
	private String popupHover;
	private String sharedBack;
	private String sharedColor;
	private String sharedHover;
	private String sharedFocus;
	private String titleColor;
	private String titleAfter;
	private String titleProduct;
	private String titleHover;
	private String titleEditor;
	private String pageColor;
	private String textColor;
	private String buttonColor;
	private String footerColor;
	private String primaryTop;
	private String primaryBottom;
	private String segondTop;
	private String segondBottom;
	
	public AppearanceForm() {
		this.theme = this.style = 1;
	}
	
	public AppearanceForm(final Long id, final Appearance appearance) {
		this.id = id;
		if(appearance != null) {
			this.theme = appearance.getTheme();
			this.style = appearance.getTheme() == 17 ? 2 : 1;
			initColor(appearance);
		} else {
			this.theme = this.style = 1;
			initColor();
		}
	}
	
	private final void initColor() {
		this.primaryColor = ConstraintesForm.EXPLORER_PRIMARY_COLORS[0];
		this.segondColor = ConstraintesForm.EXPLORER_SEGOND_COLORS[0];
		this.treenColor = ConstraintesForm.EXPLORER_TREEN_COLORS[0];
		this.menuBack = ConstraintesForm.EXPLORER_MENUBACK_COLORS[0];
		this.menuColor = ConstraintesForm.EXPLORER_MENU_COLORS[0];
		this.menuHover = ConstraintesForm.EXPLORER_MENUHOVER_COLORS[0];
		this.popupBack = ConstraintesForm.EXPLORER_POPUPBACK_COLORS[0];
		this.popupColor = ConstraintesForm.EXPLORER_POPUP_COLORS[0];
		this.popupHover = ConstraintesForm.EXPLORER_POPUPHOVER_COLORS[0];
		this.sharedBack = ConstraintesForm.EXPLORER_SHAREDBACK_COLORS[0];
		this.sharedColor = ConstraintesForm.EXPLORER_SHARED_COLORS[0];
		this.sharedHover = ConstraintesForm.EXPLORER_SHAREDHOVER_COLORS[0];
		this.sharedFocus = ConstraintesForm.EXPLORER_SHAREDFOCUS_COLORS[0];
		this.titleColor = ConstraintesForm.EXPLORER_TITLE_COLORS[0];
		this.titleAfter = ConstraintesForm.EXPLORER_TITLEAFTER_COLORS[0];
		this.titleProduct = ConstraintesForm.EXPLORER_TITLEPRODUCT_COLORS[0];
		this.titleHover = ConstraintesForm.EXPLORER_TITLEHOVER_COLORS[0];
		this.titleEditor = ConstraintesForm.EXPLORER_TITLEFROALA_COLORS[0];
		this.pageColor = ConstraintesForm.EXPLORER_PAGE_COLORS[0];
		this.textColor = ConstraintesForm.EXPLORER_TEXTE_COLORS[0];
		this.buttonColor = ConstraintesForm.EXPLORER_BUTTON_COLORS[0];
		this.footerColor = ConstraintesForm.EXPLORER_FOOTER_COLORS[0];
		this.primaryTop = ConstraintesForm.EXPLORER_PRIMARY1_COLORS[0];
		this.primaryBottom = ConstraintesForm.EXPLORER_PRIMARY2_COLORS[0];
		this.segondTop = ConstraintesForm.EXPLORER_SEGOND1_COLORS[0];
		this.segondBottom = ConstraintesForm.EXPLORER_SEGOND2_COLORS[0];
	}
	
	private final void initColor(final Appearance appearance) {
		if(!StringUtils.isEmpty(appearance.getPrimaryColor())) {
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
			this.titleProduct = appearance.getTitleProduct();
			this.titleHover = appearance.getTitleHover();
			this.titleEditor = appearance.getTitleEditor();
			this.pageColor = appearance.getPageColor();
			this.textColor = appearance.getTextColor();
			this.buttonColor = appearance.getButtonColor();
			this.footerColor = appearance.getFooterColor();
			this.primaryTop = appearance.getPrimaryTop();
			this.primaryBottom = appearance.getPrimaryBottom();
			this.segondTop = appearance.getSegondTop();
			this.segondBottom = appearance.getSegondBottom();
		} else {
			initColor();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTheme() {
		return theme;
	}

	public void setTheme(Integer theme) {
		this.theme = theme;
	}
	
	public Integer getStyle() {
		return style;
	}
	
	public void setStyle(Integer style) {
		this.style = style;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSegondColor() {
		return segondColor;
	}

	public void setSegondColor(String segondColor) {
		this.segondColor = segondColor;
	}

	public String getTreenColor() {
		return treenColor;
	}

	public void setTreenColor(String treenColor) {
		this.treenColor = treenColor;
	}

	public String getMenuBack() {
		return menuBack;
	}

	public void setMenuBack(String menuBack) {
		this.menuBack = menuBack;
	}

	public String getMenuColor() {
		return menuColor;
	}

	public void setMenuColor(String menuColor) {
		this.menuColor = menuColor;
	}

	public String getMenuHover() {
		return menuHover;
	}

	public void setMenuHover(String menuHover) {
		this.menuHover = menuHover;
	}

	public String getPopupBack() {
		return popupBack;
	}

	public void setPopupBack(String popupBack) {
		this.popupBack = popupBack;
	}

	public String getPopupColor() {
		return popupColor;
	}

	public void setPopupColor(String popupColor) {
		this.popupColor = popupColor;
	}

	public String getPopupHover() {
		return popupHover;
	}

	public void setPopupHover(String popupHover) {
		this.popupHover = popupHover;
	}

	public String getSharedBack() {
		return sharedBack;
	}

	public void setSharedBack(String sharedBack) {
		this.sharedBack = sharedBack;
	}

	public String getSharedColor() {
		return sharedColor;
	}

	public void setSharedColor(String sharedColor) {
		this.sharedColor = sharedColor;
	}

	public String getSharedHover() {
		return sharedHover;
	}

	public void setSharedHover(String sharedHover) {
		this.sharedHover = sharedHover;
	}

	public String getSharedFocus() {
		return sharedFocus;
	}

	public void setSharedFocus(String sharedFocus) {
		this.sharedFocus = sharedFocus;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getTitleAfter() {
		return titleAfter;
	}

	public void setTitleAfter(String titleAfter) {
		this.titleAfter = titleAfter;
	}

	public String getTitleProduct() {
		return titleProduct;
	}

	public void setTitleProduct(String titleProduct) {
		this.titleProduct = titleProduct;
	}

	public String getTitleHover() {
		return titleHover;
	}

	public void setTitleHover(String titleHover) {
		this.titleHover = titleHover;
	}

	public String getTitleEditor() {
		return titleEditor;
	}

	public void setTitleEditor(String titleEditor) {
		this.titleEditor = titleEditor;
	}

	public String getPageColor() {
		return pageColor;
	}

	public void setPageColor(String pageColor) {
		this.pageColor = pageColor;
	}

	public String getTextColor() {
		return textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getFooterColor() {
		return footerColor;
	}

	public void setFooterColor(String footerColor) {
		this.footerColor = footerColor;
	}

	public String getPrimaryTop() {
		return primaryTop;
	}

	public void setPrimaryTop(String primaryTop) {
		this.primaryTop = primaryTop;
	}

	public String getPrimaryBottom() {
		return primaryBottom;
	}

	public void setPrimaryBottom(String primaryBottom) {
		this.primaryBottom = primaryBottom;
	}

	public String getSegondTop() {
		return segondTop;
	}

	public void setSegondTop(String segondTop) {
		this.segondTop = segondTop;
	}

	public String getSegondBottom() {
		return segondBottom;
	}

	public void setSegondBottom(String segondBottom) {
		this.segondBottom = segondBottom;
	}

	@Override
	public String toString() {
		return "AppearanceForm [id=" + id + ", theme=" + theme + ", style=" + style + ", primaryColor=" + primaryColor
				+ ", segondColor=" + segondColor + ", treenColor=" + treenColor + ", menuBack=" + menuBack
				+ ", menuColor=" + menuColor + ", menuHover=" + menuHover + ", popupBack=" + popupBack + ", popupColor="
				+ popupColor + ", popupHover=" + popupHover + ", sharedBack=" + sharedBack + ", sharedColor="
				+ sharedColor + ", sharedHover=" + sharedHover + ", sharedFocus=" + sharedFocus + ", titleColor="
				+ titleColor + ", titleAfter=" + titleAfter + ", titleProduct=" + titleProduct + ", titleHover="
				+ titleHover + ", titleEditor=" + titleEditor + ", pageColor=" + pageColor + ", textColor=" + textColor
				+ ", buttonColor=" + buttonColor + ", footerColor=" + footerColor + ", primaryTop=" + primaryTop
				+ ", primaryBottom=" + primaryBottom + ", segondTop=" + segondTop + ", segondBottom=" + segondBottom + "]";
	}

}
